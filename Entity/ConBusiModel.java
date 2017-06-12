package Entity;

import java.util.Date;

public class ConBusiModel {

	private int conID;
	private String conName;
	private Date draftTime;
	
	public ConBusiModel(){
		conID = 0;
		conName = "";
		this.draftTime = new Date(); 
	}
	
	public int getConId() {
		return conID;
	}

	public void setConId(int conId) {
		this.conID = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public Date getDrafTime() {
		return draftTime;
	}

	public void setDrafTime(Date drafTime) {
		this.draftTime = drafTime;
	}
}
