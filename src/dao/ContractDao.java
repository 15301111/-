package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entity.ConProcess;
import Entity.ConState;
import Entity.Contract;
import Utils.AppException;
import Utils.DateUtil;
import Utils.JdbcUtil;

public class ContractDao {
	private Connection conn = null;
	private PreparedStatement pstate = null;
	
	DateUtil dateutil = new DateUtil();
	
	public boolean insertProcess(ConProcess conProcess) {
		try {
			conn = JdbcUtil.getConnection();
			//pstate = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, null);
		}

		return false;
	}

	public List<Integer> getIdByType(int type) {
		List<Integer> conIDs = new ArrayList<Integer>();
		ResultSet rs = null;
		String sql = "select id from t_contract_state where type = ?";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, type);

			rs = pstate.executeQuery();

			while (rs.next()) {
				conIDs.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return conIDs;
	}

	public boolean insertContract(Contract contract) {
		boolean successflag = false;
		String sql = "Insert into t_contract(user_id, customer, name, content, begintime, endtime) values(?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstate.setInt(1, contract.getUserId());
			pstate.setString(2, contract.getCustomer());
			pstate.setString(3, contract.getName());
			pstate.setString(4, contract.getContent());

			java.sql.Date beginTime = new java.sql.Date(contract.getBeginTime().getTime());
			java.sql.Date endTime = new java.sql.Date(contract.getEndTime().getTime());
			pstate.setDate(5, beginTime);
			pstate.setDate(6, endTime);

			pstate.executeUpdate();
			rs = pstate.getGeneratedKeys();

			while (rs.next()) {
				contract.setId(rs.getInt(1));
				successflag = true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return successflag;
	}

	

	public boolean CheckConById(int id) {
		boolean checkflag = false;
		String sql = "select * from t_contract";
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equals(id)) {
					System.out.println(id + "exist!");
					checkflag = true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return checkflag;
	}

	public Contract getContract(int id) {
		Contract contract = new Contract();
		ResultSet rs = null;
		String sql = "select * from t_contract where id = ?";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);

			rs = pstate.executeQuery();
			while (rs.next()) {
				contract.setId(rs.getInt("id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setName(rs.getString("name"));
				contract.setContent(rs.getString("content"));
				contract.setBeginTime(rs.getDate("begintime"));
				contract.setEndTime(rs.getDate("endtime"));
				contract.setBreach(rs.getInt("breach"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return contract;
	}

	public List<Contract> getContracts(Integer userId) {
		List<Contract> ids = new ArrayList<Contract>();
		// Declare database connection object, pre-compiled object and result set object
		ResultSet rs = null;
		try {
			// Create database connection
			conn = JdbcUtil.getConnection();
			// Declare operation statement,query contract id according to user id, "?" is a Placeholder
			String sql = "select * "
					+"from t_contract";
			// Pre-compiled sql
			pstate = conn.prepareStatement(sql);
			// Query result set
			rs = pstate.executeQuery();
			
			// Get information in result set by loop,and save it to conIds
			while (rs.next()) {
				Contract contract = new Contract();
				contract.setId(rs.getInt("id"));
				contract.setName(rs.getString("name"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
				contract.setContent(rs.getString("content"));
				contract.setBreach(rs.getInt("breach"));
				ids.add(contract);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		return ids;
	}

	public Contract getContract(String contractname) {
		Contract contract = new Contract();
		ResultSet rs = null;
		String sql = "select * from t_contract where name = ?";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, contractname);
			
			rs = pstate.executeQuery();
			while (rs.next()) {
				contract.setId(rs.getInt("id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setName(rs.getString("name"));
				contract.setContent(rs.getString("content"));
				contract.setBeginTime(rs.getDate("begintime"));
				contract.setEndTime(rs.getDate("endtime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return contract;
	}

	public void deleteContract(String name) {
		try {
			conn=JdbcUtil.getConnection();
			String sql = "delete from t_contract where name = ?";
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, name);
			pstate.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Contract getById(int id) throws AppException {
		// Declare contract
		Contract contract = null;
		
		//Declare Connection object,PreparedStatement object  and ResultSet object
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			// Create database connection
			conn = JdbcUtil.getConnection();
			//Define SQL statement: query contract information according to the contract id 
			String sql = "select id,name,user_id,customer,beginTime,endTime,content "
					+"from t_contract "
					+"where id = ? and del = 0";

			//Pre-compiled sql, and set the parameter values
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, id); //Set contract id
			
			//  Query result set
			rs = psmt.executeQuery();

			//Get information in result set by loop,and encapsulated into contract object
			if(rs.next()) {
				contract = new Contract();
				contract.setId(rs.getInt("id"));
				contract.setName(rs.getString("name"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setBeginTime(rs.getDate("beginTime"));
				contract.setEndTime(rs.getDate("endTime"));
				contract.setContent(rs.getString("content"));	
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
					"com.ruanko.dao.impl.ContractDaoImpl.getById");
		} finally {
			// Close the database operation object, release resources
			JdbcUtil.release(conn, pstate, rs);
		}
		return contract;
	}
	
	public List<Contract> getallContract() {

		List<Contract> contractlist = new ArrayList<Contract>();
		String sql = "select * from t_contract ";
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
			while (rs.next()) {
				Contract contract = new Contract();
				contract.setId(rs.getInt("id"));
				contract.setUserId(rs.getInt("user_id"));
				contract.setCustomer(rs.getString("customer"));
				contract.setName(rs.getString("name"));
				contract.setBeginTime(dateutil.sql2util(rs.getDate("beginTime")));
				contract.setEndTime(dateutil.sql2util(rs.getDate("endTime")));
				contract.setContent(rs.getString("content"));
				contractlist.add(contract);
				System.out.println("查询成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}
		
		return contractlist;
	}
	
	public boolean updateEndTimeByname(String name, Date newtime) {
		boolean flag = false;
		String sql = "update t_contract set endTime = ? where name = ?";
		
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setDate(1, dateutil.util2sql(newtime));
			pstate.setString(2, name);
			
			int result  = pstate.executeUpdate();
			if(result > 0){
				flag = true;
				System.out.println("更新成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public ConState getAssignConstate(int conID) {
		// TODO Auto-generated method stub
		ConState constate = new ConState();
		ResultSet rs = null;
		String sql = "select * from t_contract_state where con_id = ?";

		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, conID);
			rs = pstate.executeQuery();

			while (rs.next()) {
				constate.setId(rs.getInt("con_id"));
				constate.setType(rs.getInt("type"));
				constate.setTime(rs.getDate("time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstate, rs);
		}

		return constate;

	}

}
