package com.app.example.bookmarksWallet.models;

public class Note {
	
	public int noteId;
	public int noteIconPath;
	public String noteName;	
	public String noteContent;	
	public boolean deletedNote;
	public boolean notePreviewIsVisible;

	public Note(int noteId,int noteIconPath,String noteName,String noteContent){
		this.noteId=noteId;
		this.noteIconPath=noteIconPath;
		this.noteName=noteName;
		this.noteContent=noteContent;
		//set initial flag
		this.deletedNote=false;
		this.notePreviewIsVisible=false;
		
	}
	public int getNoteId(){
		return this.noteIconPath;
	}

	public int getNoteIconPath(){
		return this.noteIconPath;
	}

	public String getNoteContent(){
		return this.noteContent;
	}
	
	public String getNoteName(){
		return this.noteName;
	}

	public void setNoteName(String value){
		this.noteName=value;
	}

	public void setNoteContent(String noteContent){
		this.noteContent=noteContent;
	}
	
	public void setDeletedNote(boolean value){
		this.deletedNote=value;
	}
	public boolean isDeletedNote(){
		return this.deletedNote;
	}
	public void setNotePreviewVisible(boolean value){
		this.notePreviewIsVisible=value;
	}
	public boolean isNotePreviewVisible(){
		return this.notePreviewIsVisible;
	}
}
