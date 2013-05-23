package com.app.example.bookmarksWallet.fragments;

import java.util.ArrayList;

import com.app.example.bookmarksWallet.ApplicationCheckUserLoggedIn;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.models.Link;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LinksListFragment extends Fragment {
    // Debugging
    private static final String TAG = "ActivityLinksList_TAG";
//    private static final boolean D = true;

	//they MUST BE EQUALS TO THE ONES IN THE PHP file !!!!
//	private static final int USERS_DB = 98;
	private static final int LINKS_DB = 99;
	
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
    	ArrayList<Link> linksObjArray=null;
    	
    	/**get all view I need**/
//    	final ListView linksListView = (ListView)getActivity().findViewById(R.id.linksListId);
//    	final LinearLayout linksListView = (LinearLayout)getActivity().findViewById(R.id.linksListId);
    	final LinearLayout linksListView = (LinearLayout)getActivity().findViewById(R.id.linksListId);

    	/****set get your links layout action***/
//    	LinearLayout userProfile=(LinearLayout)getActivity().findViewById(R.id.userProfileLayoutId);
/*    	userProfile.setOnClickListener(new View.OnClickListener(){        
	        public void onClick(View v) {
          	  	//launch new activity
	        	finish();
	        }
        });
*/
    	/**CREATE ListView **/
    	try{
			//fetch data
			String result=ApplicationCheckUserLoggedIn.fetchDataFromDb(LINKS_DB);
			linksObjArray = ApplicationCheckUserLoggedIn.linksParserJSONData(result);
			Log.d("createLayout_TAG","url link to be shown"+linksObjArray.toString());
    	}catch(Exception e){
    		Log.e("createLayout_TAG","error - " + e);
    	}
    
//    	linksObjArray=null;
    	if(linksObjArray!=null && linksObjArray.size()>0){
//    		public Link(int linkId,String linkIconPath,String linkName,String linkUrl,int userId,String delIcon,boolean deletedLinkFlag)
    		Log.d(getTag(),linksObjArray.toString());

			//Populate the list
    		//TODO change iconPath on DB
    		boolean deletedLinkFlag=false;
        	ArrayList<Link> linksDataList=new ArrayList<Link>();    	
        	for(int i=0;i<linksObjArray.size();i++){
        		linksDataList.add(new Link(
        				linksObjArray.get(i).getLinkIdFromLinkName(linksObjArray.get(i).getLinkName()),
        				"ic_menu_directions", 
        				linksObjArray.get(i).getLinkName(),
        				linksObjArray.get(i).getLinkUrl(),
        				linksObjArray.get(i).getUserId(),
        				null,
        				deletedLinkFlag));
        		
	    		View view =getActivity().getLayoutInflater().inflate(R.layout.link_row,null);
	        	Link linkObj = linksDataList.get(i);
	//        	view.findViewById(R.id.link_icon_id);
	        	TextView linkTitle = (TextView)view.findViewById(R.id.link_title_id);
	        	linkTitle.setText(linkObj.getLinkName());
	//        	view.findViewById(R.id.preview_icon_id);
	        	linksListView.addView(view);
	        	
	        	//attach event to actionLayout and preview layout
				view.findViewById(R.id.link_action_layout_id).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						toastMessageWrapper("get links action bottom menu");						
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
        	
        	
        	
 /*  			CustomAdapter adapter = new CustomAdapter(getActivity(),R.layout.row,linksDataList);
   			linksListView.setAdapter(adapter);
   			
   			//-------------------ONCLICK listeners------------------------   			
   	    	//long click to get action menu - android JB4.1
    		linksListView.setLongClickable(true);
    		
    		linksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
				}
    			
    		});*/
    		
    		
    		/*    		 
    		//on click I can open the link
    		linksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView parentView, View childView, int position, long id) {
					// TODO Auto-generated method stub

					RelativeLayout relViewRow=(RelativeLayout)childView;
	        		//cast View to text view (cos the listView obj is a textview)
	        		TextView urlSelectedTextView=(TextView)relViewRow.getActivity().findViewById(R.id.linkNameTextId);
	        		 
	        		
	        		
	        		
	        		try
	        		{
	        			String urlSelected=(String) urlSelectedTextView.getText();
	        			urlSelected=ApplicationCheckUserLoggedIn.findUrlFromLinkName(urlSelected);
		        		//TEST - print out the text of obj selected
		        		toastMessageWrapper("URL selected "+urlSelected);
		        		 
		        		
		        		//check if urlSelected is right parsed :D
		        		;
		        		
		        		if(checkURL(urlSelected)==true)
		        		{
			        		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlSelected));
			        		startActivity(browserIntent);
		        		}
		        		else
		            		toastMessageWrapper("your URL is wrong "+urlSelected);

	        		}
	        		catch(Exception e)
	        		{
	            		Log.e("MY_TAG","error - " + e);
	            		toastMessageWrapper("I cant load your URL "+ e);

	        		}
	        		
	        		        			
				}
				public void onNothingSelected(AdapterView parentView) 
				{
				}
    			
			});

    		linksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
						public boolean onItemLongClick(AdapterView<?> parentView, View childView, int position, long id) {

							//get context menu - doesnt work :(
//							linksListView.showContextMenu();
							RelativeLayout relViewRow=(RelativeLayout)childView;
			        		//cast View to text view (cos the listView obj is a textview)
			        		TextView nameTextView=(TextView)relViewRow.getActivity().findViewById(R.id.linkNameTextId);
//			        		nameTextView=(TextView)parentView.getChildAt(0);
			        		
			        		ImageView delIcon=(ImageView)relViewRow.getActivity().findViewById(R.id.delIconId);
			        		
			        		delIcon.setVisibility(View.VISIBLE);
			        		
//			        		parentView.showContextMenuForChild(linksListView);
							if(nameTextView!=null)
							{
								boolean check=ApplicationCheckUserLoggedIn.deleteUrlEntryFromDb(LINKS_DB,nameTextView.getText().toString());
								if(check)
									toastMessageWrapper("ITEM DELETED - plez refresh");
								else
									toastMessageWrapper("DELETE FAILED");
									
							}
							return true ;
						}
			});*/
    		
    	}else{
    		Log.d(TAG,"error - no one url fetched");

    		boolean deletedLinkFlag=false;
    		
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
        	
        	ArrayList<Link> linksDataList=new ArrayList<Link>();
        	for(int i=0;i<linksUrlArray.size();i++){
        		linksDataList.add(new Link(linkId,"ic_launcher", linksUrlArray.get(i),linkUrl,userId,"del_icon",deletedLinkFlag));

        		View view =getActivity().getLayoutInflater().inflate(R.layout.link_row,null);
	        	Link linkObj = linksDataList.get(i);
	//        	view.findViewById(R.id.link_icon_id);
	        	TextView linkTitle = (TextView)view.findViewById(R.id.link_title_id);
	        	linkTitle.setText(linkObj.getLinkName());
	//        	view.findViewById(R.id.preview_icon_id);
	        	linksListView.addView(view);
	        	
	        	//attach event to actionLayout and preview layout
				view.findViewById(R.id.link_action_layout_id).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						toastMessageWrapper("get links action bottom menu");						
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
//   			CustomAdapter adapter = new CustomAdapter(getActivity(),R.layout.row,linksDataList);
//        	ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,linksUrlArray);
//   			linksListView.setAdapter(adapter);

    	}	
    }
        
    //TO BE IMPLEMENTED !!!! PLEZ take care of it
    public boolean checkURL(String urlString){
    	//check URL with regex
    	return true;
    }

    public static void checkLinkIsDeleted(boolean value,View view){
    	String message="DELETE FAILED";    	
    	if(value)
    		message="ITEM DELETED - plez refresh";

  		Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

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
	
	
	
	

}
