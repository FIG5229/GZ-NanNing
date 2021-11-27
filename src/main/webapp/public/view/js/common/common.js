
/**template的自定义**/
function templateInit (){
    //压缩开始
    template.defaults.minimize = true;

    /**过滤器**/
    // 日期去掉时分秒
    template.defaults.imports.dateFormats = function (date, format) {
      return date.substring(0, 10);
    };
    // 组合搜索的head展示
    template.defaults.imports.headerFormat = function (data,headers) {
      let headerText = '';
      headers.forEach(function(val, index, arr){
        $.each(data, function(i, value) {
          if(val === value.code){
            headerText += ' '+value.value;
          };
        });
      })
      return headerText;
    };
    // 设置跳转唯一标识
    template.defaults.imports.identify = function (data,name) {
      var renoObject = name?_.find(data, function(o) {
        return o.code == name;
      }):{};
      return renoObject.value;
    };

    //时间戳转换年月日 时分秒
    template.defaults.imports.dateFormatYMDhms = function (timestamp) {
        var date = new Date(timestamp);
        return date.getFullYear() + '-'+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
    };
    //时间戳转换年月日
    template.defaults.imports.dateFormatYMD = function (timestamp) {
        var date = new Date(timestamp);
        return date.getFullYear() + '-'+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' ';
    };

    //字数限制
    template.defaults.imports.textOverflow = function (text,long) {
        if(text.length>long){
            return '<span class="textOverLimit">'+text.substr(0,long) + '<em>...</em>'+'<span>'+text.substring(long)+'</span></span>';
        }else{
            return text;
        }
    };
    //导入变量:用法$imports.log('hello world')
    template.defaults.imports.log = console.log;
}
//公共方法
function loins_excelDownload(option){
    var xhr = new XMLHttpRequest();
	var pathurl = ajaxRoot + option.url; //'/rand/'+Math.random(1);
    xhr.open(option.type, pathurl, true);
    xhr.responseType = "blob";
	xhr.setRequestHeader("Content-Type",'application/json;charset=utf-8');
	xhr.setRequestHeader("Authorization", 'bearer ' + Cookie.Get("token"));
	xhr.onload = function() {
		if (this.status == 200) {
			//var _b = xhr.getResponseHeader('Content-Disposition');
			//var _c = _b.split('filename=')[1];
			//var _d = decodeURIComponent(_c.split('.')[0])+'.'+_c.split('.')[1];
            var blob = this.response;
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(blob);//创建url对象
            a.href = url;
			//a.download = _d;
			a.download = 'excel.xlsx';
			a.click();
			window.URL.revokeObjectURL(url);//释放url对象
		}

	}
	xhr.send(option.data);
}

/*异步请求方法 ajaxRequest()
* @url 接口请求地址
* @method 接口请求传值方式 默认POST
* @param 传参
* @callBack 成功回调函数
 */
function ajaxRequest(url, method, param, callBack,timeout) {
    var pathurl = ajaxRoot + url; //'/rand/'+Math.random(1);
    var currentAjax = $.ajax({
        type: method || 'POST',
        url: pathurl,
        data: param || {},
        dataType: 'json',
        contentType: 'application/json',
        timeout: timeout || 20000,
        success: function (data) {
            //回调函数
            callBack(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == 'timeout') {
                alert('系统开小差，请尝试重试！')
                callBack(errorThrown);
            } else if (textStatus == 'abort') {
                //已取消加载
            } else {
                if(XMLHttpRequest.responseText){
                    var msg = JSON.parse(XMLHttpRequest.responseText);
                    // alert(JSON.stringify(msg.msg))
                }else{
                    alert('服务器连接失败，请稍后重试！')
                }
            }
        }
    });
    return currentAjax;
}

// XMLHttpRequest下载文件
function xhrDownload(param,callback) {
    var url = ajaxRoot + param.url;
    var xhr = new XMLHttpRequest();
    xhr.open(param.method || 'POST', url, true);
    xhr.responseType = "blob";    // 返回类型blob
    // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    xhr.onload = function () {
        // 请求完成
        if (this.status === 200) {
            callback()
            const content = this.response
            const blob = new Blob([content])
            const fileName = param.title+".xlsx"
            if ('download' in document.createElement('a')) { // 非IE下载
              const elink = document.createElement('a')
              elink.download = fileName
              elink.style.display = 'none'
              elink.href = window.URL.createObjectURL(blob)
              document.body.appendChild(elink)
              elink.click()
              document.body.removeChild(elink)
              window.URL.revokeObjectURL(elink.href) // 释放URL 对象
            } else { // IE10+下载
              navigator.msSaveBlob(blob, fileName)
            }
        }
    };
    // 发送ajax请求
    xhr.send(param.data)
}
// 获取浏览器的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//设置全局唯一标识
function generateUUID() {
    var d = new Date().getTime();
    if (window.performance && typeof window.performance.now === "function") {
        d += performance.now(); //use high-precision timer if available
    }
    //'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
    var uuid = 'zuid4xxx-yxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}



// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt){
    var o = {
      "M+" : this.getMonth()+1,                 //月份
      "d+" : this.getDate(),                    //日
      "h+" : this.getHours(),                   //小时
      "m+" : this.getMinutes(),                 //分
      "s+" : this.getSeconds(),                 //秒
      "q+" : Math.floor((this.getMonth()+3)/3), //季度
      "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
      if(new RegExp("("+ k +")").test(fmt))
    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}
/* 获取时间
** param:day 从0开始 -表示前多少天
*/
function getDay(day){
　　var today = new Date();
　　var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;
　　today.setTime(targetday_milliseconds); //注意，这行是关键代码
　　var tYear = today.getFullYear();
　　var tMonth = today.getMonth();
　　var tDate = today.getDate();
　　tMonth = doHandleMonth(tMonth + 1);
　　tDate = doHandleMonth(tDate);
　　return tYear+"-"+tMonth+"-"+tDate;
}
function getDayFomat(day){
　　var today = new Date();
　　var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;
　　today.setTime(targetday_milliseconds); //注意，这行是关键代码
　　var tYear = today.getFullYear();
　　var tMonth = today.getMonth();
　　var tDate = today.getDate();
　　tMonth = doHandleMonth(tMonth + 1);
　　tDate = doHandleMonth(tDate);
　　return tMonth+"/"+tDate+"/"+tYear;
}
function doHandleMonth(month){
　　var m = month;
　　if(month.toString().length == 1){
　　　　m = "0" + month;
　　}
　　return m;
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
       return unescape(r[2]);
    }else{
       return null;
    }
 }
