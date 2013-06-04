package com.app.example.bookmarksWallet.models;

import com.app.example.common.lib.SharedData;

import android.util.Log;

public class Link {
	
	public int linkId;
	public String linkIconPath;
	public String linkUrl;
	public int userId;
	public String linkName;	
	public String delIcon;
	public boolean deletedLinkFlag;
	public int linkPosition;
	
	public Link(int linkId,String linkIconPath,String linkName,String linkUrl,int userId,String delIcon,boolean deletedLinkFlag){
		//TODO add linkOrderInList into db
//		this.linkOrderInList=linkOrderInList;
		this.linkId=linkId;
		this.linkIconPath=linkIconPath;
		this.linkName=linkName;
		this.linkUrl=linkUrl;
		this.userId=userId;
		this.delIcon=delIcon;
		this.deletedLinkFlag=deletedLinkFlag;
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
	
	public void setDeletedLink(boolean value){
		this.deletedLinkFlag=value;
	}
	public boolean getDeletedLink(){
		return this.deletedLinkFlag;
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
				" - "+this.linkName+" - "+this.userId+" - "+this.deletedLinkFlag;
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
