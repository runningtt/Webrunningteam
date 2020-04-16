package com.example.map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoSQLHelper extends SQLiteOpenHelper {
    public MemoSQLHelper(Context context) {
        super(context, MemoContract.DATABASE_NAME, null, MemoContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MemoContract.MemoTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
        db.execSQL(MemoContract.MemoTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
