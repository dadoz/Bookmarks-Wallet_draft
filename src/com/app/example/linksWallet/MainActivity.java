package com.app.example.linksWallet;

import com.app.example.linksWallet.fragments.WallpaperLoginFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

//application layout STYLE - website
//http://bashooka.com/inspiration/flat-web-ui-design/

public class MainActivity extends SherlockFragmentActivity {

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
		getActionBar().setBackgroundDrawable(new ColorDrawable(R.color.basicRed));
		getActionBar().hide();
		
		Intent intent = new Intent(this,FragmentChangeActivity.class);
		startActivity(intent);
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
    

}
