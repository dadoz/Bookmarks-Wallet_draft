package com.app.example.linksWallet;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Link>{
	Context context;
	private int layoutResourceId;
	private LayoutInflater inflater;
	Link data[]=null;

//	private static final boolean D=true;
//	private static final String TAG="CustomAdapterTAG";
	protected static final int LINKS_DB = 0;
	
	public CustomAdapter(Context ctx, int layoutResourceId,ArrayList<Link> linksDataList) {
		super(ctx, layoutResourceId,linksDataList);
		this.layoutResourceId=layoutResourceId;
		inflater=LayoutInflater.from(ctx);
		this.context=ctx;
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent)	{
		try	{
			convertView=inflater.inflate(layoutResourceId, null);
			final Link linkObj=(Link) getItem(position);
			//LINK icon
			ImageView linkIcon=(ImageView) convertView.findViewById(R.id.row_icon);
			String drawPath="drawable/"+linkObj.linkIconPath;
			int imageResource=context.getResources().getIdentifier(drawPath,null, context.getPackageName());
			Drawable image=context.getResources().getDrawable(imageResource);
			linkIcon.setImageDrawable(image);
			//TITLE
			final TextView linkName=(TextView) convertView.findViewById(R.id.row_title);
			linkName.setText(linkObj.linkName);

/*			ImageView delIcon=(ImageView) convertView.findViewById(R.id.delIconId);
//			String drawPath2="android:drawable/"+linkObj.delIcon;
			String drawPath2="drawable/"+linkObj.delIcon;
			int imageResource2=context.getResources().getIdentifier(drawPath2, null, context.getPackageName());
			Drawable image2=context.getResources().getDrawable(imageResource2);
			delIcon.setImageDrawable(image2);

			
			
    		delIcon.setOnClickListener(new View.OnClickListener(){
    			public void onClick(View view){
//            		parentView.showContextMenuForChild(linksListView);
    				if(linkName!=null){
//    					boolean check=false;
    					boolean check=ApplicationCheckUserLoggedIn.deleteUrlEntryFromDb(LINKS_DB,linkName.getText().toString());
    					if(check){
    						Log.d(TAG,"item del");
    						linkObj.setDeletedLink(true);
    						LinksListFragment.checkLinkIsDeleted(true,view);
//    						toastMessageWrapper("ITEM DELETED - plez refresh");
    					}else{
//    						toastMessageWrapper("DELETE FAILED");
    						linkObj.setDeletedLink(false);
    						LinksListFragment.checkLinkIsDeleted(true,view);
    						Log.d(TAG,"item not del");
    					}
    				}
    				
    			}
    		});
			delIcon.setVisibility(View.INVISIBLE);*/
		}catch(Exception e){
			Log.e("errorTAG",""+e);
		}
		return convertView;
	}

	static class LinkHolder{
		ImageView linkIcon;
		TextView linkName;
		ImageView delIcon;
	}
}
