package com.app.example.linksWallet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
	
}
