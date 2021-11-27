package com.thinkgem.jeesite.modules.sys.security.token;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义 Shiro 的 Filter。
 * Shiro 自定义 Filter 不能注册到 springBean，就是不能使用@bean的方式。
 * 必须new一个新对象，然后交由shiroFilter管理。语法如下：
 * Map<String, Filter> filters = new HashMap<>();
 * filters.put("JwtFilter", new JwtFilter());
 * shiroFilterFactoryBean.setFilters(filters);
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            logger.error("JwtFilter过滤验证失败!");
            return false;
        }
    }

   @Override  //拦截之后的登录操作
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        try{
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader("token");
            Subject subject = SecurityUtils.getSubject();
            subject.login(new JwtToken(token));
            return subject.getPrincipal() != null;
        }catch (AuthenticationException e) {
            logger.error("Token登录异常信息："+e.getMessage());
            return false;
        }
    }

    @Override  //对跨域提供支持
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
