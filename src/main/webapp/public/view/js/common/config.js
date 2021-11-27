/**
 * config.js配置
 * 配置系统服务地址接口、路径、主题等参数
 * date:2020.04.26
 */
var webroot = "/view/";                           // 本地环境配置/zg-smart-search/，访问使用地址
//var ajaxRoot = 'http://'+window.location.host+'/politics';      // 服务地址
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录，如： myproj/view/my.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8080
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/myproj
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//得到了 http://localhost:8080/myproj
var ajaxRoot =localhostPaht+projectName;

var GLOBAL_DEBUG = false;                    //是否开启测试
// 后台ajax请求地址
var GLOBAL_AJAX_URL = {
    /***智能搜索 **/
    keySearchType:'/keysearch/listType',  // 查询所有分类
    keySearchSearch:'/keysearch/search',  // 搜索结果列表
    keySearchCount:'/keysearch/count',    // 统计所有分类数据数目
    keySearchSelect:'/keysearch/dict',    // 分类中字段的select选择
    keySearchExport:'/keysearch/export',  // 导出搜索结果excel表
};
