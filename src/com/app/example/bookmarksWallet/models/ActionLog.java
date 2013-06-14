package com.app.example.bookmarksWallet.models;

public class ActionLog {
	
	public int actionLogId;
	public String actionLogAction;
	public String actionLogModel;
	public int  actionLogModelId;
	
	public ActionLog(int actionLogId,String actionLogAction, String actionLogModel,
			int actionLogModelId){
		this.actionLogAction=actionLogAction;
		this.actionLogModel=actionLogModel;
		this.actionLogModelId=actionLogModelId;
	}
	
	public int getActionLogId(){
		return this.actionLogId;
	}
	
	public String getActionLogAction(){
		return this.actionLogAction;
	}

	public String getActionLogModel(){
		return this.actionLogModel;
	}
	
	public int getActionLogModelId(){
		return this.actionLogModelId;
	}
	
	public void setActionLogAction(String value){
		this.actionLogAction=value;
	}

	public void setActionLogModel(String value){
		this.actionLogModel=value;
	}
	
	public void setActionLogModelId(int value){
		this.actionLogModelId=value;
	}

}
