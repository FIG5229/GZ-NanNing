/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;

/**
 * 手机端视图拦截器
 * @author ThinkGem
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");
	/*https://www.cnblogs.com/shihaiming/p/9565835.html*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
//		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间
			startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
			logger.error("手机APP访问开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
					.format(beginTime), request.getRequestURI());
//		}


		String token = request.getHeader("token");// 从 http 请求头中取出 token
		// 如果不是映射到方法直接通过
		/*if (!(handler instanceof HandlerMethod)) {
			return true;
		}*/
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();


		// 执行认证
		if (token == null) {
			throw new RuntimeException("无token，请重新登录");
		}
		// 获取 token 中的 user id
		String policeId;
		try {
			policeId = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}catch (NullPointerException e){
			throw new RuntimeException("{\"message\":\"token验证失败\",\"data:"+token+"\",\"success\":false}");
		}
		User user = UserUtils.getByLoginName(policeId.trim());
		if (user == null) {
			throw new RuntimeException("{\"message\":\"用户查询失败，请确认警号"+policeId+"\",\"success\":false}");
		}
		// 验证 token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getNo())).build();
		try {
			jwtVerifier.verify(token);
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			throw new RuntimeException("{\"message\":\"token验证失败\",\"success\":false}");
		}
		return true;


	}

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();

			System.out.println("访问ip=" + ip);

			if (ip.equals("127.0.0.1")) {

				//根据网卡取本机配置的IP    

				InetAddress inet = null;

				try {

					inet = InetAddress.getLocalHost();

				} catch (Exception e) {

					e.printStackTrace();

				}

				ip = inet.getHostAddress();

			}

		}

		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   

		if (ip != null && ip.length() > 15) { //"***.***.***.***".length() = 15   

			if (ip.indexOf(",") > 0) {

				ip = ip.substring(0, ip.indexOf(","));

			}

		}

		System.out.println("访问ip=========" + ip);

		return ip;

	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
				modelAndView.setViewName("mobile/" + modelAndView.getViewName());
			}
		}
	}
	/**
	 * 获取并返回token信息
	 * @param request
	 * @return
	 */
	private String getJwt(HttpServletRequest request) {

		//从header中获取
		String authHeader = request.getHeader("Authorization");
		String header = "token";
		if (StringUtils.startsWith(authHeader, header)) {
			authHeader = authHeader.replace(header+" ", "");
		}

		return authHeader;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		// 打印JVM信息。
		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis(); 	//2、结束时间
			logger.error("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024,
					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
			//删除线程变量中的数据，防止内存泄漏
			startTimeThreadLocal.remove();
		}
	}

}
