package com.app.example.linksWallet;

//import com.app.example.linksWallet.fragments.FragmentChangeActivity;
//import com.app.example.linksWallet.fragments.LoginActivity;
//import com.app.example.linksWallet.fragments.LinkListContentFragment;
import com.app.example.linksWallet.fragments.LoginFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
//import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
//import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//http://androiddrawables.com/menu.html
public class MainActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

        // We default to building our Fragment at runtime, but you can switch the layout here
        // to R.layout.activity_fragment_xml in order to have the Fragment added during the
        // Activity's layout inflation.
        setContentView(R.layout.fragment_activity);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_activity_id); // You can find Fragments just like you would with a 
                                                                               // View by using FragmentManager.
        
        // If we are using activity_fragment_xml.xml then this the fragment will not be
        // null, otherwise it will be.
        if (fragment == null) {
            
            // We alter the state of Fragments in the FragmentManager using a FragmentTransaction. 
            // FragmentTransaction's have access to a Fragment back stack that is very similar to the Activity
            // back stack in your app's task. If you add a FragmentTransaction to the back stack, a user 
            // can use the back button to undo a transaction. We will cover that topic in more depth in
            // the second part of the tutorial.
//    		boolean loggedIn=true;
//    		if(loggedIn){
//    			Intent intent = new Intent(this,LinkListActivity.class);
//    			startActivity(intent);
//    		}else{
//    			Intent intent = new Intent(this,LoginActivity.class);
//    			startActivity(intent);
//    		}
        	
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_activity_id, new LoginFragment());
            ft.commit(); // Make sure you call commit or your Fragment will not be added. 
                         // This is very common mistake when working with Fragments!
        }

		Intent intent = new Intent(this,LinkListActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
		startActivity(intent);

        
        // customize the SlidingMenu
//        SlidingFragmentActivity bb=new SlidingFragmentActivity();
//        SlidingMenu sm = bb.getSlidingMenu();
//    	sm.setShadowWidthRes(R.dimen.shadow_width);
//    	sm.setShadowDrawable(R.drawable.shadow);
//    	sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//    	sm.setFadeDegree(0.35f);
//    	sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

//    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    	getSupportActionBar().setNavigationMode(1);
    
}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//	        toggle();
			return true;
	/*		case R.id.github:
			Util.goToGitHub(this);
			return true;*/
		}
		return super.onOptionsItemSelected(item);
	}

    

}
