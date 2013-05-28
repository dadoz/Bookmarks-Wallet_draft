package com.app.example.common.lib;


import android.util.Log;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public final class ActionModeForActionOverflowBar implements ActionMode.Callback {
    private static final String TAG ="ActionModeForBottomBar_TAG";

	@Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = false;
        int index=0;
        int order=0;
        

//		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//		  ListView linksListView = (ListView)mode.getCustomView().findViewById(R.id.linksListId);
//		 ListAdapter linksListAdapter = linksListView.getAdapter();
//		 // Retrieve the item that was clicked on
//		 Link linkObj = (Link) linksListAdapter.getItem(info.position);
        
        menu.add(0,index++,order++,"Save")
            .setIcon(isLight ? android.R.drawable.ic_menu_add : android.R.drawable.ic_menu_add)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0,index++,order++,"Search")
            .setIcon(isLight ? android.R.drawable.ic_menu_search : android.R.drawable.ic_menu_search)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0,index++,order++,"Refresh")
            .setIcon(isLight ? android.R.drawable.ic_menu_rotate : android.R.drawable.ic_menu_rotate)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    	
//        Toast.makeText(, "Got click: " + item, Toast.LENGTH_SHORT).show();
    	Log.d(TAG, "xx - "+ item.getTitle()+item.getItemId()+item.getOrder());
    	mode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
}