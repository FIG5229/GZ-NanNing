/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborOfficeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.sys.dao.*;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户工具类
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";
    public static final String CACHE_AUTH_INFO = "authInfo";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_LABOR_OFFICE_LIST = "laborOfficeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
    public static final String CACHE_LABOR_OFFICE_ALL_LIST = "laborOfficeAllList";
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static AffairLaborOfficeDao affairLaborOfficeDao = SpringContextHolder.getBean(AffairLaborOfficeDao.class);

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null) {
            user = userDao.getByLoginName(new User(null, loginName));
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_AUTH_INFO);
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_LABOR_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        removeCache(CACHE_LABOR_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
        }
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                roleList = roleDao.findAllList(new Role());
            } else {
                Role role = new Role();
                role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    public static List<User> findUserByRoleId(String roleId) {
        return userDao.findUserByRoleId(roleId);
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
//        List<Menu> resList = new ArrayList<Menu>();
//        Map<String, String> getOrgRelation = officeDao.getOrgRelation(UserUtils.getUser().getOffice().getId());
//
//        for (Menu menu : menuList) {
//            if(isNeedAdd(getOrgRelation,menu)){
//                resList.add(menu);
//            }
//        }
        return menuList;
    }

    private static boolean isNeedAdd(Map<String, String> getOrgRelation, Menu menu) {
        //团
        Set<String> groupSet = new HashSet<String>();
        groupSet.add("3f700926a0124fc79437e318cb12f7c9");
        groupSet.add("c9f4c6785f4c4a63a715e241d994ca6f");
        groupSet.add("341a6367fcbe43b492ca534c2dc3e0a5");
        groupSet.add("626fdd571adb4627a9efef9af92a9e7e");
        groupSet.add("08ab0b8a63c1422e9a75fb7859528416");
        groupSet.add("8138feb5bfff46a7a5f81d61514bff87");
        groupSet.add("b38b940d6634433b83895e731e501e38");
        groupSet.add("7fcedf0bb5204a0fa6fdfcb9e81bcbd8");

        //工会
        Set<String> unionSet = new HashSet<String>();
        unionSet.add("0ae5ad1a31694181a3547e2c1b4b4777");
        unionSet.add("a36e3f9d313b46cdbdd490288750fd23");
        unionSet.add("5d8133c75b7941f9a19c0b8a71dd6a08");
        unionSet.add("7728405e35294525bd0efa6051731b31");
        unionSet.add("9690824e87414ef5be7251c6ad50c288");
        unionSet.add("653253eef4a644fa80ef12f3f975478c");
        unionSet.add("4b5fa2d716a548e8b5bedb74852df962");
        unionSet.add("8eed95a3c6504bef8a8fe769bff3ba6e");
        unionSet.add("9ad7303015be4d66a08afb062ab07b6e");
        unionSet.add("f9327882a022474fba67e6c75bf1cb53");
        unionSet.add("5f86797fb9fe49699f68d3f86cc34865");
        unionSet.add("626b960b7d914b52a039b9738f06959e");
        boolean result = true;
        if (null ==getOrgRelation || null == getOrgRelation.get("party_id")) {
            if (menu.getParentIds().indexOf("65709bf8c79a448da0de3ce941222a55") > -1 || menu.getId().indexOf("65709bf8c79a448da0de3ce941222a55") > -1) {
                result = false;
            }
        }
        if (null ==getOrgRelation ||null == getOrgRelation.get("group_id")) {
            if (groupSet.contains(menu.getId()) || groupSet.contains(menu.getParentId()) || groupSet.contains(menu.getParent().getParentId())) {
                result = false;
            }
        }

        if (null ==getOrgRelation ||null == getOrgRelation.get("union_id")) {
            if (unionSet.contains(menu.getId()) || unionSet.contains(menu.getParentId()) || unionSet.contains(menu.getParent().getParentId())) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new Area());
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                officeList = officeDao.findAllList(new Office());
            } else {
                Office office = new Office();
                office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = officeDao.findList(office);
            }
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 劳资获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<AffairLaborOffice> getLaborOfficeList() {
        @SuppressWarnings("unchecked")
        List<AffairLaborOffice> officeList = (List<AffairLaborOffice>) getCache(CACHE_LABOR_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                officeList = affairLaborOfficeDao.findAllList(new AffairLaborOffice());
            } else {
                AffairLaborOffice affairLaborOffice = new AffairLaborOffice();
                affairLaborOffice.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = affairLaborOfficeDao.findList(affairLaborOffice);
            }
            putCache(CACHE_LABOR_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeDao.findAllList(new Office());
        }
        return officeList;
    }

    /**
     * 劳资获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<AffairLaborOffice> getLaborOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<AffairLaborOffice> officeList = (List<AffairLaborOffice>) getCache(CACHE_LABOR_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = affairLaborOfficeDao.findAllList(new AffairLaborOffice());
        }
        return officeList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

}
