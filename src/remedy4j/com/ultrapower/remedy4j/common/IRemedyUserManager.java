package com.ultrapower.remedy4j.common;

public interface IRemedyUserManager
{
	public boolean addUser(String loginName, String password, String fullName);
	public boolean updateUser(String loginName, String password, String fullName);
	public boolean deleteUser(String loginName);
}
