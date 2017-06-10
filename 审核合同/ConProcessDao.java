package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entity.ConProcess;
import Utils.AppException;
import Utils.DBUtil;
import Utils.JDBC;

/**
 * Contract process data access layer implementation class
 */
public class ConProcessDao{

	//获得t_contract_process数据
	public List<Integer> getConIds(ConProcess conProcess) throws AppException {
		List<Integer> acIds = new ArrayList<Integer>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBC.getConnection();
			
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
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(psmt);
			DBUtil.closeConnection(conn);
		}
		return acIds;
	}
}