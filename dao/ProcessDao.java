package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Entity.ConProcess;
import Utils.AppException;
import Utils.JdbcUtil;

public class ProcessDao {
	private Connection conn = null;
	private PreparedStatement pstate = null;
	
	public boolean insertProcess(ConProcess conprocess) {
		boolean flag = false;
		String sql = "insert into t_contract_process(con_id, user_id, type, state) values(?, ?, ?, ?);";
		try {
			conn = JdbcUtil.getConnection();
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, conprocess.getConId());
			pstate.setInt(2, conprocess.getUserId());
			pstate.setInt(3, conprocess.getType());
			pstate.setInt(4, conprocess.getState());

			int result = pstate.executeUpdate();
			if (result > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.release(conn, pstate, null);
		}

		return flag;
	}
	
	public List<Integer> getConIds(ConProcess conProcess) throws AppException {
		List<Integer> acIds = new ArrayList<Integer>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			
			String sql = "select con_id from t_contract_process " +
					"where user_id= ? and type = ? and state = ? and del=0";
			
			//完成sql语句
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conProcess.getUserId());
			psmt.setInt(2, conProcess.getType());
			psmt.setInt(3, conProcess.getState());
			
			//从数据库获取信息
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				acIds.add(rs.getInt("con_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
			"ConProcessDao.getConIds");
		} finally {
			JdbcUtil.release(conn, psmt, rs);
		}
		return acIds;
	}
	
	public boolean update(ConProcess conProcess) throws AppException {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "update t_contract_process set state = ?, content = ?, time = ? " 
					+"where user_id = ? and con_id = ? and type = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conProcess.getState());
			psmt.setString(2, conProcess.getContent());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.sql.Timestamp time = new java.sql.Timestamp(conProcess.getTime().getTime());
			df.format(time);
			psmt.setTimestamp(3, time);
			psmt.setInt(4, conProcess.getUserId());
			psmt.setInt(5, conProcess.getConId());
			psmt.setInt(6, conProcess.getType());
			//返回执行成功的记录条数
			int count = psmt.executeUpdate();
			
			if (count > 0) {
				flag = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("com.ruanko.dao.impl.ConProcessDaoImpl.update");
		} finally {
			JdbcUtil.release(conn, psmt,null);
		}
		return flag;
	}
	
	public boolean updateType(ConProcess conProcess) throws AppException {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "update t_contract_process set type = ? " 
					+"where user_id = ? and con_id = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conProcess.getType());

			psmt.setInt(2, conProcess.getUserId());
			psmt.setInt(3, conProcess.getConId());
			//返回执行成功的记录条数
			int count = psmt.executeUpdate();
			
			if (count > 0) {
				flag = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("ConProcessDao.update");
		} finally {
			JdbcUtil.release(conn, psmt,null);
		}
		return flag;
	}
	
	public int getTotalCount(ConProcess conProcess) throws AppException{
		int totalCount = 0;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select count(id) as n from t_contract_process "
				 +"where con_id = ? and type = ? and state = ?";
				
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conProcess.getConId());
			psmt.setInt(2, conProcess.getType());
			psmt.setInt(3, conProcess.getState());
			rs = psmt.executeQuery();
			rs.next();
			totalCount =  rs.getInt("n");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(
			"ConProcessDao.getTotalCount");
		} finally {
			JdbcUtil.release(conn, psmt, rs);
		}
		return totalCount;
	}
}
