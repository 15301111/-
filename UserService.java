package Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Utils.DBUtil;
import Utils.JDBC;

public class UserService {



public boolean register(String name,String password) {
	
	boolean registflag = false;
	boolean existflag = false;
	
	DBUtil util = new DBUtil();
	existflag = util.Check(name);
    registflag = util.InsertInfo(name, password);
    
	return registflag&&(!existflag);
}
 	

}
