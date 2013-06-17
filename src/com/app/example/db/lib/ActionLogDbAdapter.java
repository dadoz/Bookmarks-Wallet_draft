package com.app.example.db.lib;

//import android.os.Bundle;
//import android.app.Activity;
//import android.view.Menu;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class ActionLogDbAdapter{
	public static final String DATABASE_NAME="BookmarksWalletDb_tmpLog";
	public static final int DATABASE_VERSION=1;

	public static final String TAG="DatabaseAdapter_TAG";

	public static final String LOG_TABLE_NAME="LogTable_tmp";

	public static final String ROWID_KEY="_id";
	public static final String LOG_ACTION_KEY="action";
	public static final String LOG_MODEL_KEY="model";
	public static final String LOG_MODEL_ID_KEY="modelId";

	public static final String LOG_DB_CREATE=
			"create table "+LOG_TABLE_NAME+"(_id integer primary key autoincrement,"
			+LOG_ACTION_KEY+ " text not null," 
			+LOG_MODEL_KEY+" text not null," 
			+LOG_MODEL_ID_KEY+" integer);";

	private final Context context;
	
	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;
	
	public ActionLogDbAdapter(Context ctx){
		this.context=ctx;
		DBHelper=new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context,DATABASE_NAME,null,DATABASE_VERSION);			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(LOG_DB_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG,"Upgrading database from version" + oldVersion
					+ " to " 
					+ newVersion + ", wich will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
			onCreate(db);
		}
	}
	
	//open the database
	public ActionLogDbAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
		
	}
	
	//close the database
	public void close(){
		DBHelper.close();
	}


	public void dropDbTable(){
		try {
			  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			  db.execSQL("VACUUM");
		}catch (Exception e){
			Log.d(TAG, "Failed to do : " + e.getMessage());
		}	
	}   
	
	//insert a title into the database --- row of a db
	public long insertActionLog(String action, String model,
			int modelId){
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(LOG_ACTION_KEY, action);
		initialValues.put(LOG_MODEL_KEY, model);
		initialValues.put(LOG_MODEL_ID_KEY, modelId);

		return db.insert(LOG_TABLE_NAME,null, initialValues);

	}
	//delete all rows
	public void deleteActionLogs(){
		//delete all db table
		db.delete(LOG_TABLE_NAME, null, null);
	}
	
	//delete row by rowId
	public void deleteActionLogById(int dbRowId){
		//TODO check if dbRowId is int
		db.delete(LOG_TABLE_NAME, ROWID_KEY + "=" + dbRowId, null);
	}

	//retrieve all titles
    public Cursor getActionLogs(){
        return db.query(LOG_TABLE_NAME, new String[] {
        		ROWID_KEY,
        		LOG_ACTION_KEY,
        		LOG_MODEL_KEY,
        		LOG_MODEL_ID_KEY},
        		null, 
                null, 
                null, 
                null, 
                null);
    }
    
    
    
}
