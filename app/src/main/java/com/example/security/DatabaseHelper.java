package com.example.security;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="SECURITY.DB";
    public final static String TABLE_NAME="EVENTS";
    public final static String COL_ID="ID";
    public final static String COL_TYPE="TYPE";
    public final static String COL_DESCRIPTION="DESCRIPTION";
    SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE TEXT NOT NULL,DESCRIPTION TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        this.database=sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(query,null);
        this.onCreate(sqLiteDatabase);
    }


    public boolean insertEvent(String type, String description) {

        database=this.getWritableDatabase();
        String query="SELECT * FROM "+ TABLE_NAME;
        Cursor cursor=database.rawQuery(query,null);
        int count=cursor.getCount();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_ID,(count+1));
        contentValues.put(COL_TYPE,type);
        contentValues.put(COL_DESCRIPTION,description);
        long result=database.insert(TABLE_NAME,null,contentValues);
        database.close();
        if(result==-1) //data is not inserted
            return false;
        else //insert
            return true;
    }

    public ArrayList<String> getAllEvents() {
        database=this.getReadableDatabase();
        ArrayList<String> eventList=new ArrayList<>();
        String query="SELECT TYPE, DESCRIPTION FROM "+TABLE_NAME;
        Cursor cursor=database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                eventList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        database.close();
        return eventList;
    }
}
