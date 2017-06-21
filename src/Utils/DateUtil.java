/** 
* @author: л����
* @date��2017��3��12�� ����8:07:08
* @version 1.0 
*/
package Utils;

import java.sql.Timestamp;

public class DateUtil {
	private final static long millisecondsOfOneDay = 1000 * 60 * 60 * 24;
	
	public static java.sql.Date util2sql(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	public static java.util.Date sql2util(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}
	//����ʱ��Ϊһ���
	public static Timestamp generateExpiredTime(){
		return new Timestamp(new java.util.Date().getTime()+millisecondsOfOneDay);
	}
}
