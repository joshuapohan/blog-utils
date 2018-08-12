package com.blogutils;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

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
}