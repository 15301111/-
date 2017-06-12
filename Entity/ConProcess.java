package Entity;

import java.util.Date;

public class ConProcess {

	
	private int id;			   
	private int conId;			
	private int userId;			
	private int type;			
	private int state;			
	//private String content;		
	//private Date time;			
	
	
	
	public ConProcess(){
		this.id = 0;
		this.conId = 0;
		this.userId = 0;
		this.type = 0;
		this.state = 0;
		//this.content = "";
		//this.time = new Date();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	//public String getContent() {
	//	return content;
	//}

	//public void setContent(String content) {
	//	this.content = content;
	//}
	
	//public Date getTime() {
	//	return time;
	//}

	//public void setTime(Date time) {
	//	this.time = time;
	//}

}
