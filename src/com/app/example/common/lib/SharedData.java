package com.app.example.common.lib;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.app.example.bookmarksWallet.models.Link;
import com.app.example.bookmarksWallet.models.Note;
import com.app.example.bookmarksWallet.models.User;

public class SharedData {
	//they MUST BE EQUALS TO THE ONES IN THE PHP file !!!!
	public static final int USERS_DB = 98;
	public static final int LINKS_DB = 99;
	public static enum Fragments {
		LINKS_LIST, NOTES_LIST,ADD_NOTE
	};
	
	public static final boolean SELECT_ALL_ROW_FROM_DB=true;
	public static final boolean INSERT_URL_ON_DB=true;
	public static final boolean DELETE_URL_FROM_DB=true;

	public static final int USER_LOGIN_FAILED=-1;
	
	public static final String EMPTY_USERNAME="";
	public static final String EMPTY_PASSWORD="";
	public static final int EMPTY_USERID=-1;

    public static final String PREFS_NAME = "UserCredentialFile";
    public static final String USERNAME_STORED_LABEL =" usernameStored";
    public static final String PASSWORD_STORED_LABEL =" passwordStored";
    public static final String USERID_STORED_LABEL =" userIdStored";


    public static final String LOG_DB = "LocalDbLog";
    public static final String ACTION_LABEL =" usernameStored";
    public static final String MODEL_LABEL =" passwordStored";
    public static final String ID_LABEL =" userIdStored";

    
    
    public static String databaseResultString="";

	public static final int EMPTY_LINKID = -1;
	public static final String EMPTY_STRING = "";
	public static final String LOCAL_DB = "sqlite_db_name";
	public static final String ONLINE_DB = "online_db_url";
	private static final String TAG = "SharedData_TAG";

	public static final String LINK_LABEL = "LINK";
	public static final String NOTE_LABEL = "NOTE";
	public static final String ADD_LABEL = "ADD";
	public static final String EDIT_LABEL = "EDIT";
	public static final String DELETE_LABEL = "DELETE";

	public static ArrayList<Note> notesListStatic=null;
	public static ArrayList<Link> linksListStatic=null;
	private static User userObj=null;

	public static int LINK_NOT_IN_LIST=-1;
	static int linkPosition=LINK_NOT_IN_LIST;
	//localhost URL for android emulator dev (AVD)
//	public static String DBUrl="http://10.0.2.2/sharedLinksApp/fetchDataFromDbJSON.php";
//	public static String DBUrl="http://192.168.42.155:8080/sharedLinksApp/fetchDataFromDbJSON.php";
	public static String DBUrl="http://kubiz.altervista.org/sharedLinksApp/fetchDataFromDbJSON.php";

	/**NOTES*/
	public static void setNotesList(ArrayList<Note> notesListTmp){
		if(notesListStatic==null){
			notesListStatic=new ArrayList<Note>();
			notesListStatic.addAll(notesListTmp);
		}
	}
	
	public static ArrayList<Note> getNotesList(){
		return notesListStatic;
	}
	
	public static Note getNoteById(int noteId){
		if(notesListStatic!=null)
			for(Note note:notesListStatic)
				if(note.getNoteId()==noteId)
					return note;
		return null;
	}
	/**LINKS*/
	public static void setLinksList(ArrayList<Link> linksListTmp){
    	for(Link link:linksListTmp)
    		Log.d(TAG,link.getLinkName());
		if(linksListStatic==null){
			linksListStatic=new ArrayList<Link>();
			linksListStatic.addAll(linksListTmp);
		}
	}

	public static ArrayList<Link> getLinksListStatic(){
		return linksListStatic;
	}
	
	public static Link getLinkById(int noteId){
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
	public static boolean removeLink(Link link) {
		if(linksListStatic.remove((Link)link))
			return true;
		return false;
	}
    /**STATIC fx to get values from Link - JSOUP*/
    public static String getLinkNameByUrl(String URLString){
    	//URL title 
    	try{
	    	Document doc = Jsoup.connect(URLString).get();
	    	Elements URLtitle=doc.select("title");
	    	Log.d(TAG,URLtitle.text());
	    	return URLtitle.text();
    	}catch(Exception e){
	    	Log.e(TAG,""+e);
    	}
    	
    	//empty urlname
    	String URLTitleString=URLString.split("//")[1];
    	Log.d(TAG,URLTitleString);
    	return URLTitleString;
//    	return SharedData.EMPTY_STRING
    }

	/**USER*/
    public static void setUser(int userIdDb,String usernameDb,String passwordDb){
    	boolean userLoggedIn=true;
//    	userObj=null;
//    	if(userObj==null)
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
	
	/**SharedPreferences**/
    public static String getUsernameStored(SharedPreferences sharedPref){
    	return sharedPref.getString(USERNAME_STORED_LABEL, EMPTY_USERNAME);
    }
    public static String getPasswordStored(SharedPreferences sharedPref){
		return sharedPref.getString(PASSWORD_STORED_LABEL, EMPTY_PASSWORD);
    }
    public static int getUserIdStored(SharedPreferences sharedPref){
		return sharedPref.getInt(USERID_STORED_LABEL, EMPTY_USERID);
    }
    public static void setUsernameStored(SharedPreferences sharedPref,String username){
    	Editor editor = sharedPref.edit();
    	editor.putString(USERNAME_STORED_LABEL, username);
    	editor.commit();
    }
    public static void setPasswordStored(SharedPreferences sharedPref,String password){
    	Editor editor = sharedPref.edit();
    	editor.putString(PASSWORD_STORED_LABEL, password);
    	editor.commit();
    }
    public static void setUserIdStored(SharedPreferences sharedPref,int userId){
    	Editor editor = sharedPref.edit();
    	editor.putInt(USERID_STORED_LABEL, userId);
    	editor.commit();
    }

    public static void clearSharedPreferences(SharedPreferences sharedPref){
    	setUsernameStored(sharedPref,SharedData.EMPTY_USERNAME);
    	setPasswordStored(sharedPref,SharedData.EMPTY_PASSWORD);
    	setUserIdStored(sharedPref,SharedData.EMPTY_USERID);
    }
    //TODO fix this shit
    /***LINK POOSITION**/
    public static void setLinkPosition(int pos){
    	linkPosition=pos;
    }
    public static int getLinkPosition(){
    	return linkPosition;
    }

    public static boolean isNetworkAvailable(FragmentActivity fragActivity) {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) fragActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

	/**SharedPreferences**/
    public static String getActionLogDbStored(SharedPreferences sharedPref){
    	return sharedPref.getString(ACTION_LABEL, EMPTY_USERNAME);
    }
    public static String getModelLogDbStored(SharedPreferences sharedPref){
		return sharedPref.getString(MODEL_LABEL, EMPTY_PASSWORD);
    }
    public static int getIdLogDbStored(SharedPreferences sharedPref){
		return sharedPref.getInt(ID_LABEL, EMPTY_USERID);
    }
    public static void setLogDbStored(SharedPreferences sharedPref,String action,String model, int id){
    	setActionLogDbStored( sharedPref, action );
    	setModelLogDbStored( sharedPref, model);
    	setIdLogDbStored( sharedPref, id);
    }
    public static void setActionLogDbStored(SharedPreferences sharedPref,String action ){
    	Editor editor = sharedPref.edit();
    	editor.putString(ACTION_LABEL, action);
    	editor.commit();
    }
    public static void setModelLogDbStored(SharedPreferences sharedPref,String model){
    	Editor editor = sharedPref.edit();
    	editor.putString(MODEL_LABEL, model);
    	editor.commit();
    }
    public static void setIdLogDbStored(SharedPreferences sharedPref,int id){
    	Editor editor = sharedPref.edit();
    	editor.putInt(ID_LABEL, id);
    	editor.commit();
    }

    public static void clearLog(SharedPreferences sharedPref){
    	setActionLogDbStored(sharedPref,SharedData.EMPTY_STRING);
    	setModelLogDbStored(sharedPref,SharedData.EMPTY_STRING);
    	setIdLogDbStored(sharedPref,SharedData.EMPTY_LINKID);
    }

}
