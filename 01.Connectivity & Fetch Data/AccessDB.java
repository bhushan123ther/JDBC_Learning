package com.jdbconnectivity;

import java.sql.Connection;

public class AccessDB {
	
    static Connection con=null;
    
	public AccessDB() {
		
		con= DBConnection.getConnection();
		//System.out.println("Connection established");
	
	}

	public static void main(String[] args) {
		
		AccessDB obj= new AccessDB();


	}

}
