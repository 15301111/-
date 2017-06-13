package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.sun.org.apache.regexp.internal.RE;

public class DBUtill {

	Connection conn = JDBC.getConnection();

	public boolean InsertInfo(String name, String password) {
		boolean insertflag = false;
		ResultSet rs = null;

		String sql = "INSERT INTO t_user(name, password) VALUES (?,?);";

		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, name);
			pstmt.setString(2, password);

			int rowAffected = pstmt.executeUpdate();

			if (rowAffected == 1) {
				insertflag = true;
				// System.out.println("注册成功！");
				rs = pstmt.getGeneratedKeys(); // 获取结果
				// if(rs.next()){
				// userID = rs.getInt(1); //得到id
				// }
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());

			}
		}
		return insertflag;

	}

	public boolean Check(String name) {
		boolean checkflag = false;

		String sql = "select * from t_user";

		try (Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery(sql);) {
			System.out.println(String.format("Connect to database %s" + " successfully", conn.getCatalog()));

			while (res.next()) {
				if (res.getString("name").equals(name)) {
					System.out.println(name + "exist!");
					checkflag = true;
				}
				// System.out.println(res.getString("name")+res.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return checkflag;
	}

	public boolean Login(String name,String password){
		boolean matchflag = false;
		ResultSet rs = null;
		String sql = "SELECT * From t_user Where name = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("name").equals(name)&&rs.getString("password").equals(password)){
					matchflag = true;
					}
				}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return matchflag;
		}
	
	public static void closeConnection(Connection conn) {
		try {
			if ((conn != null) && (!conn.isClosed())) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement st) {
		try {
			if ((st != null) && (!st.isClosed())) {
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if ((rs != null) && (!rs.isClosed())) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getUserID(String name){

		int dbuserID = 0;
		
		ResultSet rs = null;
		String sql = "Select * from t_user where name = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString("name").equals(name)){
					dbuserID =rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbuserID;
	}
	
	public String getRolenifo(int userID){
		String dbrole = "";
		ResultSet rs = null;
		String sql = "select * from t_user where id = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, userID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				if(rs.getInt("id") == userID){
					dbrole =rs.getString("urole");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbrole;
	}
}
