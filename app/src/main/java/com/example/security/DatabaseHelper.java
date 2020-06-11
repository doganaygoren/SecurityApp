package com.example.security;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="eventList.db";
    public static final String TABLE_NAME="events";
    public static final String COL1="id";
    public static final String COL2="type";
    public static final String COL3="description";
    public static final String COL4="created_at";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable="CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + " TYPE TEXT, DESCRIPTION TEXT, CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP)";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public boolean addEvent(String type, String description){

        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,type);
        contentValues.put(COL3,description);
        long result=database.insert(TABLE_NAME,null,contentValues);
        if( result == -1 )
            return false;
        else
            return true;
    }

    //Will be used for Settings
    public ArrayList<String> getAllEvents() {
        SQLiteDatabase database=this.getReadableDatabase();
        ArrayList<String> eventList=new ArrayList<>();
        String query="SELECT TYPE, DESCRIPTION FROM "+TABLE_NAME;
        Cursor cursor=database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                eventList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return eventList;
    }

    public Cursor getEvents(){

        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
    }

    public void deleteEvent(int item_id){

        SQLiteDatabase database=this.getWritableDatabase();
        String sql= "DELETE FROM events WHERE id=" +item_id;
        SQLiteStatement statement=database.compileStatement(sql);
        statement.execute();
        database.close();
    }
    /*
    public void deleteEvent(String item_id){

        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(TABLE_NAME, COL1+" = ?",new String[]{item_id});
        database.close();
    }
    */
    public void deleteAllEvents(){

        SQLiteDatabase database=this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_NAME);
    }
}
