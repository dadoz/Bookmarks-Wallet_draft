package com.app.example.db.lib;

import java.util.ArrayList;

import com.app.example.bookmarksWallet.models.ActionLog;
import com.app.example.bookmarksWallet.models.Link;
import com.app.example.common.lib.SharedData;

public class DatabaseSync {

	public static boolean syncLocalDb(DatabaseAdapter linskDb,ActionLogDbAdapter actionLogDb){

		ArrayList<ActionLog> actionLogList = DatabaseConnectionCommon.getActionLogWrappLocalDb(actionLogDb);		
		
		if(actionLogList!=null){
			for(ActionLog actionLogObj:actionLogList)
				if(actionLogObj.getActionLogModel().equals(SharedData.LINK_LABEL)){
					if(actionLogObj.getActionLogAction().equals(SharedData.EDIT_LABEL)){
					//update on online db
					}
					if(actionLogObj.getActionLogAction().equals(SharedData.ADD_LABEL)){
						Link linkObj=DatabaseConnectionCommon.getLinkByIdWrappLocalDb(linskDb,actionLogObj.getActionLogModelId());
						DatabaseConnectionCommon.insertUrlEntryOnDb(SharedData.LINKS_DB, linkObj.linkUrl);
					}
					if(actionLogObj.getActionLogAction().equals(SharedData.DELETE_LABEL)){
						DatabaseConnectionCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB, actionLogObj.getActionLogModelId());
					}
				}
		}
		
		
//   		String actionLogDbStored=SharedData.getActionLogDbStored(sharedPref);
//		String modelLogDbStored = SharedData.getModelLogDbStored(sharedPref);
//		int idLogDbStored=SharedData.getIdLogDbStored(sharedPref);
//
//		if(modelLogDbStored.equals(SharedData.LINK_LABEL)){
//			if(actionLogDbStored.equals(SharedData.EDIT_LABEL)){
//				//update on online db
//			}
//			if(actionLogDbStored.equals(SharedData.ADD_LABEL)){
//				Link linkObj=DatabaseConnectionCommon.getLinkByIdWrappLocalDb(db,idLogDbStored);
//				DatabaseConnectionCommon.insertUrlEntryOnDb(SharedData.LINKS_DB, linkObj.linkUrl);
//			}
//			if(actionLogDbStored.equals(SharedData.DELETE_LABEL)){
//				DatabaseConnectionCommon.deleteUrlEntryFromDb(SharedData.LINKS_DB, idLogDbStored);
//			}
//			
//		}
//		if(modelLogDbStored.equals(SharedData.NOTE_LABEL)){
//			//TODO to be implemented
//		}
		
		return true;
	}
	
}
