package com.example.dereknam.strengthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Derek Nam on 04-07-2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_GROUPS = "groups";
    public static final String GROUPS_COLUMN_ID = "_id";
    public static final String GROUPS_COLUMN_NAME = "name";
    public static final String GROUPS_COLUMN_DESCRIPTION = "description";

    public static final String DATABASE_NAME = "strength_app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GROUPS_CREATE = "create table "+
            TABLE_GROUPS + "(" + GROUPS_COLUMN_ID + " integer primary key autoincrement,"+
            GROUPS_COLUMN_NAME + " text not null,"+
            GROUPS_COLUMN_DESCRIPTION + " text not null);";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase database){
        database.execSQL(TABLE_GROUPS_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version "+ oldVersion +" to "
        + newVersion +" which will destroy all old data.");

        onCreate(db);
    }
}
