package com.app.example.bookmarksWallet;

//import com.app.example.linksWallet.fragments.LoginFragment;
import com.app.example.bookmarksWallet.fragments.WallpaperLoginFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

//application layout STYLE - website
//http://bashooka.com/inspiration/flat-web-ui-design/

public class MainActivity extends SherlockFragmentActivity {
    public static final String PREFS_NAME = "UserCredentialFile";
	private static final String EMPTY_USERNAME="";
	private static final String EMPTY_PASSWORD="";
	private static final int EMPTY_USERID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
        setContentView(R.layout.fragment_activity);
       
        //STATIC handling of fragment - REMEMBER u could not change it in this way
//        defined in xml
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_activity_id); 
        
        //DINAMIC handling of fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main_frameLayout_id, new WallpaperLoginFragment());
		ft.commit(); 
		
		//hide action bar on main activity
		getActionBar().setBackgroundDrawable(new ColorDrawable(R.color.cobaltGreen));
		getActionBar().hide();

		//IF NOT LOGGED IN
		if(!userLoggedInChecker()){
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);                  	  
		}

		Intent intent = new Intent(MainActivity.this, FragmentChangeActivity.class);
		startActivity(intent);                  	  
		//OTW inflate a new LoginFragm
//		ft.add(R.id.main_frameLayout_id, new LoginFragment());
//		ft.commit(); 
		
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
	
	public boolean userLoggedInChecker(){
		// Restore userLOGIN credentials even if user kill the app
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		/***
		 *	getString(key,defValue)
		 *	key	The name of the preference to retrieve. 
		 * 	defValue	Value to return if this preference does not exist.
		 */
		String usernameStored = settings.getString("usernameStored", EMPTY_USERNAME);
   		String passwordStored = settings.getString("passwordStored", EMPTY_PASSWORD);
   		int userIdStored=settings.getInt("userIdStored", EMPTY_USERID);

	    try{
	    	if(usernameStored!=EMPTY_USERNAME && passwordStored!=EMPTY_PASSWORD){
	    		ApplicationCheckUserLoggedIn.newUserObjWrapper(userIdStored,usernameStored,passwordStored,true);
	    		return true;
	    	}
	    }catch(Exception e){
	    	Log.v("ON_STOP","error - " + e);
	    }
   		return false;
	}
}
