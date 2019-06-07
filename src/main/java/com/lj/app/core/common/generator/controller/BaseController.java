package com.lj.app.core.common.generator.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 基类
 *
 */
public class BaseController {

    /**
     * 获得登录ID
     * @param request
     * @return
     */
	public Long getLoginId(HttpServletRequest request) {
		/*Map<String, Object> map = JwtUtil.validateTokenAndGetClaims(request);
		return Long.valueOf((String) map.get("USER_ID"));*/
		return 1L;
	}

	/**
	 * 获得登录用户名
	 * @param request
	 * @return
	 */
	public String getUserName(HttpServletRequest request) {
	/*	Map<String, Object> map = JwtUtil.validateTokenAndGetClaims(request);
		return (String) map.get("USER_NAME");*/
		return "admin";
	}

	/**
	 * 获取文件拓展名
	 * 
	 */
	public static String getExt(String path) {

		if (path == null || path.equals("") || !path.contains(".")) {
			return null;
		} else {
			return path.substring(path.lastIndexOf('.') + 1, path.length());
		}

	}

}
