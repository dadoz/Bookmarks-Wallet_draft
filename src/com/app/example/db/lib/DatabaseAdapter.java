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

public class DatabaseAdapter{
	public static final String TAG="DatabaseAdapter_TAG";
	public static final String ROWID_KEY="_id";
	public static final String LINK_ORDER_IN_LIST_KEY="linkOrderInList";
	public static final String LINK_NAME_KEY="linkName";
	public static final String ICON_PATH_KEY="iconPath";
	public static final String LINK_URL_KEY="linkUrl";
	public static final String LINKS_USER_ID_KEY="linksUserId";
	
	public static final String DATABASE_NAME="BookmarksWalletDb_tmp";
	public static final String LINKS_TABLE_NAME="LinksTable_tmp1";
	public static final int DATABASE_VERSION=1;
	
	public static final String DATABASE_CREATE=
			"create table "+LINKS_TABLE_NAME+"(_id integer primary key autoincrement,"
			+LINK_ORDER_IN_LIST_KEY+ " integer," 
			+LINK_NAME_KEY+" text not null," 
			+ICON_PATH_KEY+" text not null,"		
			+LINK_URL_KEY+" text not null,"
			+LINKS_USER_ID_KEY+" text not null);";
	private final Context context;
	
	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;
	
	public DatabaseAdapter(Context ctx){
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
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG,"Upgrading database from version" + oldVersion
					+ " to " 
					+ newVersion + ", wich will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}
	
	//open the database
	public DatabaseAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
		
	}
	
	//close the database
	public void close(){
		DBHelper.close();
	}

	//insert a title into the database --- row of a db
	public long insertLink(String linkOrderInList, String linkName, String iconPath,
			String linkUrl,String linksUserId){
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(LINK_ORDER_IN_LIST_KEY, linkOrderInList);
		initialValues.put(LINK_NAME_KEY, linkName);
		initialValues.put(ICON_PATH_KEY, iconPath);
		initialValues.put(LINK_URL_KEY, linkUrl);
		initialValues.put(LINKS_USER_ID_KEY, linksUserId);

		return db.insert(LINKS_TABLE_NAME,null, initialValues);

	}
	//delete all rows
	public void deleteLinks(){
		//delete all db table
		db.delete(LINKS_TABLE_NAME, null, null);
	}
	
	//delete row by rowId
	public void deleteLinkById(int dbRowId){
		//TODO check if dbRowId is int
		db.delete(LINKS_TABLE_NAME, ROWID_KEY + "=" + dbRowId, null);
	}


	public void dropDbTable(){
		try {
			  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			  db.execSQL("VACUUM");
		}catch (Exception e){
			Log.d(TAG, "Failed to do : " + e.getMessage());
		}	
	}   
	//retrieve all titles
    public Cursor getLinks(){
        return db.query(LINKS_TABLE_NAME, new String[] {
        		ROWID_KEY,
        		LINK_ORDER_IN_LIST_KEY,
   				LINK_NAME_KEY,
   				ICON_PATH_KEY,		
        		LINK_URL_KEY,
        		LINKS_USER_ID_KEY},
        		null, 
                null, 
                null, 
                null, 
                null);
    }

    //retrieve a particular title
    public Cursor getLinkById(long rowId) throws SQLException{
        Cursor mCursor =
                db.query(true, LINKS_TABLE_NAME, new String[] {
                		LINK_ORDER_IN_LIST_KEY,
           				LINK_NAME_KEY,
           				ICON_PATH_KEY,		
                		LINK_URL_KEY,
                		LINKS_USER_ID_KEY},
                		ROWID_KEY + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //update title
    public boolean updateLink(long rowId,String linkOrderInList, String linkName, String iconPath,
    		String linkUrl,String linksUserId){
    	
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(LINK_ORDER_IN_LIST_KEY, linkOrderInList);
    	initialValues.put(LINK_NAME_KEY, linkName);
    	initialValues.put(ICON_PATH_KEY, iconPath);
    	initialValues.put(LINK_URL_KEY, linkUrl);
    	initialValues.put(LINKS_USER_ID_KEY, linksUserId);

        return db.update(LINKS_TABLE_NAME, initialValues, 
                         ROWID_KEY + "=" + rowId, null) > 0;
    }

	
}