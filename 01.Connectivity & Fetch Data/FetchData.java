package com.jdbconnectivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FetchData {
	
	static Connection con = null;
	Statement st;
	ResultSet rs;
	
	public FetchData()
	{
		con= DBConnection.getConnection();
	}
	
	public void fetchCourseData()
	{
		try
		{
			st= con.createStatement();
			rs= st.executeQuery("select * from course");
			System.out.println("-------------------Courses Available----------------");
			System.out.println("=========================================================");
			
			while(rs.next())
			{
				int cid= rs.getInt(1);
				String cname = rs.getString(2);
				String duration= rs.getString(3);
				float fees= rs.getFloat(4);
				int noOfStudents= rs.getInt(5);
				
				System.out.println(cid+"  "+cname+"  "+duration+"  "+fees+"  "+noOfStudents);
				System.out.println();
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	

	public static void main(String[] args) {
		
		FetchData obj= new FetchData();
		obj.fetchCourseData();


	}

}
