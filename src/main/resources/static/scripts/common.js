var url=window.location.href;
var start=window.location.host.length+window.location.protocol.length+2;

if (window.frames.length != parent.frames.length) {
    url=top.location.href;
    start=top.location.host.length+window.location.protocol.length+2;
}

var end = url.indexOf("/upm");
var ctx=url.substring(start,end);


function trim(str){  
    str = str.replace(/^(\s|\u00A0)+/,'');   
    for(var i=str.length-1; i>=0; i--){   
        if(/\S/.test(str.charAt(i))){   
            str = str.substring(0, i+1);   
            break;   
        }   
    }   
    return str;   
}

//检测手机号是不符合要求
function isWrongMobileNum(sMobile) {
	return !(/^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1})+\d{8})$/.test(sMobile));
}

/**
* 消除ajax后台返回值的前后缀
*/
function removePre(data){
	var _data = data.replace("<PRE>","").replace("</PRE>","");
	return _data;
}

//截取参数
function getUrlParam  (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * 获取url参数
 * @param name
 * @param name2
 * @returns
 */
function getUrlParamValue(name,name2) {

	if (name == null || name == 'undefined') {
		return null; 
	}

	var searchStr = decodeURI(location.search);

	var infoIndex = searchStr.indexOf(name + "=");
	
	if (infoIndex == -1) {
		return null; 
	}

	var searchInfo = searchStr.substring(infoIndex + name.length + 1);

	var tagIndex = searchInfo.indexOf("&"+name2);

	if (tagIndex !=-1 ) { 
		searchInfo = searchInfo.substring(0, tagIndex);
		}

	return searchInfo;

	}