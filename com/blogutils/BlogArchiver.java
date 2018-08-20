package com.blogutils;

import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


class BlogArchiver{

    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public static void main(String[] args){

		JSONParser parser = new JSONParser();

		try{
			Response jsonresponse = Jsoup.connect("https://jpoblog.herokuapp.com/json/posts").userAgent(USER_AGENT).execute();
			FileOutputStream out = (new FileOutputStream(new File("test.json")));
			String jsonString =  jsonresponse.body();
			out.write(jsonresponse.bodyAsBytes());
			out.close();

			Object obj = parser.parse(jsonString);

            JSONObject jsonObject = (JSONObject) obj;
            Iterator<Map.Entry<JSONObject,JSONObject>> hashIt  = jsonObject.entrySet().iterator();
            while(hashIt.hasNext()){
                Map.Entry<JSONObject,JSONObject> curChEntry = hashIt.next();
                if(IsParsePostSuccessful(curChEntry.getValue())){
                }
            }
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	protected static boolean IsParsePostSuccessful(JSONObject post){
		Long id = (Long) post.get("id");
		String title = (String) post.get("title");
		String author = (String) post.get("author");
		String timestamp = (String) post.get("timestamp");
		String body = (String) post.get("body");

		System.out.println("title : " + title);
		System.out.println("author : " + author);
		System.out.println("timestap : " + timestamp);

		BlogPost newPost = new BlogPost(id);
		newPost.SetTitle(title);
		newPost.SetAuthor(author);
		newPost.SetTimeStamp(timestamp);
		newPost.SetBody(body);
		newPost.SaveAsHTML();
		newPost.SaveToDatabase();

		return true;

	}

}


