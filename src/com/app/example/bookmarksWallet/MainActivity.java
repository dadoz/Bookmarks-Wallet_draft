package com.app.example.bookmarksWallet;

import com.app.example.bookmarksWallet.fragments.WallpaperLoginFragment;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.DatabaseCommon;

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
    public static final String PREFS_NAME = "UserCredentialFile";
	private static final String EMPTY_USERNAME="";
	private static final String EMPTY_PASSWORD="";
	private static final int EMPTY_USERID=-1;
	private static final String TAG = "MainActivity_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
        setContentView(R.layout.fragment_activity);
        
        //hide action bar on main activity
        getActionBar().hide();
        //TODO move this fx in sm other place plez - intent to get new url from browser
        storeUrlFromBrowserIntoDb();
        
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main_frameLayout_id, new WallpaperLoginFragment());
		ft.commit(); 
		
		if(userLoggedInChecker())
			startActivity(new Intent(MainActivity.this, FragmentChangeActivity.class));

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
//		finish();
	}
	/**CHECK CREDENTIAL FROM SHARED_PREF**/
	public boolean userLoggedInChecker(){
		// Restore userLOGIN credentials even if user kill the app
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		
		String usernameStored = settings.getString("usernameStored", EMPTY_USERNAME);
   		String passwordStored = settings.getString("passwordStored", EMPTY_PASSWORD);
   		int userIdStored=settings.getInt("userIdStored", EMPTY_USERID);

    	if(usernameStored!=EMPTY_USERNAME && passwordStored!=EMPTY_PASSWORD){
    		SharedData.setUser(userIdStored,usernameStored,passwordStored);
    		return true;
    	}
    	Log.d(TAG,"userLoggedInCheker -  user is not logged in");
   		return false;
	}
	/**GET and STORE url to Db from Browser***/
	public boolean storeUrlFromBrowserIntoDb(){
		try{
			Intent urlIntent = getIntent();
		    if (urlIntent.getAction().equals(Intent.ACTION_SEND)){
		    	String urlString = urlIntent.getStringExtra(Intent.EXTRA_TEXT);;
		    	if(SharedData.isUserLoggedIn()){
			    	toastMessageWrapper(urlString);
		    		DatabaseCommon.insertUrlEntryOnDb(SharedData.LINKS_DB, urlString);
		    	}else
		    		toastMessageWrapper("u're not loggedin in - plez login");
		    }
		}catch(Exception e){
			Log.e(TAG, "StoreUrlFromBrowserIntoDb - "+e);
			toastMessageWrapper("no URL availble - something has gone wrong");
			return false;
		}
		return true;
	}
	/**TOAST MESSAGGE WRAPPER**/
	private void toastMessageWrapper(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
