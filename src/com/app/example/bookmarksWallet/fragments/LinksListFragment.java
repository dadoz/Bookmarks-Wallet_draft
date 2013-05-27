package com.app.example.bookmarksWallet.fragments;

import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.fragments.NotesListFragment.CustomAdapter;
import com.app.example.bookmarksWallet.models.Link;
import com.app.example.bookmarksWallet.models.Note;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class LinksListFragment extends SherlockFragment {
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
    	ArrayList<String> linksUrlArray = null;
		//Populate the list
		//TODO change iconPath on DB
		boolean deletedLinkFlag=false;
    	ArrayList<Link> linksDataList=new ArrayList<Link>();    	
//    	boolean emptyListFromDb=true;
    	
    	/**get all view I need**/
    	final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//    	final LinearLayout linksListView = (LinearLayout)getActivity().findViewById(R.id.linksListId);
//    	final LinearLayout linksListView = (LinearLayout)getActivity().findViewById(R.id.linksListId);

    	/**CREATE ListView **/
    	try{
			//fetch data
			String result=DatabaseCommon.fetchDataFromDb(SharedData.LINKS_DB);
			linksDataList = DatabaseCommon.getLinksListFromJSONData(result);
			Log.d("createLayout_TAG","url link to be shown"+linksDataList.toString());
    	}catch(Exception e){
    		Log.e("createLayout_TAG","error - " + e);
    	}

    	//TEST - empty list
    	linksUrlArray=new ArrayList<String>();
		linksUrlArray.add("heavy metal");
		linksUrlArray.add("pop");
		linksUrlArray.add("underground");
		linksUrlArray.add("heavy metal");
		linksUrlArray.add("underground");
		linksUrlArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		linksUrlArray.add("pop");
		linksUrlArray.add("heavy metal");
    	int linkId=0;
    	String linkUrl="http://www.google.it";
    	int userId=0;
    	
//    		public Link(int linkId,String linkIconPath,String linkName,String linkUrl,int userId,String delIcon,boolean deletedLinkFlag)
//    	int listSize=linksUrlArray.size();
//    	if(linksDataList!=null && linksDataList.size()>0){
//			emptyListFromDb=false;
//			listSize= linksDataList.size();
//		}   
		
    	if(linksDataList==null || linksDataList.size()>0)
        	for(int i=0;i<linksUrlArray.size();i++)
           		linksDataList.add(new Link(linkId++,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));

    	ArrayAdapter<Link> adapter=new LinkCustomAdapter(getActivity());
		adapter.addAll(linksDataList);
		linksListView.setAdapter(adapter);

    	//set noteList to sharedData fx
    	SharedData.setLinksList(linksDataList);
    	
//    	for(int i=0;i<listSize;i++){
//    		//TEST
//    		if(emptyListFromDb)
//        		linksDataList.add(new Link(linkId++,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));
//    	}
    	
    }
    
    //TO BE IMPLEMENTED !!!! PLEZ take care of it
    public boolean checkURL(String linkUrl){
    	//check URL with regex
    	return true;
    }

    public static void checkLinkIsDeleted(boolean value,View view){
    	String message="DELETE FAILED";    	
    	if(value)
    		message="ITEM DELETED - plez refresh";

  		Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

    }
	//open link
	public void openLinkOnBrowser(String linkUrl){
		toastMessageWrapper("get link on Browser - "+linkUrl);
//		RelativeLayout relViewRow=(RelativeLayout)childView;
//		//cast View to text view (cos the listView obj is a textview)
//		TextView urlSelectedTextView=(TextView)relViewRow.getActivity().findViewById(R.id.linkNameTextId);

		try{
//			String urlSelected=(String) urlSelectedTextView.getText();
//			urlSelected=ApplicationCheckUserLoggedIn.findUrlFromLinkName(urlSelected);
    		//TEST - print out the text of obj selected
    		toastMessageWrapper("URL selected "+linkUrl);
    		 
    		//check if linkUrl is right parsed :D
    		if(checkURL(linkUrl)){
        		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        		getActivity().startActivity(browserIntent);
    		}else
        		toastMessageWrapper("your URL is wrong "+linkUrl);

		}catch(Exception e){
    		Log.e("MY_TAG","error - " + e);
    		toastMessageWrapper("I cant load your URL "+ e);
		}
	}
    //delete link
	public void deleteLink(String linkName){
		//get context menu - doesnt work :(
//		linksListView.showContextMenu();
//		RelativeLayout relViewRow=(RelativeLayout)childView;
		//cast View to text view (cos the listView obj is a textview)
//		TextView nameTextView=(TextView)relViewRow.getActivity().findViewById(R.id.linkNameTextId);
//		nameTextView=(TextView)parentView.getChildAt(0);
		
//		ImageView delIcon=(ImageView)relViewRow.getActivity().findViewById(R.id.delIconId);
		
//		delIcon.setVisibility(View.VISIBLE);
		
//		parentView.showContextMenuForChild(linksListView);
		if(linkName!=null){
			boolean check=DatabaseCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB,linkName);
			if(check)
				toastMessageWrapper("ITEM DELETED - plez refresh");
			else
				toastMessageWrapper("DELETE FAILED");
		}
//		return true ;
	}

//    public void onBackPressed(){
//    	super.onBackPressed();
//    	Log.v("MY_TAG","back_pressed");
//    	return;
//    }    
//    toast message wrapper
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
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 
		 menu.add(0,index++,order++,"Save")
		 .setIcon(android.R.drawable.ic_menu_add)
		 .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		 menu.add(0,index++,order++,"Delete")
		    .setIcon( android.R.drawable.ic_menu_delete)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
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

    
	public class LinkCustomAdapter extends ArrayAdapter<Link> {
		
		public LinkCustomAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.link_row, null);
			}

			View view =getActivity().getLayoutInflater().inflate(R.layout.link_row,null);
//			Link linkObj = getItem(position);
//			view.findViewById(R.id.link_icon_id);
			TextView linkTitle = (TextView)view.findViewById(R.id.link_title_id);
			linkTitle.setText(getItem(position).getLinkName());
//			view.findViewById(R.id.preview_icon_id);
//			linksListView.addView(view);
			
			//TODO - fix ulr open (I guess it will link always to last Url)
//			if(!emptyListFromDb)
//				linkUrl=linksDataList.get(i).getLinkUrl();

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
			final String linkUrlFinal=getItem(position).getLinkUrl();
			view.findViewById(R.id.link_preview_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openLinkOnBrowser(linkUrlFinal);
				}
			});
		    
			return convertView;
		}

	}
	

}
