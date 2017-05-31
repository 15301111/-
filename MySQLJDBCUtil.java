import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLJDBCUtil {

	public static Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			FileInputStream f = new FileInputStream("db.properties");
			
			Properties pros = new Properties();
			
			pros.load(f);
			
			String url = pros.getProperty("url");
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			
			//System.out.println(url);
			//System.out.println(password);
			//System.out.println(user);
			
			conn = DriverManager.getConnection(url, user, password);
		} catch (FileNotFoundException e1) {
		System.out.println(e1.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		
	
	
		return conn;
	}
}
