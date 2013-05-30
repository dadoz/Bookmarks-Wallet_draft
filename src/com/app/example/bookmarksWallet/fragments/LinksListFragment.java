package com.app.example.bookmarksWallet.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.internal.widget.IcsAdapterView.AdapterContextMenuInfo;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
//import com.app.example.bookmarksWallet.FragmentChangeActivity.linkOverActionBar;
import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.DatabaseCommon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
		createLayout();
	}
	
    public void createLayout(){
    	ArrayList<Link> linksDataList=new ArrayList<Link>();    	
    	final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
    	try{
    		//TODO change iconPath on DB
			linksDataList = DatabaseCommon.getLinksListFromJSONData();
			//TEST
	    	if(linksDataList==null || linksDataList.size()==0)
				Log.d(TAG,"links from db EMPTY");
    	}catch(Exception e){
    		Log.e(TAG,"error - " + e);
    	}
    	//TEST - empty list
    	if(linksDataList==null || linksDataList.size()==0)
    		linksDataList=testLinksList();

    	ArrayAdapter<Link> adapter=new LinkCustomAdapter(getActivity());
		adapter.addAll(linksDataList);
		linksListView.setAdapter(adapter);
    	//set noteList to sharedData fx
    	SharedData.setLinksList(linksDataList);

    	getSherlockActivity().registerForContextMenu(linksListView);
    	
    	//TEST
    	for(Link link:linksDataList)
    		Log.d(TAG,link.getLinkName());
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
	 * @param linksListView ***/
	public boolean deleteLink(Link linkObj, ListView linksListView){
		if(linkObj!=null){
			if(DatabaseCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB,linkObj.getLinkId())){
				linksListView.removeViewAt(SharedData.getLinkPosition());
				toastMessageWrapper("ITEM DELETED");
				return true;
			}
			//TEST
			Log.d(TAG, "link name - "+ linkObj.getLinkName());
			toastMessageWrapper("DELETE FAILED"+linkObj.getLinkName());
			return false;
		}
		toastMessageWrapper("DELETE FAILED");
		return false;
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
		linksUrlArray.add("underground");
		linksUrlArray.add("heavy metal");
		linksUrlArray.add("underground");
		linksUrlArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		linksUrlArray.add("pop2");
		linksUrlArray.add("heavy metal2");
    	String linkUrl="http://www.google.it";
    	int userId=0;
    	for(int i=0;i<linksUrlArray.size();i++)
       		linksDataList.add(new Link(i,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));
    	
    	return linksDataList;
    }
	
	/**BOTTOM static menu**/
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
    };
    /**CUSTOM LAYOUT CLASS**/
	public class LinkCustomAdapter extends ArrayAdapter<Link> {
		public LinkCustomAdapter(Context context) {
			super(context, 0);
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
					Activity activity = getActivity();
					if(activity instanceof FragmentChangeActivity) {
						Log.i(TAG, "TextView clicked on row " + position);
						SharedData.setLinkPosition(position);
				    	getSherlockActivity().startActionMode(new linkOverActionBar());
					}

				}
			});
			
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

	
	/**OVER ACTION BAR impl**/
	public final class linkOverActionBar implements ActionMode.Callback {
	    private static final String TAG ="linkOverActionBar_TAG";

		@Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        //Used to put dark icons on light action bar
	        int index=0;
	        int order=0;
	        
	        menu.add(0,index++,order++,"Save")
	            .setIcon(android.R.drawable.ic_menu_add)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        menu.add(0,index++,order++,"Search")
	            .setIcon(android.R.drawable.ic_menu_search)
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
			ListAdapter linksListAdapter = linksListView.getAdapter();
//	    	Log.d(TAG, ""+ SharedData.getLinkPosition());
	    	if(SharedData.getLinkPosition()!=-1){
		    	Link linkObj = (Link) linksListAdapter.getItem(SharedData.getLinkPosition());
		    	
		    	switch(item.getItemId()){
		    	case 0:
		    		toastMessageWrapper("Save smthing");
		    		break;
		    	case 1:
		    		toastMessageWrapper("Search your link");
		    		break;
		    	case 2:
		    		toastMessageWrapper("Delete this link");
		    		if(linkObj!=null)
		    			deleteLink(linkObj,linksListView);
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
}
