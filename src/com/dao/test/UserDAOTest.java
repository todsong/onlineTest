package com.dao.test;

import java.util.Iterator;
import java.util.List;

import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.pojo.User;

public class UserDAOTest 
{
	private User user;
	private UserDAO ud = new UserDAOImpl();
	public void addUserTest(User user)
	{
		user = new User();
		user.setName("张三");
		user.setId("00001");
		user.setPasswd("asd");
		ud.addUser(user);
	}
	public void queryUserById(String id)
	{
		user = ud.queryUserById(id);
		System.out.println(user.getId()+" "+user.getName()+" "+user.getPasswd());
	}
	public void queryUserByName(String name)
    {
        List<User> userList = ud.queryUserByName(name);
        for(Iterator<User> iter=userList.iterator(); iter.hasNext(); )
        {
            User user = iter.next();
            System.out.println(user.getId()+" "+user.getName()+" "+user.getPasswd());   
        }
    }
	public void queryUserByDept(int dept)
    {
        List<User> userList = ud.queryUserByDept(dept);
        for(Iterator<User> iter=userList.iterator(); iter.hasNext(); )
        {
            User user = iter.next();
            System.out.println(user.getId()+" "+user.getName()+" "+user.getPasswd());   
        }
    }
	public void getAllUser()
    {
        List<User> userList = ud.getAllUser();
        for(Iterator<User> iter=userList.iterator(); iter.hasNext(); )
        {
            User user = iter.next();
            System.out.println(user.getId()+" "+user.getName()+" "+user.getPasswd());   
        }
    }
	public void updateUserById(String id)
	{
		user = new User();
		user.setName("李四");
		user.setId("00002");
		user.setPasswd("asd");
		ud.updateUserById(id, user);
		ud.queryUserById(id);
	}
	
	public void deleteUserById(String id)
	{
		ud.deleteUserById(id);
		ud.queryUserById(id);
	}
	public void getUserSum()
    {
        
        System.out.println(ud.getUserSum());
    }
	public static void main(String[] args)
	{
		UserDAOTest udt = new UserDAOTest();
		//udt.addUserTest(udt.user);
		//udt.queryUserById("00002");
		//udt.queryUserByName("张三");
	//	udt.updateUserById("00001");
		//udt.deleteUserById("00001");
		udt.getUserSum();
		udt.getAllUser();
		udt.getUserSum();
        
		//udt.queryUserByDept("2");
	}
}
