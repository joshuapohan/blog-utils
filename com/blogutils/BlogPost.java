package com.blogutils;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


class BlogPost{
	long _id;
	String title;
	String author;
	String timestamp;
	String body;
	
	BlogPost(long _id){
		this._id = _id;
	}

	public void SetTitle(String title){
		this.title = title;
	}

	public void SetAuthor(String author){
		this.author = author;
	}

	public void SetTimeStamp(String timestamp){
		this.timestamp = timestamp;
	}

	public void SetBody(String body){
		this.body = body;
	}

	public void SaveAsHTML(){
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("MM_dd");
		String dateFolder = ft.format(dNow);
		File fileDirectory = new File("Archive/" + dateFolder);
		fileDirectory.mkdirs();
		System.out.println("Saved as HTML");
	}

	public void SaveToDatabase(){
		File fileDirectory = new File("C:/sqlite/db/");
		fileDirectory.mkdirs();
		IsDatabaseConnectionSuccessful();
		IsTableConnectionSuccessful();
		IsDataSaveSuccessful();
	}

	protected static boolean IsDatabaseConnectionSuccessful(){

		String url = "jdbc:sqlite:C:/sqlite/db/" + "blog.db";

		try(Connection conn = DriverManager.getConnection(url)){
			if(conn != null){
				DatabaseMetaData meta = conn.getMetaData();
				return true;
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	protected static boolean IsTableConnectionSuccessful(){

		String url = "jdbc:sqlite:C:/sqlite/db/" + "blog.db";

		String sqlQuery = "CREATE TABLE IF NOT EXISTS blogpost(\n"
				+ " id integer PRIMARY KEY,\n "
				+ " title varchar(255),\n"
				+ " author varchar(255),\n"
				+ " timestamp varchar(255),\n"
				+ " body text\n"
				+ ");";

		try(Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement()){
			stmt.execute(sqlQuery);
			return true;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	protected boolean IsDataSaveSuccessful(){

		String url = "jdbc:sqlite:C:/sqlite/db/" + "blog.db";
		String sqlInsert = "INSERT INTO blogpost(id, title, author, timestamp, body) \n"
				+ "VALUES(?,?,?,?,?)";

		try(Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement(sqlInsert)){
			pstmt.setInt(1, (int) this._id);
			pstmt.setString(2, this.title);
			pstmt.setString(3, this.author);
			pstmt.setString(4, this.timestamp);
			pstmt.setString(5, this.body);
			pstmt.executeUpdate();
			return true;
		}
		catch(SQLException e){
			System.out.println(e.getMessage()); 
		}
		return false;
	}

}