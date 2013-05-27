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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    	final LinearLayout notesListView = (LinearLayout)getActivity().findViewById(R.id.notesListId);

    	//STATIC data
    	notesTitleArray=new ArrayList<String>();
		notesTitleArray.add("note 1");
		notesTitleArray.add("find your pippo friends");
		notesTitleArray.add("check my party note");
		notesTitleArray.add("hey_ure_fkin_my_shitty_dog_are_u_sure_u_want_to_cose_ure_crazy");
		String noteContentList="bla bla bla - this is the content";
    	
    	ArrayList<Note> notesDataList=new ArrayList<Note>();
    	for(int i=0;i<notesTitleArray.size();i++){
    		Note noteObj = new Note(i,android.R.drawable.ic_menu_agenda, notesTitleArray.get(i), noteContentList);
    		notesDataList.add(noteObj);
        	final boolean notePreviewIsVisible=noteObj.isNotePreviewVisible();
        	final int noteId=noteObj.getNoteId();
			
    		View view =getActivity().getLayoutInflater().inflate(R.layout.link_row,null);
    		notesListView.addView(view);
    		
    		ImageView noteIcon = (ImageView)view.findViewById(R.id.link_icon_id);
    		noteIcon.getResources().getDrawable(noteObj.getNoteIconPath());
        	TextView noteTitle = (TextView)view.findViewById(R.id.link_title_id);
        	noteTitle.setText(noteObj.getNoteName());

    		View view2 =getActivity().getLayoutInflater().inflate(R.layout.note_preview_row,null);
    		notesListView.addView(view2);

        	TextView noteContent = (TextView)view2.findViewById(R.id.note_preview_text_id);
        	noteContent.setText(noteObj.getNoteContent());
        	if(!notePreviewIsVisible)
        		view2.setVisibility(View.INVISIBLE);

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
			view.findViewById(R.id.link_preview_layout_id).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					toastMessageWrapper("get links preview");
					toggleNotePreview(noteId,notePreviewIsVisible);
				}
			});
    	}
    	for (Note note:notesDataList)
    		Log.d(TAG, note.getNoteName());
    	//set noteList to sharedData fx
    	SharedData.setNotesListStatic(notesDataList);
    }
 
    
    public void toggleNotePreview(int noteId,boolean notePrevIsVisible){
    	//if true show notePreview else hide it
    	LinearLayout notePreviewLayout = (LinearLayout)getActivity().findViewById(R.id.note_preview_layout_id);
    	Note noteObj=SharedData.getNoteStaticById(noteId);
    	if(noteObj!=null){
	    	if(!notePrevIsVisible){
	    		noteObj.setNotePreviewVisible(true);
	    		notePreviewLayout.setVisibility(View.INVISIBLE);
	    	}else{
	    		noteObj.setNotePreviewVisible(false);
		    	notePreviewLayout.setVisibility(View.VISIBLE);
	    	}
    	}	
	    
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
}
