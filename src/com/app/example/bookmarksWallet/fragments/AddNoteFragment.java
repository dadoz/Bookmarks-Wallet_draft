package com.app.example.bookmarksWallet.fragments;


import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.R;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Toast;

public class AddNoteFragment extends SherlockFragment{
	ActionBarSherlock mSherlock=ActionBarSherlock.wrap(getActivity());

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.add_note_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	//  toast message wrapper
//	private void toastMessageWrapper(String message){
//		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//	}

	/**BOTTOM static menu**/
/*    public boolean onCreateOptionsMenu(android.view.Menu menu) {
		return mSherlock.dispatchCreateOptionsMenu(menu);
    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 int index=0,order=0;

		 menu.add(0,(index++),order++,"Edit")
		    .setIcon(android.R.drawable.ic_menu_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		 
		 menu.add(0,index++,order++,"Save")
		 .setIcon(android.R.drawable.ic_menu_add)
		 .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		 menu.add(0,index++,order++,"Delete")
		    .setIcon( android.R.drawable.ic_menu_delete)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
	    	case 0:
	    		toastMessageWrapper("Edit action");
	    		break;
	    	case 1:
	    		toastMessageWrapper("Save action");
	    		break;
	    	case 2:
	    		toastMessageWrapper("Delete action");
	    		break;
    	}
    	return true;
    }
    */
}
