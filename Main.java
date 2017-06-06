import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public class Main {
            public static void main(String[] args){
            	/*
            	String sql = "SELECT first_name, last_name,email FROM candidates";
            	try(Connection conn = MySQLJDBCUtil.getConnection();
            		Statement stmt  = conn.createStatement();
            		ResultSet rs       = stmt.executeQuery(sql);) {
            		System.out.println(String.format("Connect to database %s"+" successfullly.",conn.getCatalog()));
            		while(rs.next()){
            			System.out.println(rs.getString("first_name")+"\t"+rs.getString("last_name")+"\t"+rs.getString("email"));
            		}
					
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(ex.getNextException());
				}*/
            	
            	//更新数据
            	//update();
            	
            	
            	//插入数据
            	/*
            	int id = InserCandidate("Bush", "Lily", Date.valueOf("1980-01-04"), 
                        "bush.l@yahoo.com", "(408) 898-6666");
         
                   System.out.println(String.format("A new candidate with id %d has been inserted.",id));*/
            	//transition
            	/*
            	  int[] skills = {1,2,3};
            	  addCandidate("John", "Doe", Date.valueOf("1990-01-04"), 
                          "john.d@yahoo.com", "(408) 898-5641", skills);*/
            	//调用存储过程
            	 //getSkills(136);
            	
            	//读
            	//writeBlob(136, "johndoe_resume.pdf");
            	
            	//写
            	//readBlob(136, "johndoe_resume_from_db.pdf");
            }
            
            public static void update(){
            	String sqlUpdate = "UPDATE candidates SET last_name = ? WHERE id = ?;";
            	try (
					Connection conn = MySQLJDBCUtil.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)){
            		
            		String lastName = "william";
            		int id = 100;
            		pstmt.setString(1, lastName);
            		pstmt.setInt(2,id);
            		
            		int rowAffected = pstmt.executeUpdate();
            		System.out.println(String.format("Row affected %d", rowAffected));
            		
            		lastName = "Grohe";
            		id = 101;
            		pstmt.setString(1, lastName);
            		pstmt.setInt(2, id);
            		
            		rowAffected = pstmt.executeUpdate();
            		System.out.println(String.format("Row affected %d", rowAffected));
            		
				} catch (SQLException e) {
					
				System.out.println(e.getMessage());	
				}
            	
            }
            
            public static int InserCandidate(String firstname,String lastname,Date dob, String email, String phone){

				
            	ResultSet rs = null;
            	int candidateId = 0;
            	
            	String sql = "INSERT INTO candidates(first_name, last_name, dob, phone, email) VALUES(?, ?, ?, ?, ?);";
            	
            	try (	Connection conn = MySQLJDBCUtil.getConnection();
            			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS))
				{
					pstmt.setString(1, firstname);
					pstmt.setString(2, lastname);
					pstmt.setDate(3, dob);
					pstmt.setString(4, phone);
					pstmt.setString(5, email);
					
					
					int rowAffected = pstmt.executeUpdate();
					
					if(rowAffected ==1 ){
						rs = pstmt.getGeneratedKeys();
						if(rs.next())
							candidateId = rs.getInt(1);
						
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					
				}finally{
						try {
							if(rs != null)
							rs.close();
						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
							
						}
						
				}
            	
            	
            	return candidateId;
            	
            }
            
            public static void addCandidate(String firstname, String lastname, Date dob, String email, String phone, int[] skills){
            	
            	Connection conn = null;
            	
            	PreparedStatement pstmt = null;
            	
            	PreparedStatement pstmtAssignment = null;
            	
            	ResultSet rs = null;
            	
            	
            	try {
					conn = MySQLJDBCUtil.getConnection();
					conn.setAutoCommit(false);
					
					String sqlInsert = "INSERT INTO candidates(first_name, last_name, dob, phone, email) VALUES(?, ?, ?, ?, ?);";
					
					   pstmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
					   
					pstmt.setString(1, firstname);
					pstmt.setString(2, lastname);
					pstmt.setDate(3, dob);
					pstmt.setString(4, email);
					pstmt.setString(5, phone);
					
					int rowAffected = pstmt.executeUpdate();
					
					rs = pstmt.getGeneratedKeys();
					
					int CandidateId = 0;
					
					if(rs.next()){
						CandidateId = rs.getInt(1);
					}
					
					if(rowAffected == 1){
						String sqlpivot = "INSERT INTO candidate_skills(candidate_id, skill_id) VALUES(?, ?)";
						
						pstmtAssignment = conn.prepareStatement(sqlpivot);
						
						for(int skillId :skills){
							pstmtAssignment.setInt(1, CandidateId);
							pstmtAssignment.setInt(2, skillId);
							
							pstmtAssignment.executeUpdate();
						}
						
						conn.commit();
					}else{
						conn.rollback();
					}
				} catch (SQLException ex) {
					
					try{
						if(conn != null)
							conn.rollback();
					}catch(SQLException e){
						System.out.println(e.getMessage());
					}
					System.out.println(ex.getMessage());
				}
            	
            
            }

           public static void getSkills(int candidateId){
        	   String query = "{ call get_candidate_skill(?)}";
        	   ResultSet rs;
        	   
        	   try(Connection conn =MySQLJDBCUtil.getConnection();
        			   CallableStatement stmt = conn.prepareCall(query))
        	   {
        		   stmt.setInt(1, candidateId);
        		   
        		   rs = stmt.executeQuery();
        		   while(rs.next()){
        			   System.out.println(String.format("%s - %s ", rs.getString("first_name")+" "+rs.getString("last_name"),rs.getString("skill")));
        		   }
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
           }
           
           public static void writeBlob(int candidateId, String filename) {
           
               String updateSQL = "UPDATE candidates SET resume = ? WHERE id=?";
        
               try (Connection conn = MySQLJDBCUtil.getConnection();
                       PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
        
                   File file = new File(filename);
                   FileInputStream input = new FileInputStream(file);
       
                   pstmt.setBinaryStream(1, input);
                   pstmt.setInt(2, candidateId);

                   System.out.println("Reading file " + file.getAbsolutePath());
                   System.out.println("Store file in the database.");
                   pstmt.executeUpdate();
        
               } catch (SQLException | FileNotFoundException e) {
                   System.out.println(e.getMessage());
               }
           }
        
           
           public static void readBlob(int candidateId, String filename) {
             
               String selectSQL = "SELECT resume FROM candidates WHERE id=?";
               ResultSet rs = null;
        
               try (Connection conn = MySQLJDBCUtil.getConnection();
                       PreparedStatement pstmt = conn.prepareStatement(selectSQL);) {
               
                   pstmt.setInt(1, candidateId);
                   rs = pstmt.executeQuery();
        
                   File file = new File(filename);
                   FileOutputStream output = new FileOutputStream(file);
        
                   System.out.println("Writing to file " + file.getAbsolutePath());
                   while (rs.next()) {
                       InputStream input = rs.getBinaryStream("resume");
                       byte[] buffer = new byte[1024];
                       while (input.read(buffer) > 0) {
                           output.write(buffer);
                       }
                   }
               } catch (SQLException | IOException e) {
                   System.out.println(e.getMessage());
               } finally {
                   try {
                       if (rs != null) {
                           rs.close();
                       }
                   } catch (SQLException e) {
                       System.out.println(e.getMessage());
                   }
               }
        
           }
     
           
}
