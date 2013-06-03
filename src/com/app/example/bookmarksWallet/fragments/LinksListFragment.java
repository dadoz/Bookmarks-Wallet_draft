package com.app.example.bookmarksWallet.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.DatabaseAdapter;
import com.app.example.db.lib.DatabaseConnectionCommon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LinksListFragment extends SherlockFragment {
	private static final String TAG = "LinksListFragment_TAG";
	ActionBarSherlock mSherlock=ActionBarSherlock.wrap(getActivity());


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.links_list_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final DatabaseAdapter db= new DatabaseAdapter(getActivity());
		createLayout(db);
	}
	
    public void createLayout(DatabaseAdapter db){
    	final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);

    	//get linkList
//    	ArrayList<Link> linksDataList=SharedData.getLinksListStatic();
    	
    	ArrayList<Link> linksDataList = DatabaseConnectionCommon.getLinksWrappLocalDb(db);
    	if(linksDataList==null){
    		Log.d(TAG,"set list from JSON data");
    		try{
	    		//TODO change iconPath on DB
				linksDataList = DatabaseConnectionCommon.getLinksListFromJSONData();
				for(Link link:linksDataList)
					DatabaseConnectionCommon.insertLinkWrappLocalDb(db,link);
	    	}catch(Exception e){
	    		Log.e(TAG,"error - " + e);
	    	}
    	}
    	//TEST - empty list
    	if(linksDataList==null){
    		Log.d(TAG,"set list TEST data");
    		linksDataList=testLinksList();
    	}

    	if(linksDataList!=null && linksDataList.size()==0){
    		//TODO handle empty - need to be refreshed
    		((TextView)getActivity().findViewById(R.id.linkList_empty_label)).setText("Empty List");
    	}
    	
    	ArrayAdapter<Link> adapter=new LinkCustomAdapter(getActivity(), R.layout.link_row, linksDataList);
		linksListView.setAdapter(adapter);
		//single mode choiced
//		linksListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//		linksListView.setSelector(R.drawable.custom_selector);
//		linksListView.setFocusableInTouchMode(true);
//		linksListView.setSelector(R.drawable.custom_selector);
//		linksListView.setDrawSelectorOnTop(true);
//		linksListView.addHeaderView(getView());
//		linksListView.addFooterView(getView());
    	//set noteList to sharedData fx
    	SharedData.setLinksList(linksDataList);
    	getSherlockActivity().registerForContextMenu(linksListView);
    }
	/**OPEN LINK***/
	public void openLinkOnBrowser(String linkUrl){
		toastMessageWrapper("get link on Browser - "+linkUrl);
		try{
    		//TEST - print out the text of obj selected
    		toastMessageWrapper("URL selected "+linkUrl);
    		//check if linkUrl is right parsed :D
    		if(checkURL(linkUrl)){
        		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        		getActivity().startActivity(browserIntent);
    		}else
        		toastMessageWrapper("your URL is wrong "+linkUrl);

		}catch(Exception e){
    		Log.e(TAG,"error - " + e);
    		toastMessageWrapper("I cant load your URL "+ e);
		}
	}
    //TODO TO BE IMPLEMENTED !!
    public boolean checkURL(String linkUrl){
    	//check URL with regex
    	return true;
    }
	/**DELETE LINK
	 * @param linksListView 
	 * @param linkObj***/
	public boolean deleteLink(Link linkObj, ListView linksListView){
		if(linkObj!=null){
			
			if(DatabaseConnectionCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB,SharedData.LOCAL_DB,linkObj.getLinkId(),getActivity())){
				((LinkCustomAdapter) linksListView.getAdapter()).remove(linkObj);
				//SharedData.removeLink(linkObj);
				//delete from local db
				//TODO TEST rm link from online db remove it
//				DatabaseConnectionCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB,SharedData.ONLINE_DB,linkObj.getLinkId());
				toastMessageWrapper("Item deletedx - "+linkObj.getLinkName());
				return true;
			}
			//TEST
			Log.d(TAG, "link namexs - "+ linkObj.getLinkName());
			return false;
		}
		return false;
	}
	/**EDIT LINK***/
	public boolean editLink(Link linkObj, ListView linksListView){
		toastMessageWrapper("NEED TO BE IMPLEMENTED");
		
		return false;
	}
	/**SHARE LINK***/
	public boolean shareLink(Link linkObj){
		Intent shareLinkIntent = new Intent(android.content.Intent.ACTION_MEDIA_SHARED);
		shareLinkIntent.setType("text/plain");
		shareLinkIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, linkObj.getLinkName());
		shareLinkIntent.putExtra(android.content.Intent.EXTRA_TEXT, linkObj.getLinkUrl());
		startActivity(shareLinkIntent);
		return true;
	}

	
//    public void onBackPressed(){
//    	super.onBackPressed();
//    	Log.v("MY_TAG","back_pressed");
//    	return;
//    }    
	/**TOAST MESSAGGE WRAPPER**/
	private void toastMessageWrapper(String message){
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
	
    public ArrayList<Link> testLinksList(){
    	ArrayList<Link> linksDataList = new ArrayList<Link>();
    	ArrayList<String> linksUrlArray= new ArrayList<String>();
		boolean deletedLinkFlag=false;

    	linksUrlArray.add("heavy metal1");
		linksUrlArray.add("pop1");
//		linksUrlArray.add("underground");
//		linksUrlArray.add("heavy metal");
//		linksUrlArray.add("underground");
		linksUrlArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		linksUrlArray.add("bla1");
		linksUrlArray.add("link2");
    	String linkUrl="http://www.google.it";
    	int userId=0;
    	for(int i=0;i<linksUrlArray.size();i++)
       		linksDataList.add(new Link(i,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));
    	
    	return linksDataList;
    }
    /**CUSTOM LAYOUT CLASS**/
	public class LinkCustomAdapter extends ArrayAdapter<Link> {
		public LinkCustomAdapter(Context context, int linkViewResourceId,
                ArrayList<Link> items) {
			super(context, linkViewResourceId, items);
		}
		
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) 
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.link_row, null);
			
			TextView linkTitle = (TextView)convertView.findViewById(R.id.link_title_id);
			linkTitle.setText(getItem(position).getLinkName());

			//attach event to actionLayout and preview layout
			convertView.findViewById(R.id.link_action_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					toastMessageWrapper("get links action bottom menu");
//					view.setBackgroundColor(Color.parseColor("#ff0000"));
					Activity activity = getActivity();
					if(activity instanceof FragmentChangeActivity) {
						Log.i(TAG, "TextView clicked on row " + position);
						SharedData.setLinkPosition(position);
				    	getSherlockActivity().startActionMode(new linkOverActionBar());
					}

				}
				
			});
			
			
			// set the listview not scrollable - lock the touch on 
//			convertView.findViewById(R.id.link_action_layout_id).setOnTouchListener(new View.OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//		            int action = event.getAction();
//		            switch (action) {
//		            case MotionEvent.ACTION_DOWN:
//		                // Disallow ScrollView to intercept touch events.
//		                v.getParent().requestDisallowInterceptTouchEvent(true);
//		                break;
//
//		            case MotionEvent.ACTION_UP:
//		                // Allow ScrollView to intercept touch events.
//		                v.getParent().requestDisallowInterceptTouchEvent(false);
//		                break;
//		            }
//		            // Handle ListView touch events.
//		            v.onTouchEvent(event);
//		            return true;
//				}
//			});
			
			//attach event to actionLayout and preview layout
			final String linkUrlFinal=getItem(position).getLinkUrl();
			convertView.findViewById(R.id.link_preview_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openLinkOnBrowser(linkUrlFinal);
				}
			});
			return convertView;
		}
		
		
	}

	/**BOTTOM static menu**/
/*    public boolean onCreateOptionsMenu(android.view.Menu menu) {
		return mSherlock.dispatchCreateOptionsMenu(menu);
    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 int index=0,order=0;
		 
		 
		 menu.add(0,(index++),order++,"Select all - FOOTER")
		 	.setIcon(android.R.drawable.ic_menu_edit)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 
//		 menu.add(0,index++,order++,"Save")
//		 .setIcon(android.R.drawable.ic_menu_add)
//		 .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
//		 menu.add(0,index++,order++,"Delete FOOTER")
//		    .setIcon( android.R.drawable.ic_menu_delete)
//		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//		 final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//		 ListAdapter linksListAdapter = linksListView.getAdapter();
//		 // Retrieve the item that was clicked on
//		 Link linkObj = (Link) linksListAdapter.getItem(info.position);
    	
    	
    	switch(item.getItemId()){
	    	case 0:
	    		toastMessageWrapper("Select all links");
	    		break;
//	    	case 1:
//	    		toastMessageWrapper("Delete action");
//	    		deleteLink(null);
//	    		break;
    	}
    	return true;
    }

    OnMenuItemClickListener deleteLinkClickListener = new OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {  
//        	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//	   		final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//	   		ListAdapter linksListAdapter = linksListView.getAdapter();
	   		// Retrieve the item that was clicked on
//	   		if(linksListAdapter!=null){
//	   			Link linkObj = (Link) linksListAdapter.getItem(info.position);
//	   			deleteLink(linkObj,linksListView);
//	   		}
            return false;
        }
    };*/

	
	/**OVER ACTION BAR impl**/
	public final class linkOverActionBar implements ActionMode.Callback {
	    private static final String TAG ="linkOverActionBar_TAG";

		@Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        //Used to put dark icons on light action bar
	        int index=0;
	        int order=0;
	        
	        menu.add(0,index++,order++,"Share")
	            .setIcon(android.R.drawable.ic_menu_share)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        menu.add(0,index++,order++,"Edit")
	            .setIcon(android.R.drawable.ic_menu_edit)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        menu.add(0,index++,order++,"Delete")
	            .setIcon(android.R.drawable.ic_menu_delete)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        return true;
	    }

	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false;
	    }

	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//	    	Log.d(TAG, ""+ SharedData.getLinkPosition());
	    	if(SharedData.getLinkPosition()!=SharedData.LINK_NOT_IN_LIST){
		    	Link linkObj = (Link) linksListView.getAdapter().getItem(SharedData.getLinkPosition());
		    	
		    	switch(item.getItemId()){
		    	case 0:
		    		//SHARE opt
		    		toastMessageWrapper("Share your link on ....");
		    		if(linkObj!=null)
		    			if(!shareLink(linkObj))
		    				toastMessageWrapper("Item sharing Failed- "+linkObj.getLinkName());
		    		break;
		    	case 1:
		    		//EDIT opt
		    		toastMessageWrapper("Edit your link");
//		            openContextMenu(item);
		    		if(linkObj!=null)
		    			if(!editLink(linkObj,linksListView))
		    				toastMessageWrapper("Item edit Failed- "+linkObj.getLinkName());
    				toastMessageWrapper("Edit on menu - Link not found on ListView");
		    		break;
		    	case 2:
		    		//DEL opt
		    		toastMessageWrapper("Delete this link");
		    		if(linkObj!=null)
		    			if(!deleteLink(linkObj,linksListView))
		    				toastMessageWrapper("Item del Failed- "+linkObj.getLinkName());
    				toastMessageWrapper("Delete on menu - Link not found on ListView");
		    		break;
		    	}
	    	}
	    	Log.d(TAG, ""+ item.getTitle());
	    	mode.finish();
	        return true;
	    }

	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    }
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("One");
        menu.add("Two");
        menu.add("Three");
        menu.add("Four");
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        //Note how this callback is using the fully-qualified class name
        Toast.makeText(getActivity(), "Got click: " + item.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }


	
	
}
