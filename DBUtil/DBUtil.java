package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.org.apache.regexp.internal.RE;

public class DBUtil {

	Connection conn = JDBC.getConnection();

	public boolean InsertInfo(String name, String password) {
		boolean insertflag = false;
		ResultSet rs = null;

		String sql = "INSERT INTO user(username, password) VALUES (?,?);";

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

		String sql = "select * from user";

		try (Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery(sql);) {
			System.out.println(String.format("Connect to database %s" + " successfully", conn.getCatalog()));

			while (res.next()) {
				if (res.getString("username").equals(name)) {
					System.out.println(name + "exist!");
					checkflag = true;
				}
				// System.out.println(res.getString("username")+res.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return checkflag;
	}

public boolean Login(String name,String password){
	boolean matchflag = false;
	    ResultSet rs = null;
		String sql = "SELECT * From user Where username = ?";
    try(PreparedStatement pstmt = conn.prepareStatement(sql);){
	    pstmt.setString(1, name);

	    rs = pstmt.executeQuery();
	while(rs.next()){
		if(rs.getString("username").equals(name)&&rs.getString("password").equals(password)){
			matchflag = true;
		}
	}
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		return matchflag;
	}

}
