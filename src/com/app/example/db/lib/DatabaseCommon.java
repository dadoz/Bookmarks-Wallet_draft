package com.app.example.db.lib;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.StrictMode;
import android.util.Log;

import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;
import com.app.example.http.client.CustomHttpClient;

public class DatabaseCommon {
	static String databaseResultString;
	 //TODO  DB STUFF - put all this code into DB class
	/**
	 * function to fetch data from db and compare 
	 * the usr and pws typed in from user
	 * @param usernameTypedIn
	 * @param passwordTypedIn
	 * @return true or false - return usr and pswd typed in from user check :D
	 */
   public static String fetchDataFromDb(int choicedDB){
     	String response ="";
     	String result="";
     	// call executeHttpPost method passing necessary parameters 
     	try {
     		//check if db is right
     		if(choicedDB!=SharedData.LINKS_DB && choicedDB!=SharedData.USERS_DB)
     			Log.e("fetchDataFromDb_TAG", "NO DB FOUND - u must define the right database name");
     		
     		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

     		postParameters.add(new BasicNameValuePair("choicedDB",""+choicedDB));
     		postParameters.add(new BasicNameValuePair("selectAllRowFromDB",""+SharedData.SELECT_ALL_ROW_FROM_DB));
     		
     		//add new pair of params to set userId
     		if(choicedDB==SharedData.LINKS_DB){
     			//get my userId to fetch all liks I stored before
//     			if(userObj!=null)
//     				userIdTMP=userObj.getUserId();
     			int userIdTMP=1;
     			postParameters.add(new BasicNameValuePair("userId",""+userIdTMP));
     		}	
     		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
     	    StrictMode.setThreadPolicy(policy);
     		
     		// from device instead of use 127.0.0.1 u must use 10.0.2.2 
     	    response = CustomHttpClient.executeHttpPost(SharedData.DBUrl,postParameters);
     	  
     		// store the result returned by PHP script that runs MySQL query
   	    result = response.toString();  
   	    Log.d("RESULT_JSON",result);
     	}catch (Exception e) {
     		Log.e("fetchDataFromDb_TAG","Error in http connection!!" + e.toString());     
     	}
     	return result;
   }
   public static boolean usersParserJSONData(String usernameTypedIn,String passwordTypedIn,String result){
	    Log.v("JSON_TAG","this is the result" + result);
	    //parse json data
	    try{
	    	//def temp db variable
	    	String usernameDb;
	    	String passwordDb;
	    	int userIdDb;

	    	//set false cos the user should be logged out before trying to log in again
	    	JSONArray jArray = new JSONArray(result);
	    	for(int i=0;i<jArray.length();i++){
	    		//getJSONObj 
	    		JSONObject json_data = jArray.getJSONObject(i); 
	    		
	    		//get usr and pswd from JASON data
	    		userIdDb=json_data.getInt("user_id");
	    		usernameDb=json_data.getString("username");
	    		passwordDb=json_data.getString("password");
               
//               databaseResultString += "\n" +"USR:" + usernameDb + "  " +"PSWD:" + passwordDb;
//               //Log all database entries
	    		Log.i("usersParserJSONData_TAG","id: "+userIdDb+
                        ", usrname: "+usernameDb+
                        ", pswd: "+passwordDb
	    				);

	    		//check usrname and pswd and set NEW user
	       	    if(usernameDb.compareTo(usernameTypedIn)==0 && passwordDb.compareTo(passwordTypedIn)==0)
	       	    	SharedData.setUser(userIdDb,usernameDb,passwordDb);
	    	}
	    	return true;
	    }catch(JSONException e){
	    	Log.e("usersParserJSONData_TAG", "Error parsing data "+e.toString());
	      	return false;
	    }
   }
   public static ArrayList<Link> getLinksListFromJSONData(String result){
	   ArrayList<String> linksUrlArray=new ArrayList<String>();
	   ArrayList<Link> linksObjList=new ArrayList<Link>();

   	//create list of Link object
//   	if(linksObjList==null)
//   		linksObjList=new ArrayList<Link>();
   	
	   	//var of links db
	   int linkIdDb=0;
	   String iconPathDb="";
	   String linkUrlDb="";
	   int userIdDb=0;
	   String linkName="";
	   boolean deletedLinkFlag=false;
	   String delIconPathDb="";    	
   	
	   //Log.v("JSON_TAG","this is the result" + result);
	   //parse json data
	   try{
//	    	String databaseResultString = "";
	    	JSONArray jArray = new JSONArray(result);
	    	for(int i=0;i<jArray.length();i++){
	    		//getJSONObj 
	    		JSONObject json_data = jArray.getJSONObject(i); 

	    		databaseResultString ="";
               
	    		//get usr and pswd from JASON data
	    		linkIdDb=json_data.getInt("link_id");
	    		iconPathDb=json_data.getString("iconPath");
	    		linkUrlDb=json_data.getString("linkUrl");
	    		userIdDb=json_data.getInt("links_user_id");
	    		linkName=json_data.getString("linkName");
        
	    		//add delIconPath to JSON data!!!!!!!!!
               
	    		//populate the list
	    		linksUrlArray.add(linkName);
	    		Log.v("JSON_links_result",""+json_data.toString());
               
	    		linksObjList.add(new Link(linkIdDb,iconPathDb,linkName,linkUrlDb, userIdDb,delIconPathDb,deletedLinkFlag));

               
	/*              try{
	               	URL linkURLObj=new URL(linkUrlDb);
	               	infoURL=linkURLObj.getUserInfo();
	               }
	               catch(Exception e)
	               {
	               	Log.v("URL_TAG","error - "+e);
	               }*/
	    		//set databaseResultString to print out all database entries
	    		databaseResultString = result;
	    		//Log all database entries
	    		Log.i("linksParserJSONData_TAG","id: "+linkIdDb+
	    				", iconPath: "+iconPathDb+
                        ", linkUrl: "+linkUrlDb+
                        ", userId: "+userIdDb+
                        ", linkName: "+linkName
	    				);
             
	    	}
	    }catch(JSONException e){
	    	Log.e("linksParserJSONData_TAG", "Error parsing data "+e.toString());
	    	linksUrlArray.add("Empty URLs list");
	    }
	    //return the list of all links
   	return linksObjList;
   }
   public static boolean insertUrlEntryOnDb(int choicedDB,String urlString){
	   if(SharedData.isUserLoggedIn()){
		   try{
		  		//check if db is right
		  		if(choicedDB!=SharedData.LINKS_DB && choicedDB!=SharedData.USERS_DB)
		  			Log.e("MY_TAG", "NO DB FOUND - u must define the right database name");
	
		  		//new params array
		  		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	
		  		//add choicedDB params
		  		postParameters.add(new BasicNameValuePair("insertUrlOnDb",""+SharedData.INSERT_URL_ON_DB));
		  		postParameters.add(new BasicNameValuePair("choicedDB",""+choicedDB));
		  		
		  		if(choicedDB==SharedData.LINKS_DB){
		  			//get my userId to fetch all liks I stored before
	  				int userIdTMP=SharedData.getUser().getUserId();
		  			postParameters.add(new BasicNameValuePair("userId",""+userIdTMP));
	
		  			//get url to be stored
		  			if(urlString!=null)
		  				postParameters.add(new BasicNameValuePair("linkUrl",""+urlString));
		  			
		  			//get url name
		  			//add url name to be added to the url above
		  			String linkNameTMP=getUrlTitle(urlString);
		  			
		  			if(linkNameTMP!=null)
		  				postParameters.add(new BasicNameValuePair("linkName",""+linkNameTMP));
		  		}	
		  		
		  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	    StrictMode.setThreadPolicy(policy);
		   		String response = CustomHttpClient.executeHttpPost(SharedData.DBUrl,postParameters);
		   		Log.d("MY_TAG",response);
		   		return true;

		  	}catch (Exception e) {
		  		Log.e("insertUrlEntryOnDb_TAG","Error in http connectionx!!" + e.toString());     
	   		return false;
		  	}
	   }
	   return false;
   }
   public static boolean deleteUrlEntryFromDb(int choicedDB,String linkName){
	   if(SharedData.isUserLoggedIn()){
		   try{
		  		//check if db is right
		  		if(choicedDB!=SharedData.LINKS_DB && choicedDB!=SharedData.USERS_DB)
		  			Log.e("MY_TAG", "NO DB FOUND - u must define the right database name");
	
		  		//new params array
		  		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	
		  		//add choicedDB params
		  		postParameters.add(new BasicNameValuePair("deleteUrlFromDb",""+SharedData.DELETE_URL_FROM_DB));
		  		postParameters.add(new BasicNameValuePair("choicedDB",""+choicedDB));
		  		
	 			//get my userId to fetch all liks I stored before
	 			int userIdTMP=SharedData.getUser().getUserId();
	 			if(userIdTMP!=SharedData.EMPTY_USERID)
	 				postParameters.add(new BasicNameValuePair("links_user_id",""+userIdTMP));
	 			else
	 				return false;
	
	 			//getLinksId to be deleted
	 			int linkIdTMP=SharedData.getLinkIdByLinkName(linkName);
	 			
	 			//DEBUG
	 			Log.v("id_link_TAG",""+linkIdTMP);
	 			
	 			//check linkId!=null
	 			if(linkIdTMP!=SharedData.EMPTY_LINKID)
	 				postParameters.add(new BasicNameValuePair("linkId",""+linkIdTMP));
	 			else
	 				return false;
	 			
		  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	    StrictMode.setThreadPolicy(policy);
		        
		  	    
		   		String response = CustomHttpClient.executeHttpPost(SharedData.DBUrl,postParameters);
		   		
		   		Log.d("DEL_RESULT_TAG","this is the result" + response);
		   		return true;
		  	}catch (Exception e) {
		  		Log.e("deleteUrlEntryFromDb_TAG","Error in http connection!!" + e.toString());     
	   		return false;
		  	}
   		}
	   return false;
   }
   
	public static String getDatabaseResultString(){
		if(SharedData.getUser().isUserLoggedIn())	
			return databaseResultString;

		Log.e("MY_TAG","u're not autorized to get this data - u must log in");
		return SharedData.EMPTY_STRING;
	}
	
    /**STATIC fx to get values from Link - JSOUP*/
    public static String getUrlTitle(String URLString){
    	//URL title 
    	String URLTitleString;
    	try{
	    	Document doc = Jsoup.connect(URLString).get();
	
	    	Elements URLtitle=doc.select("title");
//	    	toastMessageWrapper(URLtitle.text());
	    	URLTitleString=URLtitle.text();
	    	Log.v("JSOUP_TAG",URLTitleString);
	    	
	    	return URLTitleString;
    	}catch(Exception e){
	    	Log.v("JSOUP_TAG",""+e);
    	}
    	return SharedData.EMPTY_STRING;
    }

}
