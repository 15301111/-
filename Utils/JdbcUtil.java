/** 
* @author: л����
* @date��2017��3��12�� ����11:12:01
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
	//�Զ���һ�����ݿ����ӳص�˽���ڲ���
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
				// �������ݿ�����
				Class.forName(driver);
				for (int i = 0; i < jdbcPoolInitSize; i++) {
					Connection conn = DriverManager.getConnection(url, username, password);
					System.out.println("��ȡ��������" + conn);
					// ����ȡ�������ݿ����Ӽ��뵽listConnections�����У�listConnections���ϴ�ʱ����һ����������ݿ����ӵ����ӳ�
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
			//������ݿ����ӳ��е����Ӷ���ĸ�������0
			if(listConnections.size()>0){
				//��listConnections�����л�ȡһ�����ݿ�����
				final Connection connection = listConnections .removeFirst();
				System.out.println("listConnections���ݿ����ӳش�С��"+listConnections.size());
				//����Connection����Ĵ������
				return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), new Class[] { Connection.class }, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if(!method.getName().equals("close")){
							return method.invoke(connection, args);
						}else{
							//������õ���Connection�����close�������Ͱ�connection�������ݿ����ӳ�
							listConnections.add(connection);
							System.out.println(connection+"���������ݿ����ӳ���!");
							System.out.println("listConnections���ݿ����ӳش�С��"+listConnections.size());
							return null;
						}
					}
				});
			}else{
				throw new RuntimeException("�Բ������ݿ�æ");
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
				// �رմ洢��ѯ�����ResultSet����
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// �رո���ִ��SQL�����Statement����
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				// �ر�Connection���ݿ����Ӷ���
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
