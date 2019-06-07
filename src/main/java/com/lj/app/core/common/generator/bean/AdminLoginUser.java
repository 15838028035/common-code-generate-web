package com.lj.app.core.common.generator.bean;

/**
* 管理员登录用户信息
*/
public class AdminLoginUser{
	
	/**
	 *登录账号
	 */
	private String userNo = "";
	
	
	/**
	 * 账号密码  user_pass
	 */
	private String userPass = "";

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
	
}

