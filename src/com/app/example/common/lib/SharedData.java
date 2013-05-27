package com.app.example.common.lib;

import java.util.ArrayList;


import android.util.Log;

import com.app.example.bookmarksWallet.models.Link;
import com.app.example.bookmarksWallet.models.Note;
import com.app.example.bookmarksWallet.models.User;

public class SharedData {
	//they MUST BE EQUALS TO THE ONES IN THE PHP file !!!!
	public static final int USERS_DB = 98;
	public static final int LINKS_DB = 99;

	public static final boolean SELECT_ALL_ROW_FROM_DB=true;
	public static final boolean INSERT_URL_ON_DB=true;
	public static final boolean DELETE_URL_FROM_DB=true;

	public static final String EMPTY_USERNAME="";
	public static final String EMPTY_PASSWORD="";
	public static final int EMPTY_USERID=-1;
		
	public static String databaseResultString="";

	public static final int EMPTY_LINKID = -1;
	public static final String EMPTY_STRING = "";
	public static ArrayList<Note> notesListStatic=null;
	public static ArrayList<Link> linksListStatic=null;
	private static User userObj=null;

	//localhost URL for android emulator dev (AVD)
//	public static String DBUrl="http://10.0.2.2/sharedLinksApp/fetchDataFromDbJSON.php";
//	public static String DBUrl="http://192.168.42.155:8080/sharedLinksApp/fetchDataFromDbJSON.php";
	public static String DBUrl="http://kubiz.altervista.org/sharedLinksApp/fetchDataFromDbJSON.php";

	/**NOTES*/
	public static void setNotesListStatic(ArrayList<Note> notesListTmp){
		if(notesListStatic==null){
			notesListStatic=new ArrayList<Note>();
			notesListStatic.addAll(notesListTmp);
		}
	}

	public static ArrayList<Note> getNotesListStatic(){
		return notesListStatic;
	}
	
	public static Note getNoteStaticById(int noteId){
		if(notesListStatic!=null)
			for(Note note:notesListStatic)
				if(note.getNoteId()==noteId)
					return note;
		return null;
	}


	/**LINKS*/
	public static void setLinksListStatic(ArrayList<Link> linksListTmp){
		if(linksListStatic==null){
			linksListStatic=new ArrayList<Link>();
			linksListStatic.addAll(linksListTmp);
		}
	}

	public static ArrayList<Link> getLinksListStatic(){
		return linksListStatic;
	}
	
	public static Link getLinkStaticById(int noteId){
		if(linksListStatic!=null)
			for(Link link:linksListStatic)
				if(link.getLinkId()==noteId)
					return link;
		return null;
	}
	
	public static int getLinkIdByLinkName(String value){
    	if(linksListStatic!=null)
    		for(Link link: linksListStatic)
    			return link.getLinkIdFromLinkName(value);
    		return EMPTY_LINKID;
    }
	    
	public static String getUrlByLinkName(String linkName){
		for(int i=0;i<linksListStatic.size();i++)
			if(!linksListStatic.get(i).findLinkNameBool(linkName))
				return linksListStatic.get(i).getLinkUrl();
		return null;
	}

	
	/**USER*/
    public static void setUser(int userIdDb,String usernameDb,String passwordDb){
    	boolean userLoggedIn=true;
    	if(userObj==null)
    		userObj=new User(userIdDb,usernameDb,passwordDb,userLoggedIn);
    }

	public static boolean isUserLoggedIn(){
		if(userObj!=null)
			return userObj.isUserLoggedIn();
		return false;
	}

	public static User getUser(){
		return userObj;
	}
	
	public static String getUsername(){
		if(userObj.isUserLoggedIn())	
			return userObj.getUsername();
		return EMPTY_USERNAME;
	}

	public static String getUserPassword(){
		if(userObj.isUserLoggedIn())	
			return userObj.getPassword();
		return EMPTY_PASSWORD;
	}
	
	public static int getUserId(){
		if(userObj.isUserLoggedIn())	
			return userObj.getUserId();
		return EMPTY_USERID;
	}
	
	public static boolean setUserLogout(){
		if(userObj!=null)
			if(userObj.isUserLoggedIn()){
				//delete the object
	//			userObj=null;
				//set loggedin status to false
				userObj.setUserLoggedIn(false);
				return true;
			}else{
				Log.e("MY_TAG","u're not autorized to get this data - u must log in");
				return false;
			}
		return false;
	}
}
