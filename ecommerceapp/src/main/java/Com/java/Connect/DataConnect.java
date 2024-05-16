package Com.java.Connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

private static Connection conn = null;
	
	private  DataConnect() {
     try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
 
           
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "Thanusri@1316");
 
            System.out.println("Connected to the database");
           
 
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
 
	}
 
	public static Connection  getConnect()
	 {
		 DataConnect d1= new DataConnect();
		 return conn;
		
	 }
	}


