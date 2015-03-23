package com.ultrapower.eoms.ultrasla.util;

/**
 * @author RenChenglin
 * @date 2011-10-31 下午02:44:10
 * @version
 */
public class Receiver {
	private String id; // 用户id
	private String loginName; // 用户登录名
	private String fullName; // 用户全名
	private String mobile; // 用户手机
	private String email; //用户邮箱

	public Receiver() {

	}

	public Receiver(String id, String loginName, String fullName,
			String mobile, String email) {
		this.id = id;
		this.loginName = loginName;
		this.fullName = fullName;
		this.mobile = mobile;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString(){
		return id+"|"+loginName+"|"+fullName+"|"+mobile+"|"+email;
	}
}
