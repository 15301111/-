package Entity;

public class User {

	private int id;
	String name;
	String password;
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	public void setName(String Name){
		this.name = Name;
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
