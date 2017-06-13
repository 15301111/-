package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.regexp.internal.recompile;*/

import Entity.ConProcess;
import Entity.ConState;
import Entity.Contract;
import Entity.User;;

public class DBUtil {

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
			//System.out.println(String.format("Connect to database %s" + " successfully", conn.getCatalog()));

			while (res.next()) {
				if (res.getString("name").equals(name)) {
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

	public boolean AddContract(Contract contract){
		boolean successflag = false;
		
		ResultSet rs = null;
		
		String sql = "Insert into t_contract(user_id, customer, name, content, beginTime, endTime) values(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, contract.getId());
			pstmt.setString(2, contract.getCustomer());
			pstmt.setString(3, contract.getName());
			pstmt.setString(4, contract.getContent());
			
			java.sql.Date beginTime = new java.sql.Date(contract.getBeginTime().getTime());
			java.sql.Date endTime = new java.sql.Date(contract.getEndTime().getTime());
			pstmt.setDate(5, beginTime);
			pstmt.setDate(6, endTime);
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			while(rs.next()){
				contract.setId(rs.getInt(1));
				successflag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return successflag;
	}
	
	public boolean AddConstate(ConState constate){
		boolean addsuccess = false;
		String sql = "insert into t_contract_state(con_id, type, time) values(?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, constate.getId());
			pstmt.setInt(2, constate.getType());
			java.sql.Date Time = new java.sql.Date(constate.getTime().getTime());
			 pstmt.setDate(3, Time);
			
			int result = pstmt.executeUpdate();
			if(result > 0 ){
				addsuccess = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return addsuccess;
	}

	
	public List<Integer> getIdByType(int type){
		List<Integer> conIDs  = new ArrayList<Integer>();
		ResultSet rs = null;
		String sql = "select con_id from t_contract_state where type = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, type);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				conIDs.add(rs.getInt("con_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conIDs;
	}
	
	public boolean CheckconById(int id) {
		boolean checkflag = false;

		String sql = "select * from t_contract";

		try (Statement stmt = conn.createStatement(); ResultSet res = stmt.executeQuery(sql);) {
			System.out.println(String.format("Connect to database %s" + " successfully", conn.getCatalog()));

			while (res.next()) {
				if (res.getString("id").equals(id)) {
					System.out.println(id + "exist!");
					checkflag = true;
				}
				// System.out.println(res.getString("username")+res.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return checkflag;
	}
	
	public Contract getContract(int id){
		Contract contract = new Contract();
		ResultSet rs = null;
		String sql = "select * from t_contract where id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				contract.setId(rs.getInt("id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setName(rs.getString("name"));
				contract.setContent(rs.getString("content"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return contract;
	}
	
	public ConState getConstate(int conid){
		ConState constate = new ConState();
		
		ResultSet rs = null;
		String sql = "select * from t_contract_state where con_id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, conid);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				constate.setId(rs.getInt("con_id"));
				constate.setType(rs.getInt("type"));
				constate.setTime(rs.getDate("time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return constate;
	}
	
	public boolean addProcess(ConProcess conprocess){
		boolean flag = false;
		String sql = "insert into t_contract_process(con_id, user_id, type, state) values(?, ?, ?, ?);";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, conprocess.getConId());
			pstmt.setInt(2, conprocess.getUserId());
			pstmt.setInt(3, conprocess.getType());
			pstmt.setInt(4, conprocess.getState());
			
			int result = pstmt.executeUpdate();
			if(result > 0 ){
				flag = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	public List<Integer> getUserIDsByRoleId(String role){
		List<Integer> userIDs= new ArrayList<>();
		ResultSet rs = null;
		String sql = "select id from t_user where urole = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, role);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				userIDs.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userIDs;
	}
	
	public User getByID(int id){
		ResultSet rs = null;
		User user = new User();
		String sql = "select * from  t_user where id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}
}
