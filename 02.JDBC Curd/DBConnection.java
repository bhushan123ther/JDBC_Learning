package com.jdbccurd;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/student_db";
	public static final String USER = "root";
	public static final String PASS = "root";

	static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			// System.out.println("Driver Loaded");
			// System.out.println("Connecting to database");
			con = DriverManager.getConnection(JDBC_URL, USER, PASS);
		} catch (Exception e) {
			System.out.println("Exception occured : " + e);
		}

		return con;
	}

	public static void main(String[] args) {
		getConnection();
	}
}
