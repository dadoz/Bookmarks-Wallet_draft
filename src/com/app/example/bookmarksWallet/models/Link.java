package com.app.example.bookmarksWallet.models;

import com.app.example.common.lib.SharedData;

import android.util.Log;

public class Link {
	
	private int linkId;
	private String linkIconPath;
	private String linkUrl;
	private int userId;
	private String linkName;	
	private String delIcon;
	private boolean linkDeletedStatus;
	private int linkPosition;
	
	public Link(int linkId,String linkIconPath,String linkName,String linkUrl,int userId,String delIcon,boolean linkDeletedStatus){
		//TODO add linkOrderInList into db
//		this.linkOrderInList=linkOrderInList;
		this.linkId=linkId;
		this.linkIconPath=linkIconPath;
		this.linkName=linkName;
		this.linkUrl=linkUrl;
		this.userId=userId;
		this.delIcon=delIcon;
		this.linkDeletedStatus=linkDeletedStatus;
		this.linkPosition=SharedData.LINK_NOT_IN_LIST;
	}
	
	public int getUserId(){
		return this.userId;
	}
	
	public String getIconPath(){
		return this.linkIconPath;
	}

	public String getLinkUrl(){
		return this.linkUrl;
	}
	
	public String getLinkName(){
		return this.linkName;
	}
	
	public void setLinkUrl(String value){
		this.linkUrl=value;
	}

	public void setLinkName(String value){
		this.linkName=value;
	}
	
	public void setLinkDeletedStatus(boolean value){
		this.linkDeletedStatus=value;
	}
	public boolean getLinkDeletedStatus(){
		return this.linkDeletedStatus;
	}
	public void setLinkPosition(int value){
		this.linkPosition=value;
	}
	public int getLinkPosition(){
		return this.linkPosition;
	}

	public boolean findLinkNameBool(String value){
		if(this.linkName==value)
			return true;

		return false;
	}
	
	public int getLinkIdFromLinkName(String value){
		Log.v("linkID_TAG",""+this.linkName+" "+value);
		if((this.linkName).compareTo(value)==0)
			return this.linkId;
		return SharedData.EMPTY_LINKID;
	}

	public String getLinkByString(){
		return ""+this.linkId+" - "+this.linkIconPath+" - "+this.linkName+
				" - "+this.linkUrl+" - "+this.userId+" - "+this.delIcon+
				" - "+this.linkName+" - "+this.userId+" - "+this.linkDeletedStatus;
	}

	public int getLinkId() {
		// TODO Auto-generated method stub
		return this.linkId;
	}

	public int getLinkOrderInList() {
		// TODO Auto-generated method stub
		return SharedData.EMPTY_LINKID;
	}

}
