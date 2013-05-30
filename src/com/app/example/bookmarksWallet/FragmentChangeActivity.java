package com.app.example.bookmarksWallet;

//import android.content.pm.ActivityInfo;
//import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.app.example.bookmarksWallet.fragments.LinksListFragment;
import com.app.example.bookmarksWallet.fragments.MenuListFragment;
//import com.app.example.bookmarksWallet.fragments.NotesListFragment;
import com.app.example.common.lib.ActionModeForActionOverflowBar;
import com.app.example.common.lib.SharedData.Fragments;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class FragmentChangeActivity extends BaseActivity {
	
	private Fragment mContent;
	
	public FragmentChangeActivity() {
		super(R.string.changing_fragments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set actionbar color 
//		int cobaltGreenColor = (getResources().getColor(R.color.cobaltGreen));
//		getActionBar().setBackgroundDrawable(new ColorDrawable(cobaltGreenColor));
		
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new LinksListFragment();	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuListFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		mContent.setHasOptionsMenu(true); //here
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
	
	public void getLinkActionBar(Fragments fragmentId){
//		if(fragmentId==SharedData.Fragments.LINKS_LIST)
			//startActionMode(new linkOverActionBar());
//		else
			startActionMode(new ActionModeForActionOverflowBar());
	}
}
