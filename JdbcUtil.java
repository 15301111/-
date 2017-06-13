/** 
* @author: 谢明霁
* @date：2017年3月12日 上午11:12:01
* @version 1.0 
*/
package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class JdbcUtil {
	//自定义一个数据库连接池的私有内部类
	private static class JdbcPool implements DataSource {
		private static LinkedList<Connection> listConnections = new LinkedList<>();
		static {
			InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
			Properties properties = new Properties();
			try {
				properties.load(in);
				String driver = properties.getProperty("driver");
				String url = properties.getProperty("url");
				String username = properties.getProperty("username");
				String password = properties.getProperty("password");

				int jdbcPoolInitSize = Integer.parseInt(properties.getProperty("jdbcPoolInitSize"));
				// 加载数据库驱动
				Class.forName(driver);
				for (int i = 0; i < jdbcPoolInitSize; i++) {
					Connection conn = DriverManager.getConnection(url, username, password);
					System.out.println("获取到了链接" + conn);
					// 将获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池
					listConnections.add(conn);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return null;
		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return 0;
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return null;
		}

		@Override
		public void setLogWriter(PrintWriter arg0) throws SQLException {

		}

		@Override
		public void setLoginTimeout(int arg0) throws SQLException {

		}

		@Override
		public boolean isWrapperFor(Class<?> arg0) throws SQLException {
			return false;
		}

		@Override
		public <T> T unwrap(Class<T> arg0) throws SQLException {
			return null;
		}

		@Override
		public Connection getConnection() throws SQLException {
			//如果数据库连接池中的连接对象的个数大于0
			if(listConnections.size()>0){
				//从listConnections集合中获取一个数据库连接
				final Connection connection = listConnections .removeFirst();
				System.out.println("listConnections数据库连接池大小是"+listConnections.size());
				//返回Connection对象的代理对象
				return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), new Class[] { Connection.class }, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if(!method.getName().equals("close")){
							return method.invoke(connection, args);
						}else{
							//如果调用的是Connection对象的close方法，就把connection还给数据库连接池
							listConnections.add(connection);
							System.out.println(connection+"被还给数据库连接池了!");
							System.out.println("listConnections数据库连接池大小是"+listConnections.size());
							return null;
						}
					}
				});
			}else{
				throw new RuntimeException("对不起，数据库忙");
			}
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return null;
		}
	}

	private static JdbcPool pool = new JdbcPool();

	public static Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	public static void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				// 关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				// 关闭Connection数据库连接对象
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		JdbcUtil.getConnection();
	}
}
