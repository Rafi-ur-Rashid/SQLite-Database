package com.example.rf.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;
import android.widget.Toast;

import java.io.Serializable;

public class dbClass extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="MyWeatherDb_test";
    private static final int DATABASE_VERSION=2;
    Context context;
    private static final String TABLE_NAME = "weather_data_1";
    private static final String UID="_id";
    private static final String NAME="Name";
    private static final String PASSWORD="PassWord";
    //private static final String CREATE_TABLE_QUERY="CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
    private static final String CREATE_TABLE_QUERY2="CREATE TABLE " + TABLE_NAME + "("
            + UID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
            + PASSWORD + " TEXT" + ")";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS " + TABLE_NAME;
    dbClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

        Toast.makeText(context,"DATABASE constructor called",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_QUERY2);
        }catch (SQLException e){
            e.printStackTrace();
        }

        Toast.makeText(context,"DATABASE onCreate called",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Toast.makeText(context,"DATABASE onUPGRADE called",Toast.LENGTH_LONG).show();
    }
    public void insertUser(String name,String password,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(PASSWORD,password);
         long id=db.insert(TABLE_NAME,null,contentValues);
        Toast.makeText(context,"Inserted "+id+" th Name: "+name+" & password: "+password,Toast.LENGTH_LONG).show();
    }
    public String selectAllData(SQLiteDatabase db){
        String[]  columns={UID,NAME,PASSWORD};
        Cursor cursor=db.query(TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(UID));
            String name=cursor.getString(cursor.getColumnIndex(NAME));
            String pass=cursor.getString(cursor.getColumnIndex(PASSWORD));
            stringBuffer.append(id+" "+name+" "+pass+"\n");
        }
        return stringBuffer.toString();

    }
    public String selectData(SQLiteDatabase db,int id){
        String[]  columns={NAME};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,UID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            //int id=cursor.getInt(cursor.getColumnIndex(UID));
//            String name=cursor.getString(cursor.getColumnIndex(NAME));
            String name=""+cursor.getString(cursor.getColumnIndex(NAME));
            stringBuffer.append(name);
        }
        return stringBuffer.toString();
    }
    public int updateData(SQLiteDatabase db,int Name,String newPass ){
        String oldPass=selectData(db,Name);
        ContentValues value=new ContentValues();
        value.put(PASSWORD,newPass);
        String[] whereArgs={oldPass};
        int count=db.update(TABLE_NAME,value,PASSWORD+" =?",whereArgs);
        return count;
    }
    public int deleteData(SQLiteDatabase db,String Name){
        String[] whereArgs={Name};
        int count=db.delete(TABLE_NAME,NAME+" =?",whereArgs);
        return count;
    }

}
