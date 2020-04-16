package com.example.map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager{
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void add(List<UsersInfo> usersInfos){
        db.beginTransaction();
        for (UsersInfo usersInfo: usersInfos){
            db.execSQL("insert into usertable values(?,?)", new Object[]{
                    usersInfo.name,usersInfo.password
            });
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void add(String username, String userpassword){
        db.beginTransaction();
        db.execSQL("insert into usertable values(?,?)", new Object[]{
                username,userpassword
        });
    }

    public void update(String userpassword){
        db.execSQL("update usertable set userpassword =?", new Object[]{
                userpassword
        });
    }

    public void delete(String username, String userpassword){
        db.execSQL("delete from usertable where username = and userpassword ="+username+userpassword);
    }

    public List<UsersInfo> query(){
        ArrayList<UsersInfo> usersInfos = new ArrayList<>();
        Cursor c = queryTheCursor();
        while(c.moveToNext()){
            UsersInfo usersInfo = new UsersInfo();
            usersInfo.name = c.getString(c.getColumnIndex("name"));
            usersInfo.password = c.getString(c.getColumnIndex("password"));
            usersInfos.add(usersInfo);
        }
        c.close();
        return usersInfos;
    }

    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM usertable",null);
        return c;
    }

    public void closeDB(){
        db.close();
    }

}
