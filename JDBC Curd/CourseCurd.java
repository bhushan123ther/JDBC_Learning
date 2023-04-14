package com.jdbccurd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CourseCurd {

	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	static Scanner sc = new Scanner(System.in);
	static Connection con = null;

	public CourseCurd() {
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

//=================================================== menu =======================================================================================================	

	public void menu() {
		System.out.println("1.Enter a course  ");
		System.out.println("2.Delete a course  ");
		System.out.println("3.Update course ");
		System.out.println("4.View course ");
		System.out.println("5.View perticular course by id");
		System.out.println("6.Exist");
	}

//============================= Enter course code starts from here =======================================================================================================

	void insertCourse() {
		System.out.println("Enter the course id");
		int courseId = sc.nextInt();

		System.out.println("Enter the course name");
		String cname = sc.next();

		System.out.println("Enter the duration");
		String duration = sc.next();

		System.out.println("Enter course fees:");
		int fees = sc.nextInt();

		try {
			pst = con.prepareStatement("insert into course values(?,?,?,?,?)");
			pst.setInt(1, courseId);
			pst.setString(2, cname);
			pst.setString(3, duration);
			pst.setInt(4, fees);
			pst.setInt(5, 0);

			int noOfRowsInserted = pst.executeUpdate();
			if (noOfRowsInserted > 0) {
				System.out.println("Course added");
			} else {
				System.out.println("Error in data");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
//=============================== Delete Course by Id code starts from here ================================================================================================

	void deleteByCourseId() {
		System.out.println("Enter the course id to be deleted : ");
		int id = sc.nextInt();
		try {
			pst = con.prepareStatement("delete from course where courseId=?");
			pst.setInt(1, id);

			int noOfRowsDeleted = pst.executeUpdate();
			if (noOfRowsDeleted > 0) {
				System.out.println("Course " + id + " is deleted");
			} else {
				System.out.println("No course with this id found");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

//============================ Update Course code starts from here ===========================================================================================================			

	void updateCourseFees(int id) {
		try {
			if (getCourseById(id)) {
				System.out.println("Enter course fees : ");
				int fees = sc.nextInt();

				pst = con.prepareStatement("update course set fees = ? where courseId = ?");
				pst.setInt(1, fees);
				pst.setInt(2, id);

				int noOfRowsUpdated = pst.executeUpdate();
				if (noOfRowsUpdated > 0) {
					System.out.println("Course Updates with new values : ");
					getCourseById(id);
				} else {
					System.out.println("Error in course data");
				}
			} else {
				System.out.println("Course Not Found");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

//============================ View Course code starts from here ===============================================================================================================			

	void getAllCourses() {
		try {
			rs = st.executeQuery("select * from course");
			System.out.println("========================== Courses Available ==========================");
			System.out.println("----------------------------------------------------------------------------");

			while (rs.next()) {
				int courseId = rs.getInt(1);
				String cname = rs.getString(2);
				String duration = rs.getString(3);
				int fees = rs.getInt(4);
				int scount = rs.getInt(5);

				System.out.println(courseId + " " + cname + " " + duration + " " + fees + " " + scount);
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
//============================= View Course by Id code starts from here =========================================================================================================

	boolean getCourseById(int id) {
		boolean status = false;

		try {
			pst = con.prepareStatement("select * from course where courseId = ?");
			pst.setInt(1, id);

			rs = pst.executeQuery();
			while (rs.next()) {
				int courseId = rs.getInt(1);
				String cname = rs.getString(2);
				String duration = rs.getString(3);
				int fees = rs.getInt(4);
				int scount = rs.getInt(5);

				System.out.println(courseId + " " + cname + " " + duration + " " + fees + " " + scount);
				System.out.println();

				status = true;
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return status;
	}

	void closeApp() {
		System.out.println("Thanks for using application.");
	}
//=============================== main method code starts from here ==========================================================================================================================

	public static void main(String[] args) {
		CourseCurd cd = new CourseCurd();

		System.out.println("=============== Course JDBC ===============");

		int ch;

		do {
			cd.menu();
			System.out.println("Enter choice : ");
			ch = sc.nextInt();

			int id;

			switch (ch) {
			case 1:
				cd.insertCourse();
				break;
			case 2:
				cd.deleteByCourseId();
				break;
			case 3:
				System.out.println("Enter the course id to be updated : ");
				id = sc.nextInt();
				cd.updateCourseFees(id);
				break;
			case 4:
				cd.getAllCourses();
				break;
			case 5:
				System.out.println("Enter the course id : ");
				id = sc.nextInt();
				if (!cd.getCourseById(id)) {
					System.out.println("Course Not Found");
				}
				break;
			case 6:
				cd.closeApp();
				System.exit(0);
				break;

			default:
				System.out.println("Error in input");
			}

			System.out.println("Do you want to perform more operations (1-Yes / 0-No): ");
			ch = sc.nextInt();
			while (ch != 1 && ch != 0) {
				System.out.println("Enter proper option ");
				ch = sc.nextInt();
			}
			if (ch == 0) {
				System.out.println("Application closing.....");
			}
		} while (ch == 1);
	}
}
