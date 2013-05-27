package com.app.example.bookmarksWallet.fragments;


import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.models.Link;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NotesListFragment extends SherlockFragment{
	ActionBarSherlock mSherlock=ActionBarSherlock.wrap(getActivity());

//	private static final String TAG = "NotesListFragment_TAG";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.notes_list_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		createLayout();
	}

    public void createLayout(){
    	ArrayList<String> linksUrlArray = null;
    	
    	/**get all view I need**/
    	final LinearLayout notesListView = (LinearLayout)getActivity().findViewById(R.id.notesListId);

		boolean deletedLinkFlag=false;
    	linksUrlArray=new ArrayList<String>();
		linksUrlArray.add("note 1");
		linksUrlArray.add("find your pippo friends");
		linksUrlArray.add("check my party note");
		linksUrlArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		
    	int linkId=0;
    	String linkUrl="http://www.google.it";
    	int userId=0;
    	
    	ArrayList<Link> linksDataList=new ArrayList<Link>();
    	for(int i=0;i<linksUrlArray.size();i++){
    		linksDataList.add(new Link(linkId,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));

    		View view =getActivity().getLayoutInflater().inflate(R.layout.link_row,null);
        	Link linkObj = linksDataList.get(i);
        	TextView linkTitle = (TextView)view.findViewById(R.id.link_title_id);
        	linkTitle.setText(linkObj.getLinkName());
        	notesListView.addView(view);
        	
        	//attach event to actionLayout and preview layout
			view.findViewById(R.id.link_action_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					toastMessageWrapper("get links action bottom menu");						
					Activity activity = getActivity();
					if(activity instanceof FragmentChangeActivity) {
					    ((FragmentChangeActivity) activity).getLinkActionBar();
					}
				}
			});
        	//attach event to actionLayout and preview layout
			view.findViewById(R.id.link_preview_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					toastMessageWrapper("get links preview");
				}
			});
    	}        	
    }
    
//  toast message wrapper
	private void toastMessageWrapper(String message){
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	/**BOTTOM static menu**/
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
}
