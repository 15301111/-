package Entity;

import java.util.Date;

public class CBEntity {

	private int conId; 			
	private String conName;
	private Date drafTime;
	private int breach;
	
	public CBEntity() {
		this.conId = 0;
		this.conName = "";
		this.drafTime = new Date();
		this.breach = 0;
	}
	
	public int getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public Date getDrafTime() {
		return drafTime;
	}

	public void setDrafTime(Date drafTime) {
		this.drafTime = drafTime;
	}
	
	public int getbreach() {
		return breach;
	}

	public void setbreach(int conId) {
		this.breach = conId;
	}
	
}
