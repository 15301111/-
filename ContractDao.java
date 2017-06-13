package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Entity.ConProcess;
import Entity.Constate;
import Entity.Contract;

public class ContractDao {
	private Connection conn = null;
	private PreparedStatement pstate = null;

	public boolean addProcess(ConProcess conProcess) {
		return false;
	}

	public List<Integer> getIdByType(int stateDrafted) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insertContract(Contract contract) {
		boolean successflag = false;

		ResultSet rs = null;

		String sql = "Insert into contract(id, customer, name, content, begintime, endtime) values(?, ?, ?, ?, ?, ?)";
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

			while (rs.next()) {
				contract.setId(rs.getInt(1));
				successflag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successflag;
	}

	public boolean insertConstate(Constate constate) {
		boolean addsuccess = false;
		String sql = "insert into contract_state(id, type, time) values(?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, constate.getID());
			pstmt.setInt(2, constate.getType());
			java.sql.Date Time = new java.sql.Date(constate.getTime().getTime());
			pstmt.setDate(3, Time);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				addsuccess = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addsuccess;
	}

	public boolean CheckConById(int id) {
		boolean checkflag = false;

		String sql = "select * from contract";

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

	public Contract getContract(int id) {
		Contract contract = new Contract();
		ResultSet rs = null;
		String sql = "select * from contract where id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
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
		}

		return contract;
	}

	public Constate getConstate(int conid) {
		Constate constate = new Constate();

		ResultSet rs = null;
		String sql = "select * from contract_state where id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, conid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				constate.setID(rs.getInt("id"));
				constate.setType(rs.getInt("type"));
				constate.setTime(rs.getDate("time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return constate;
	}
}
