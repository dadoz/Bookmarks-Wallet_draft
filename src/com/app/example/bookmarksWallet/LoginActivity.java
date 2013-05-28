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
    public static final String PREFS_NAME = "UserCredentialFile";
	private static final String TAG = "LoginActivity_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
        setContentView(R.layout.login_layout);
        createLayout();
    }

    public void createLayout(){
    	Button cancelButton = (Button)findViewById(R.id.cancelButtonId);
    	Button loginButton = (Button)findViewById(R.id.loginButtonId);
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
				if(checkUserLoggedIn(username,password))
					startActivity(new Intent(LoginActivity.this, FragmentChangeActivity.class));                  	  
            }
    	});
    	
    	Log.d(TAG,"hey login activity - trying login");
        //TEST
    	String username="davide";
    	String password="pswd";
    	int userId=1;
		//FIXME remove this test rows
		SharedData.setUser(userId,username,password);
		startActivity( new Intent(LoginActivity.this, FragmentChangeActivity.class));                  	  
    	
    }
	/**USER LOGIN CHECKER**/
    public boolean checkUserLoggedIn(String username,String password){
        int loginFail = 0;
        if(username!="" && password!=""){
        	try{
        		int userId = DatabaseCommon.usersParserJSONData(username,password);
        		if(userId!=SharedData.USER_LOGIN_FAILED){
            		SharedData.setUser(userId,username,password);
                	toastMessageWrapper("u're LOGGED IN :D");
                	return true;
        		}
        	}catch(Exception e){
        		Log.e(TAG,"Error - "+ e);
        	}
        	//set invalid username or password
        	loginFail=1;
        }
   
        //handle error
		switch(loginFail){
        	case 0:
        		toastMessageWrapper("Username and pswd empty");
        		break;
        	case 1:
        		toastMessageWrapper("Invalid username or password - plez reinsert");
        		break;
        }        		
        return false;
    }
    /**toast message wrapper*/
	private void toastMessageWrapper(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
