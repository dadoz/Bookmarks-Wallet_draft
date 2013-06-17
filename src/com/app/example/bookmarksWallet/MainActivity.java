package com.app.example.bookmarksWallet;

import com.app.example.bookmarksWallet.fragments.WallpaperLoginFragment;
import com.app.example.bookmarksWallet.models.User;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.ActionLogDbAdapter;
import com.app.example.db.lib.DatabaseAdapter;
import com.app.example.db.lib.DatabaseConnectionCommon;
import com.app.example.db.lib.DatabaseSync;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

//application layout STYLE - website
//http://bashooka.com/inspiration/flat-web-ui-design/

public class MainActivity extends SherlockFragmentActivity {
	private static final String TAG = "MainActivity_TAG";
	public DatabaseAdapter db;
	public ActionLogDbAdapter actionLogDb;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
        setContentView(R.layout.fragment_activity);

        /**get LOCAL DB*/
        db=new DatabaseAdapter(this);
        actionLogDb=new ActionLogDbAdapter(this);
        //TODO remeber to clean up first get from online db (otw it will commit again all up on online db)
        
        /**get actionBar*/
        getActionBar().hide();
        
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main_frameLayout_id, new WallpaperLoginFragment());
		ft.commit(); 
			
		if(checkUserLoggedInFromSharedPreferences()){
			//TODO move this fx in sm other place plez - intent to get new url from browser
	        addLinkOnDbIntent();
	        //sync localDb
			if(SharedData.isNetworkAvailable(this))
				if(DatabaseSync.syncLocalDb(db,actionLogDb))
					toastMessageWrapper("localDB sync successful");
	        
	        startActivityForResult(new Intent(MainActivity.this, FragmentChangeActivity.class),1);
		}
		else
			startActivity(new Intent(MainActivity.this, LoginActivity.class));                  	  


    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onPause(){
		super.onPause();
		if(SharedData.isNetworkAvailable(this))
			if(DatabaseSync.syncLocalDb(db,actionLogDb))
				toastMessageWrapper("localDB sync successful");

//		finish();
	}
	/**CHECK CREDENTIAL FROM SHARED_PREF**/
	public boolean checkUserLoggedInFromSharedPreferences(){
		SharedPreferences sharedPref = getSharedPreferences(SharedData.PREFS_NAME, 0);
   		String usernameStored=SharedData.getUsernameStored(sharedPref);
		String passwordStored = SharedData.getPasswordStored(sharedPref);
		int userIdStored=SharedData.getUserIdStored(sharedPref);

		if(usernameStored.compareTo(SharedData.EMPTY_USERNAME)!=0 && passwordStored.compareTo(SharedData.EMPTY_PASSWORD)!=0){
    		SharedData.setUser(userIdStored,usernameStored,passwordStored);
       		Log.d(TAG, "check credential from shared"+usernameStored+passwordStored);
    		return true;
    	}
    	Log.d(TAG,"userLoggedInCheker -  user is not logged in");
   		return false;
	}
	/**GET and ADD Link to Db from Browser***/
	public boolean addLinkOnDbIntent(){
		try{
			Intent urlIntent = getIntent();
			SharedPreferences sharedPref = getSharedPreferences(SharedData.PREFS_NAME, 0);
		    if (urlIntent.getAction().equals(Intent.ACTION_SEND)){
		    	String linkUrl = urlIntent.getStringExtra(Intent.EXTRA_TEXT);
		    	if(SharedData.isUserLoggedIn()){
			    	toastMessageWrapper(linkUrl);
//		    		DatabaseConnectionCommon.insertUrlEntryOnDb(SharedData.LINKS_DB, urlString);
		            //TODO remove static init of linkOrderInList
			        int linkOrderInList=SharedData.EMPTY_LINKID;
			        String linkName=SharedData.getLinkNameByUrl(linkUrl);
			        String iconPath="/emptyPath";
			        int linksUserId=SharedData.getUserIdStored(sharedPref);
			        //set new linkId
			        //get max linkId on db and increme
			        int linkId=DatabaseConnectionCommon.getMaxOnLinkIdWrappLocalDb(db);
		    		if(DatabaseConnectionCommon.insertLinkWrappLocalDb(db,actionLogDb, linkId,linkOrderInList,linkName,iconPath,linkUrl,linksUserId,getSharedPreferences(SharedData.LOG_DB, 0)))
			    		toastMessageWrapper("link added with SUCCESS");
			    			
		    	}else
		    		toastMessageWrapper("u're not loggedin in - plez login");
		    }
		}catch(Exception e){
			Log.e(TAG, "addLinkOnDbIntent - "+e);
			toastMessageWrapper("no URL availble - something has gone wrong");
			return false;
		}
		return true;
	}
	/**USER LOGOUT**/
	public boolean setUserLogout(){
		User userObj = SharedData.getUser();
		if(userObj!=null){
			if(userObj.isUserLoggedIn()){
				userObj.setUserLoggedIn(false);
				SharedPreferences sharedPref = getSharedPreferences(SharedData.PREFS_NAME, 0);
				SharedData.clearSharedPreferences(sharedPref);
				Log.d(TAG, "userLoggedIn"+userObj.isUserLoggedIn());
				return true;
			}else{
				Log.e(TAG,"u're not autorized to get this data - u must log in");
				return false;
			}
		}
		return false;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult - getin");
		if (requestCode == 1) {
		     if(resultCode == RESULT_OK){      
		         String result=data.getStringExtra("result");
		         Log.d(TAG, result);
		         //TODO check if userLogout() fail or not
		         setUserLogout();
		         startActivity(new Intent(MainActivity.this, LoginActivity.class));                  	  
		     }
		     if (resultCode == RESULT_CANCELED)
		    	 toastMessageWrapper("closing application");
		  }
	}	
	/**TOAST MESSAGGE WRAPPER**/
	private void toastMessageWrapper(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
