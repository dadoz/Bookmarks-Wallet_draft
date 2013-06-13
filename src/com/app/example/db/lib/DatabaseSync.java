package com.app.example.db.lib;

import android.content.SharedPreferences;

import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;

public class DatabaseSync {

	public static boolean syncLocalDb(DatabaseAdapter db,SharedPreferences sharedPref){

   		String actionLogDbStored=SharedData.getActionLogDbStored(sharedPref);
		String modelLogDbStored = SharedData.getModelLogDbStored(sharedPref);
		int idLogDbStored=SharedData.getIdLogDbStored(sharedPref);

		if(modelLogDbStored.equals("LINK")){
			if(actionLogDbStored.equals("EDIT")){
				//update on online db
			}
			if(actionLogDbStored.equals("ADD")){
				Link linkObj=DatabaseConnectionCommon.getLinkByIdWrappLocalDb(db,idLogDbStored);
				DatabaseConnectionCommon.insertUrlEntryOnDb(SharedData.LINKS_DB, linkObj.linkUrl);
			}
			if(actionLogDbStored.equals("DELETE")){
				DatabaseConnectionCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB, idLogDbStored);
			}
			
		}
		if(modelLogDbStored.equals("NOTE")){
			//TODO to be implemented
		}
		
		return true;
	}
	
}
