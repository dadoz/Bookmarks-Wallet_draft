package com.app.example.linksWallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class LoginActivity extends SherlockFragmentActivity{

	//shared preferences variable
    public static final String PREFS_NAME = "UserCredentialFile";

	//they MUST BE EQUALS TO THE ONES IN THE PHP file !!!!
	private static final int USERS_DB = 98;
//	private static final int LINKS_DB = 99;
//
//	private static final String EMPTY_USERNAME="";
//	private static final String EMPTY_PASSWORD="";
//	private static final int EMPTY_USERID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

        setContentView(R.layout.login_layout);

    }

    public void createLayoutLOGIN(){
        //clean up all the main view
//        ScrollView layout=(ScrollView)findViewById(R.id.mainActivityScrollViewId);
//    	View child = getLayoutInflater().inflate( R.layout.login_layout,null);
//    	layout.addView(child);
    
    	//get all button from layout
    	Button cancelButton = (Button)findViewById(R.id.cancelButtonId);
    	Button loginButton = (Button)findViewById(R.id.loginButtonId);
    	
    	//get all EditText from layout
    	final EditText usernameText=(EditText)findViewById(R.id.usernameEditTextId);
    	final EditText passwordText=(EditText)findViewById(R.id.passwordEditTextId);
    	
    	//set actions to buttons
    	cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //clean the fields
              usernameText.setText("");
              passwordText.setText("");
            }
    	});
    	
    	loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//            get usr and pswd from user
              String username = usernameText.getText().toString();  
              String password = passwordText.getText().toString();  

              //TEST
//              username="username1";
//              password="testPassword";
              
//              toastMessageWrapper(username+password);
              //check usr and pswd user typed in 
//              checkCredentialsOnDb(username,password);
              
//              boolean checkUserLoggedIn = checkUserLoggedIn(username,password);

              if(checkUserLoggedIn(username,password)){
            	  //launch new activity
            	  Intent intent = new Intent(LoginActivity.this, FragmentChangeActivity.class);
                  startActivity(intent);                  	  
              }
              
            }
    	});
    }
	

	/**
	 *     
	 * check credential of user - check if user could be logged in or not    
	 * @param username
	 * @param password
	 */
    public boolean checkUserLoggedIn(String username,String password)
    {
    	//check credential typed in
    	boolean userLoggedIn=false;

        if(username.length() > 0 && password.length() >0){
        	//check user password and username to log him in 
        	try{
        		//fetch data
        		String result=ApplicationCheckUserLoggedIn.fetchDataFromDb(USERS_DB);
        		//get if usser is logged in - check
        		boolean checkDataParsered=ApplicationCheckUserLoggedIn.usersParserJSONData(username, password, result);
        		if(checkDataParsered)
        			userLoggedIn=ApplicationCheckUserLoggedIn.getUserLoggedIn();
        	}catch(Exception e){
        		Log.e("MY_TAG","Error - "+ e);
        	}
        	
            if(userLoggedIn){  
            	toastMessageWrapper("u're LOGGED IN :D");
                return true;
            }
           	toastMessageWrapper("Invalid username or password - plez reinsert");  
        }
        
        toastMessageWrapper("Username and pswd empty");
        return false;
    }
    
    //toast message wrapper
	private void toastMessageWrapper(String message) 
	{
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
    
	
}
