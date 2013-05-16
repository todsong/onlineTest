package com.dao;

import java.util.List;

import com.pojo.User;

public interface UserDAO  extends GenericDAO
{
	public int addUser(User user);
	public int updateUserById(String id, User user);
	public int deleteUserById(String Id);
	public int getUserSum();
	public List<User> getAllUser();
	public User queryUserByPwd(String id, String passwd);
	public User queryUserById(String id);
    public List<User> queryUserByName(String name);
    public List<User> queryUserByDept(int dept);
}
