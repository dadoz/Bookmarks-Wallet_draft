package com.app.example.bookmarksWallet.fragments;

import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
//import com.app.example.bookmarksWallet.fragments.LogoutFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());

		int i=0;
		adapter.add(new SampleItem(i++,"Tunnus", R.drawable.profile_picture));
		adapter.add(new SampleItem(i++,"Add-write a note", android.R.drawable.ic_menu_add));
		adapter.add(new SampleItem(i++,"Note list", android.R.drawable.ic_menu_agenda));
		adapter.add(new SampleItem(i++,"Links list", android.R.drawable.ic_menu_directions));
		
		adapter.add(new SampleItem(i++,"Settings", android.R.drawable.ic_menu_preferences));
		adapter.add(new SampleItem(i++,"LOGOUT", android.R.drawable.ic_lock_power_off));
		adapter.add(new SampleItem(i++,"*****-TEST-******", android.R.drawable.ic_menu_help));

		setListAdapter(adapter);
	}

	private class SampleItem {
		public int itemId;
		public String tag;
		public int iconRes;
//		public SampleItem(String tag, int iconRes) {
//			this.tag = tag; 
//			this.iconRes = iconRes;
//		}
		public SampleItem(int itemId,String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.itemId=itemId;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {
		
		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}

			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

//			profile pict and username
			if(getItem(position).itemId==0){
				convertView.setPadding(0, 20, 0, 20);
				icon.getLayoutParams().width=150;
				icon.getLayoutParams().height=150;
				convertView.setEnabled(false);
			}
			return convertView;
		}
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			//PROFILE pict
			Log.d("debug", "case 0");
//			newContent = new ColorFragment(R.color.red);
			break;
		case 1:
			//ADD - WRITE a note
			Log.d("debug", "case 1");
//			newContent=new LoginFragment();
			newContent=new AddNoteFragment();
			break;
		case 2:
			//NOTES list
			newContent=new NotesListFragment();
			break;
		case 3:
			//LINKS list
			newContent=new LinksListFragment();
			break;
		case 4:
			//SETTINGS
			newContent=new SettingsFragment();
			break;
		case 5:
			//LOGOUT
			logoutAction();
			break;
		case 6:
			//LOGOUT
			newContent=new DBTestFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}
	//switch fragment clicking on link menu
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		}
//        FragmentTransaction fragmentTransaction = getFragmentManager()
//                .beginTransaction();
		//provide the fragment ID of your first fragment which you have given in
		//fragment_layout_example.xml file in place of first argument
//        fragmentTransaction.replace(R.id.content_frame, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        

	}
	/****set logout button action***/
	@SuppressWarnings("static-access")
	public void logoutAction(){
		String result = "logout";
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result",result);
		getActivity().setResult(getActivity().RESULT_OK,returnIntent);   
		getActivity().finish();
	}
    //toast message wrapper
//	private void toastMessageWrapper(String message){
//		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//	}

}
