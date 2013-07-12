package com.app.example.bookmarksWallet.fragments;


import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.models.ActionLog;
import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;
import com.app.example.db.lib.ActionLogDbAdapter;
import com.app.example.db.lib.DatabaseAdapter;
import com.app.example.db.lib.DatabaseConnectionCommon;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
//import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

public class DBTestFragment extends SherlockFragment{
	ActionBarSherlock mSherlock=ActionBarSherlock.wrap(getActivity());

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.db_test_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onCreateLayout();
	}

	public void onCreateLayout(){
		
		Button actionLogButton = (Button)getActivity().findViewById(R.id.actionLogDbButtonId);
		actionLogButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ActionLogDbAdapter db;
				db=new ActionLogDbAdapter(getActivity());
				// TODO Auto-generated method stub
				ArrayList<ActionLog> actionLogList = DatabaseConnectionCommon.getActionLogWrappLocalDb(db);
				
				TextView result=(TextView)getActivity().findViewById(R.id.actionLogDbResultId);
				String resString="";
				if(actionLogList!=null)
					for(ActionLog obj:actionLogList)
						resString+=" "+ obj.getActionLogAction()+obj.getActionLogModel()+obj.getActionLogModelId()+"\n";
				
				if(actionLogList==null)
					result.setText("emptyList");
				else
					result.setText(resString);
				
				
			}
		});
		Button cancelActionLogDbButton = (Button)getActivity().findViewById(R.id.cancelActionLogDbButtonId);
		cancelActionLogDbButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ActionLogDbAdapter db;
				db=new ActionLogDbAdapter(getActivity());
				// TODO Auto-generated method stub
				DatabaseConnectionCommon.deleteAllActionLogWrappLocalDb(db);
				
				TextView result=(TextView)getActivity().findViewById(R.id.actionLogDbResultId);
				String resString="OK - del successful";
				
				result.setText(resString);
			}
		});

		
		Button linkDbButton = (Button)getActivity().findViewById(R.id.linkDbButtonId);
		linkDbButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				DatabaseAdapter db;
				db=new DatabaseAdapter(getActivity());
				// TODO Auto-generated method stub
				ArrayList<Link> linkList = DatabaseConnectionCommon.getLinksWrappTESTLocalDb(db);
				
				TextView result=(TextView)getActivity().findViewById(R.id.linkDbResultId);
				String resString="";
				
				if(linkList!=null)
					for(Link obj:linkList)
						resString+="LINK: id "+ obj.getLinkId()+"\n- name "+obj.getLinkName()+"\n- URL "+obj.getLinkUrl()+
						"\n- linkDel "+obj.isLinkDeleted()+"\n\n\n";
				
				if(linkList==null)
					result.setText("empty list");
				else
					result.setText(resString);
			}
		});

		Button cancelLinksDbButton = (Button)getActivity().findViewById(R.id.cancelLinksDbButtonId);
		cancelLinksDbButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				DatabaseAdapter db;
				db=new DatabaseAdapter(getActivity());
				// TODO Auto-generated method stub
				DatabaseConnectionCommon.deleteLinksWrappLocalDb(db);
				
				TextView result=(TextView)getActivity().findViewById(R.id.linkDbResultId);
				String resString="OK - del successful";
				
				result.setText(resString);
			}
		});
		

	
		Button onlineLinkDbButton = (Button)getActivity().findViewById(R.id.onlineLinksDbButtonId);
		onlineLinkDbButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
//				DatabaseAdapter db;
//				db=new DatabaseAdapter(getActivity());
				ArrayList<Link> linkList=null;
				TextView result=(TextView)getActivity().findViewById(R.id.onlineLinksDbResultId);
				// TODO Auto-generated method stub
				
				if(!SharedData.isNetworkAvailable(getActivity())){
					result.setText("NO INTERNET CONNECTION");
					return;
				}
				
				linkList = DatabaseConnectionCommon.getLinksListFromJSONData();
				
				String resString="";
				if(linkList!=null)
					for(Link obj:linkList)
						resString+="LINK: id "+ obj.getLinkId()+"\n- name "+obj.getLinkName()+"\n- URL "+obj.getLinkUrl()+
						"\n- linkDel "+obj.isLinkDeleted()+"\n\n\n";
				
				if(linkList==null)
					result.setText("empty list");
				else
					result.setText(resString);
			}
		});
		
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
