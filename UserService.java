package Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Utils.JDBC;

public class UserService {
	
	public static void main(String[]  args){
		String name = getName();
		String password = getPassword();
		 if(CheckExist(name)){
			 register(name, password);
		 }
	}

	
	
public static  boolean CheckExist(String name){
		
		boolean existflag = true;
		
		String sql = "select * from user";
		
		
		try (
				Connection conn = JDBC.getConnection();
			    Statement stmt = conn.createStatement();
			    ResultSet res = stmt.executeQuery(sql);)
		{System.out.println(String.format("Connect to database %s"+" successfully", conn.getCatalog()));
		
		 while(res.next()){ 
			 if(res.getString("username").equals(name)){
				 System.out.println(name+ "exist!");
				 existflag = false;
			 }
			 //System.out.println(res.getString("username")+res.getString("password"));
		 }
		}catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		
		
		return existflag;
		
	}

public static boolean register(String name,String password) {
	
	ResultSet rs = null;
	boolean flag = false;
	int userID = 0;
	String sql = "INSERT INTO user(username, password) VALUES (?,?);";
			
	try(Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
		
		
		pstmt.setString(1, name);
		pstmt.setString(2, password);
		
		int rowAffected = pstmt.executeUpdate();
		
		if(rowAffected ==1){
			flag = true;
			System.out.println("注册成功！");
			rs = pstmt.getGeneratedKeys();  // 获取结果
			if(rs.next()){
				userID = rs.getInt(1);  //得到id
			}
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}finally{
		try {
			if(rs != null)
			rs.close();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
			
		}
	}
	return flag;
}
 	
	public static  String getName(){
		String name1 = "qian";
		return name1;
	}
	
	public static String getPassword(){
		String password = "xiang123";
		return password;
	}
}
