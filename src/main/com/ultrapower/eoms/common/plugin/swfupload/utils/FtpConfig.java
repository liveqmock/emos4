package com.ultrapower.eoms.common.plugin.swfupload.utils;

public class FtpConfig {
	private String server;//服务器地址
	private int port;//端口
	private String username;//用户名
	private String password;//密码
	private String location;//访问的ftp相对目录
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
