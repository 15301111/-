package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ConStateDao;
import Entity.ConState;
import Utils.AppException;
import Utils.DBUtill;
import Utils.JDBC;

//获取t_contract_state数据
public class ConStateDao{
	boolean flag = false;	
		
	public ConState getConState(int conId, int type) throws AppException {
		ConState conState = new ConState();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBC.getConnection();
			String sql = "select id,con_id,type,time "
			+"from t_contract_state "
					+"where con_id = ? and type = ? and del = 0";

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conId);
			psmt.setInt(2, type);
			
			rs = psmt.executeQuery();

			if(rs.next()) {
				conState.setId(rs.getInt("id"));
				conState.setConId(rs.getInt("con_id"));
				conState.setType(rs.getInt("type"));
				conState.setTime(rs.getDate("time"));
				}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
					"ConStateDao.getByConId");
			} finally {
				DBUtill.closeResultSet(rs);
				DBUtill.closeStatement(psmt);
				DBUtill.closeConnection(conn);
				}
		return conState;
		}
	
	public boolean isExist(int con_id, int type) throws AppException {
		boolean flag = false;
		
		//jdbc
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBC.getConnection();
			String sql = "select count(id) as n from t_contract_state "
				 +"where con_id = ? and type = ? and del = 0";
			
			//完成sql语句
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, con_id);
			psmt.setInt(2, type);

			rs = psmt.executeQuery();
			rs.next();
			int n = rs.getInt("n");
			if (n > 0) {
				flag = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
			"ConStateDao.isExist");
		} finally {
			DBUtill.closeResultSet(rs);
			DBUtill.closeStatement(psmt);
			DBUtill.closeConnection(conn);
		}
		return flag;
	}
	
	public boolean add(ConState conState) throws AppException{	
		boolean flag = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = JDBC.getConnection();
			String sql = "insert into t_contract_state(con_id,type) values(?,?)";
				
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conState.getConId());
			psmt.setInt(2, conState.getType());
		
			int result = psmt.executeUpdate();
			
			if(result > 0){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
			"ConStateDao.add");
		} finally {
			DBUtill.closeStatement(psmt);
			DBUtill.closeConnection(conn);
		}
		return flag;
	}

}
