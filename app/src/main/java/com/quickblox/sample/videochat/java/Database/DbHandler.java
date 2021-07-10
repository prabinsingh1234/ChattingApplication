package com.quickblox.sample.videochat.java.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_TIME = "time";
    private static final String KEY_IMAGE = "image_data";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"  + KEY_IMAGE  + " BLOB,"
                + KEY_TIME + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    public void insertUserDetails(String name, String ph, String time, byte[] image){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_PHONE, ph);
        cValues.put(KEY_TIME, time);
        cValues.put(KEY_IMAGE,   image);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, time, phone FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("time",cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            user.put("phone",cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            userList.add(user);
        }
        return  userList;
    }
    public byte[] getImage(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

//Cursor cursorr = db.rawQuery(Query, null);
        byte[] image = new byte[1024];

        Cursor cursorr =  db.rawQuery("select * from " + TABLE_Users + " where " + KEY_PHONE + "='" + id + "'" , null);

        if (cursorr.moveToFirst())
        {

            do {

                // your code like get columns
                image = cursorr.getBlob(cursorr.getColumnIndex(KEY_IMAGE));

            }
            while (cursorr.moveToNext());
        }
        return image;
    }

    public String getPh(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorr =  db.rawQuery("select * from " + TABLE_Users + " where " + KEY_NAME + "='" + name + "'" , null);

        if (cursorr.moveToFirst())
        {

            do {

                // your code like get columns
              return cursorr.getString(cursorr.getColumnIndex(KEY_PHONE));

            }
            while (cursorr.moveToNext());
        }
        return "";
    }

    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, time, phone FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_PHONE, KEY_TIME}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("time",cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            user.put("phone",cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            userList.add(user);
        }
        return  userList;
    }
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
    public int UpdateUserDetails(String location, String designation, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_PHONE, location);
        cVals.put(KEY_TIME, designation);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}