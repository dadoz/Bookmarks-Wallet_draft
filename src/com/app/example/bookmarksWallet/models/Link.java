package com.app.example.bookmarksWallet.models;

import android.util.Log;

public class Link {
	
	public int linkId;
	public String linkIconPath;
	public String linkUrl;
	public int userId;
	public String linkName;	
	public String delIcon;
	public boolean deletedLinkFlag;

	private static final int EMPTY_LINKID=-1;

	public Link(int linkId,String linkIconPath,String linkName,String linkUrl,int userId,String delIcon,boolean deletedLinkFlag){
		this.linkId=linkId;
		this.linkIconPath=linkIconPath;
		this.linkName=linkName;
		this.linkUrl=linkUrl;
		this.userId=userId;
		this.delIcon=delIcon;
		this.deletedLinkFlag=deletedLinkFlag;
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
	public boolean findLinkNameBool(String value){
		if(this.linkName==value)
			return true;

		return false;
	}
	
	public int getLinkIdFromLinkName(String value){
		Log.v("linkID_TAG",""+this.linkName+" "+value);
//		Log.v("getLinkId_TAG",""+this.linkId);
		//compare name of link to the value
		if((this.linkName).compareTo(value)==0)
			return this.linkId;

		return EMPTY_LINKID;
	}

	public String getLinkByString(){
		return ""+this.linkId+" - "+this.linkIconPath+" - "+this.linkName+
				" - "+this.linkUrl+" - "+this.userId+" - "+this.delIcon+
				" - "+this.linkName+" - "+this.userId+" - "+this.deletedLinkFlag;
	}
}
