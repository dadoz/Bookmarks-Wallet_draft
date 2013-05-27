package com.app.example.bookmarksWallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.DatabaseCommon;

public class LoginActivity extends SherlockFragmentActivity{

	//shared preferences variable
    public static final String PREFS_NAME = "UserCredentialFile";

	//they MUST BE EQUALS TO THE ONES IN THE PHP file !!!!
	private static final int USERS_DB = 98;
//	private static final int LINKS_DB = 99;

//	private static final String EMPTY_USERNAME="";
//	private static final String EMPTY_PASSWORD="";
//	private static final int EMPTY_USERID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

        setContentView(R.layout.login_layout);
        createLayoutLOGIN();
    }

    public void createLayoutLOGIN(){
    	//get all button from layout
    	Button cancelButton = (Button)findViewById(R.id.cancelButtonId);
    	Button loginButton = (Button)findViewById(R.id.loginButtonId);
    	
    	//get all EditText from layout
    	final EditText usernameText=(EditText)findViewById(R.id.usernameEditTextId);
    	final EditText passwordText=(EditText)findViewById(R.id.passwordEditTextId);
    	
    	//CANCEL button
    	cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              usernameText.setText("");
              passwordText.setText("");
            }
    	});

    	//LOGIN button
    	loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String username = usernameText.getText().toString();  
            	String password = passwordText.getText().toString();  

            	//TEST
            	username="davide";
            	password="pswd";
				if(checkUserLoggedIn(username,password)){
					Intent intent = new Intent(LoginActivity.this, FragmentChangeActivity.class);
					startActivity(intent);                  	  
				}
            }
    	});
    	
    	
    	
        //TEST
    	String username="davide";
    	String password="pswd";
    	
		if(checkUserLoggedIn(username,password)){
			Intent intent = new Intent(LoginActivity.this, FragmentChangeActivity.class);
			startActivity(intent);                  	  
		}
    	
    }
	

	/**
	 *     
	 * check credential of user - check if user could be logged in or not    
	 * @param username
	 * @param password
	 */
    public boolean checkUserLoggedIn(String username,String password){
    	boolean userLoggedIn=false;

        if(username.length() > 0 && password.length() >0){
        	//check user password and username to log him in 
        	try{
        		//fetch data
        		String result=DatabaseCommon.fetchDataFromDb(USERS_DB);
        		//get if usser is logged in - check
        		if(DatabaseCommon.usersParserJSONData(username,password, result))
        			userLoggedIn=SharedData.isUserLoggedIn();
        	}catch(Exception e){
        		Log.e("MY_TAG","Error - "+ e);
        	}
        	
            if(userLoggedIn){  
            	toastMessageWrapper("u're LOGGED IN :D");
                return true;
            }
           	toastMessageWrapper("Invalid username or password - plez reinsert");
           	return false;
        }
        
        toastMessageWrapper("Username and pswd empty");
        return false;
    }
    
    //toast message wrapper
	private void toastMessageWrapper(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
    
	
}
