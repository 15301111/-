package Entity;

import java.util.Date;

public class Constate {
	private int id;
	private int type;
	private Date time;
	
	public Constate(){
		id = 0;
		type = 0;
	    time = new Date();
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public void setType(int state){
		this.type = state;
	}
	
	public int getType(){
		return type;
	}
	
	public void setTime(Date time){
		this.time = time;
	}
	
	public Date getTime(){
		return time;
	}

}
