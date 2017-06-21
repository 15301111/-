package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.ConProcess;
import Entity.Contract;
import Entity.User;
import Utils.AppException;
import Utils.JdbcUtil;

public class UserDao {
	private Connection conn = null;
	private PreparedStatement pstate = null;

	public boolean insertUser(String name, String password) {
		boolean insertflag = false;
		ResultSet rs = null;

		String sql = "INSERT INTO t_user(name, password, urole) VALUES (?,?,?);";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstate.setString(1, name);
			pstate.setString(2, password);
			pstate.setString(3, "operator");

			int rowAffected = pstate.executeUpdate();

			if (rowAffected == 1) {
				insertflag = true;
				// System.out.println("注册成功！");
				rs = pstate.getGeneratedKeys(); // 获取结果
				// if(rs.next()){
				// userID = rs.getInt(1); //得到id
				// }
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return insertflag;

	}

	public boolean Check(String name) {
		boolean checkflag = false;
		ResultSet rs = null;
		String sql = "select * from t_user";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("name").equals(name)) {
					System.out.println(name + "exist!");
					checkflag = true;
				}
				// System.out.println(res.getString("username")+res.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return checkflag;
	}

	public boolean Login(String name, String password) {
		boolean matchflag = false;
		ResultSet rs = null;
		String sql = "SELECT * From t_user Where name = ?";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, name);

			rs = pstate.executeQuery();
			while (rs.next()) {
				if (rs.getString("name").equals(name) && rs.getString("password").equals(password)) {
					matchflag = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return matchflag;
	}

	public String getRole(int userID) {
		String dbrole = "";
		ResultSet rs = null;
		String sql = "select * from t_user where id = ?";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, userID);

			rs = pstate.executeQuery();

			while (rs.next()) {
				if (rs.getInt("id") == userID) {
					dbrole = rs.getString("urole");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return dbrole;
	}

	public int getUserID(String name) {

		int dbuserID = 0;

		ResultSet rs = null;
		String sql = "Select * from t_user where name = ?";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, name);
			rs = pstate.executeQuery();

			while (rs.next()) {
				if (rs.getString("name").equals(name)) {
					dbuserID = rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return dbuserID;
	}



	public List<Integer> getUserIDsByRole(String role) {
		List<Integer> userIDs = new ArrayList<>();
		ResultSet rs = null;
		String sql = "select id from t_user where urole = ?";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, role);

			rs = pstate.executeQuery();
			while (rs.next()) {
				userIDs.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return userIDs;
	}

	public User getByID(int id) {
		ResultSet rs = null;
		User user = new User();
		String sql = "select * from  t_user where id = ?";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);

			rs = pstate.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return user;
	}
	
	public List<Integer> getIds() throws AppException {
		// Initialiaze ids
		List<Integer> ids = new ArrayList<Integer>();
		
		//Declare Connection object,PreparedStatement object and ResultSet object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = JdbcUtil.getConnection();
			// Declare operation statement:query user id set,"?" is a placeholder
			String sql = "select id from t_user where del = 0";
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();// Return result set
			// Loop to get information in result set,and save in ids
			while (rs.next()) {
				ids.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
					"com.ruanko.dao.impl.UserDaoImpl.getIds");
		} finally {
			// Close database operation object, release resources
			JdbcUtil.release(conn, pstate, rs);
		}
		return ids;
	}
	
	public boolean updateNewpassword(int userid, String password1) {
		boolean flag = false;
		String sql = "update t_user set password = ? where id = ?";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, password1);
			pstate.setInt(2, userid);

			int result = pstate.executeUpdate();

			if (result > 0) {
				System.out.println("密码修改成功");
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, null);
		}
		return flag;
	}

	public User getUserByid(Integer userId) {
		User user = new User();
		String sql = "select * from t_user where id = ?";
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, userId);
			
			rs = pstate.executeQuery();
			while(rs.next()){
				user.setId(userId);
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return user;
	}

}
