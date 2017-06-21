package Entity;

public class User {

	String name;
	String password;
	private int id;
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String Name){
		this.name = Name;
	}
	
	public int getId(){
		return id;
	}
	
	public void setPassword(String Password){
		this.password = Password;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
}
