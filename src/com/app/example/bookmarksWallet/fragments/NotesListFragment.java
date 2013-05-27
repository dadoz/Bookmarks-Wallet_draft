package com.app.example.bookmarksWallet.fragments;


import java.util.ArrayList;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.app.example.bookmarksWallet.FragmentChangeActivity;
import com.app.example.bookmarksWallet.R;
import com.app.example.bookmarksWallet.models.Note;
import com.app.example.common.lib.SharedData;

import android.app.Activity;
import android.content.Context;
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

public class NotesListFragment extends SherlockFragment{
	private static final String TAG = "NoteListFragment_TAG";
	ActionBarSherlock mSherlock=ActionBarSherlock.wrap(getActivity());

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.notes_list_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		createLayout();
	}

    public void createLayout(){
    	ArrayList<String> notesTitleArray = null;
    	
    	/**get all view I need**/
    	final ListView notesListView = (ListView)getActivity().findViewById(R.id.notesListId);

    	//STATIC data
    	notesTitleArray=new ArrayList<String>();
		notesTitleArray.add("note 1");
		notesTitleArray.add("find your pippo friends");
		notesTitleArray.add("check my party note");
		notesTitleArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		String noteContentList="bla bla bla - this is the content";
    	
    	ArrayList<Note> notesDataList=new ArrayList<Note>();
    	for(int i=0;i<notesTitleArray.size();i++)
    		notesDataList.add(new Note(i,android.R.drawable.ic_menu_agenda, notesTitleArray.get(i), noteContentList));

    	ArrayAdapter<Note> adapter=new CustomAdapter(getActivity());
		adapter.addAll(notesDataList);
		notesListView.setAdapter(adapter);
		//TEST
    	for (Note note:notesDataList)
    		Log.d(TAG, note.getNoteName()+note.getNoteId());
    	//set noteList to sharedData fx
    	SharedData.setNotesList(notesDataList);
    }
    

    
    //  toast message wrapper
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
    
	public class CustomAdapter extends ArrayAdapter<Note> {
		
		public CustomAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_row, null);
				convertView.setTag(getItem(position).getNoteId());
			}
    		ImageView noteIcon = (ImageView)convertView.findViewById(R.id.link_icon_id);
    		noteIcon.getResources().getDrawable(getItem(position).getNoteIconPath());
        	TextView noteTitle = (TextView)convertView.findViewById(R.id.link_title_id);
        	noteTitle.setText(getItem(position).getNoteName());

        	TextView noteContent = (TextView)convertView.findViewById(R.id.note_preview_text_id);
        	noteContent.setText(getItem(position).getNoteContent());
        	if(!getItem(position).isNotePreviewVisible())
        		convertView.findViewById(R.id.note_preview_layout_id).setVisibility(View.INVISIBLE);

        	
//        	Log.d(getTag()," -- " + getItem(position).getNoteName());
        	final int staticNoteId = getItem(position).getNoteId();
        	//attach event to actionLayout and preview layout
        	convertView.findViewById(R.id.link_action_layout_id).setOnClickListener(new View.OnClickListener() {
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
        	convertView.findViewById(R.id.link_preview_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					Log.d(getTag(),"... "+v);
					toggleNotePreview(staticNoteId,v);
				}
			});
			return convertView;
		}

	}

    public void toggleNotePreview(int noteId,View v){
    	//if true show notePreview else hide it
		Log.d(TAG, "toggle fx... "+noteId);
    	LinearLayout notePreviewLayout = (LinearLayout)getActivity().findViewById(R.id.note_preview_layout_id).findViewWithTag(noteId);
    	Note noteObj=SharedData.getNoteById(noteId);
    	if(noteObj!=null){
	    	if(!SharedData.getNoteById(noteId).isNotePreviewVisible()){
	    		Log.d(getTag(), "visible"+noteId);
	    		noteObj.setNotePreviewVisible(true);
	    		notePreviewLayout.setVisibility(View.VISIBLE);
	    	}else{
	    		Log.d(getTag(), "invisible"+noteId);
	    		noteObj.setNotePreviewVisible(false);
		    	notePreviewLayout.setVisibility(View.INVISIBLE);
	    	}
    	}	
	    
    }
    
    
}
