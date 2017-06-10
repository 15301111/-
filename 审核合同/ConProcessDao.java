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

	//���t_contract_process����
	public List<Integer> getConIds(ConProcess conProcess) throws AppException {
		List<Integer> acIds = new ArrayList<Integer>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBC.getConnection();
			
			String sql = "select con_id from t_contract_process " +
					"where user_id= ? and type = ? and state = ? and del=0";
			
			//���sql���
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, conProcess.getUserId());
			psmt.setInt(2, conProcess.getType());
			psmt.setInt(3, conProcess.getState());
			
			//�����ݿ��ȡ��Ϣ
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