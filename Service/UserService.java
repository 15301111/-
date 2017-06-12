package Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.User;
import Utils.DBUtil;
import Utils.JDBC;

public class UserService {



public boolean register(String name,String password) {
	
	boolean registflag = false;
	boolean existflag = false;
	
	DBUtil util = new DBUtil();
	existflag = util.Check(name);
    registflag = util.InsertInfo(name, password);
    
	return registflag&&(!existflag);
}
 	
public boolean login(String name,String password){
	DBUtil util = new DBUtil();
	boolean exist = false;
	boolean match = false;
	boolean loginflag = false;


	exist = util.Check(name);
	if(!exist){
		System.out.println("数据库中存在"+name);
	}
	match = util.Login(name, password);
	
	if(exist&&match){
		loginflag = true;
		System.out.println("登录成功");
	
	}
	return loginflag;
  }



public List<User> getUserListByRole(String role)  {
	DBUtil util = new DBUtil();
	List<User> userList = new ArrayList<User>();
	// Declare userIds
	List<Integer> userIds = null; 
	
	
		/*
		 * 1.Get designated user's userIds
		 */
		userIds = util.getUserIDsByRoleId(role);
		
		/*
		 * 2.Get user information list according to userIds
		 */ 
		for (int userId : userIds) {
			// Get user's information
			User user = util.getByID(userId);
			if (user != null) {
				userList.add(user); 
			}
		}
	
	// Return userList
	return userList;
}
}
