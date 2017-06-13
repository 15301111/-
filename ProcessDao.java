package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Entity.ConProcess;

public class ProcessDao {
	private Connection conn = null;
	private PreparedStatement pstate = null;
	
	public boolean insertProcess(ConProcess conprocess) {
		boolean flag = false;
		String sql = "insert into conprocess(conid, userid, type, state) values(?, ?, ?, ?);";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, conprocess.getConId());
			pstmt.setInt(2, conprocess.getUserId());
			pstmt.setInt(3, conprocess.getType());
			pstmt.setInt(4, conprocess.getState());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}
}
