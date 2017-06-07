package Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	System.out.println("this is my test");
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
}
