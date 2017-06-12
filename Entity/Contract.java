package Entity;

import java.util.Date;

public class Contract {
	private int id;
	private int userID;
	private String customername;
	//private String number;
	private String contractname;
	private Date begintime;
	private Date endtime;
	private String content;
	
	
	public Contract(){
		id = 0;
		userID = 0;
		customername = "";
		//number = "";
		contractname = "";
		begintime = new Date();
		endtime = new Date();
		content = "";
	}
	
	public void setId(int ID){
		this.id = ID;
	}
	
	public int getId(){
		return id;
	}

	public int getUserId() {
		return userID;
	}

	public void setUserId(int userId) {
		this.userID = userId;
	}

	public String getCustomer() {
		return customername;
	}

	public void setCustomer(String customer) {
		this.customername = customer;
	}

	//public String getNum() {
	//	return number;
	//}

	//public void setNum(String num) {
	//	this.number = num;
	//}

	public String getName() {
		return contractname;
	}

	public void setName(String name) {
		this.contractname = name;
	}

	public Date getBeginTime() {
		return begintime;
	}

	public void setBeginTime(Date beginTime) {
		this.begintime = beginTime;
	}

	public Date getEndTime() {
		return endtime;
	}

	public void setEndTime(Date endTime) {
		this.endtime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
