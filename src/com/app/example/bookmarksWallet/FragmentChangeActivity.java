package com.app.example.bookmarksWallet;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.app.example.bookmarksWallet.fragments.LinksListFragment;
import com.app.example.linksWallet.R;
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
		int basicRedColor = (getResources().getColor(R.color.basicRed));
		getActionBar().setBackgroundDrawable(new ColorDrawable(basicRedColor));
		
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
//      FragmentTransaction fragmentTransaction = getFragmentManager()
//      .beginTransaction();
//provide the fragment ID of your first fragment which you have given in
//fragment_layout_example.xml file in place of first argument
//fragmentTransaction.replace(R.id.content_frame, fragment);
//fragmentTransaction.addToBackStack(null);
//fragmentTransaction.commit();
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

}
