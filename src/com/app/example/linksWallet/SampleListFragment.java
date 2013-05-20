package com.app.example.linksWallet;



import com.app.example.linksWallet.fragments.LinkListFragment;
import com.app.example.linksWallet.fragments.LoginFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
//import android.support.v4.widget.SimpleCursorAdapter;
//import android.util.AttributeSet;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

public class SampleListFragment extends ListFragment {

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

		setListAdapter(adapter);
	}

	private class SampleItem {
		public int itemId;
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
		public SampleItem(int itemId,String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.itemId=itemId;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		private static final String D = "Debug";

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

//			profile pict plus username
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
			Log.d("debug", "case 0");

//			newContent = new ColorFragment(R.color.red);
			newContent=new LinkListFragment();
			break;
		case 1:
//			newContent = new ColorFragment(R.color.green);
			Log.d("debug", "case 1");
			newContent=new LoginFragment();
			break;
//		case 2:
//			newContent = new ColorFragment(R.color.blue);
//			break;
//		case 3:
//			newContent = new ColorFragment(android.R.color.white);
//			break;
//		case 4:
//			newContent = new ColorFragment(android.R.color.black);
//			break;
		}
		//newContent=new LoginFragment();
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
//		if (getActivity() == null)
//			return;
		Log.d("debug", "whattahell do u wanna change frame");
		FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment_activity_id, fragment);
		//provide the fragment ID of your first fragment which you have given in
		//fragment_layout_example.xml file in place of first argument
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
		
		
//		if (getActivity() instanceof FragmentChangeActivity) {
//			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
//			fca.switchContent(fragment);
//		}
//		} else if (getActivity() instanceof ResponsiveUIActivity) {
//			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
//			ra.switchContent(fragment);
//		}
	}


	
}
