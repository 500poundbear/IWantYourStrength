package com.example.dereknam.strengthapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek Nam on 04-07-2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_GROUPS = "groups";
    public static final String GROUPS_COLUMN_ID = "_id";
    public static final String GROUPS_COLUMN_NAME = "name";
    public static final String GROUPS_COLUMN_DESCRIPTION = "description";

    ///////////////////

    public static final String TABLE_INDIVIDUALS = "individuals";
    public static final String INDIVIDUALS_COLUMN_ID = "_id";
    public static final String INDIVIDUALS_COLUMN_NAME = "name";
    public static final String INDIVIDUALS_COLUMN_SHORT_FORM = "short_form";
    public static final String INDIVIDUALS_COLUMN_DESCRIPTION = "individuals";
    public static final String INDIVIDUALS_COLUMN_GROUP_ID = "group_id";

    ///////////////////

    public static final String TABLE_STATUS_LABELS = "status_labels";
    public static final String STATUS_LABELS_COLUMN_ID = "_id";
    public static final String STATUS_LABELS_COLUMN_LABEL = "label";

    ///////////////////

    public static final String TABLE_STATUSES = "statuses";
    public static final String STATUSES_COLUMN_ID = "_id";
    public static final String STATUSES_COLUMN_INDIVIDUAL_ID = "individual_id";
    public static final String STATUSES_COLUMN_ACCOUNTED = "accounted";
    public static final String STATUSES_COLUMN_STATUS_LABEL_ID = "status_label_id";
    public static final String STATUSES_COLUMN_TIMESTAMP = "timestamp";

    ///////////////////

    public static final String TABLE_STRENGTH = "strength";
    public static final String STRENGTH_COLUMN_ID = "_id";
    public static final String STRENGTH_COLUMN_GROUP_ID = "group_id";
    public static final String STRENGTH_COLUMN_CURRENT = "current";
    public static final String STRENGTH_COLUMN_TOTAL = "total";
    public static final String STRENGTH_COLUMN_TIMESTAMP = "timestamp";


    public static final String DATABASE_NAME = "strength_app.db";
    private static final int DATABASE_VERSION = 6;

    private static final String TABLE_GROUPS_CREATE = "create table "+
            TABLE_GROUPS + "(" + GROUPS_COLUMN_ID + " integer primary key autoincrement,"+
            GROUPS_COLUMN_NAME + " text not null,"+
            GROUPS_COLUMN_DESCRIPTION + " text not null);";

    private static final String TABLE_INDIVIDUALS_CREATE = "create table "+
            TABLE_INDIVIDUALS + "(" + INDIVIDUALS_COLUMN_ID + " integer primary key autoincrement,"+
            INDIVIDUALS_COLUMN_NAME + " text not null,"+
            INDIVIDUALS_COLUMN_DESCRIPTION + " text not null,"+
            INDIVIDUALS_COLUMN_SHORT_FORM + " text not null,"+
            INDIVIDUALS_COLUMN_GROUP_ID+ " integer,"+
            " FOREIGN KEY ("+INDIVIDUALS_COLUMN_GROUP_ID+") REFERENCES "+
            TABLE_GROUPS+" (" + GROUPS_COLUMN_ID + "));";

    private static final String TABLE_STATUS_LABELS_CREATE = "create table "+
            TABLE_STATUS_LABELS + "(" + STATUS_LABELS_COLUMN_ID+" integer primary key autoincrement,"+
            STATUS_LABELS_COLUMN_LABEL+" text not null);";

    private static final String TABLE_STATUSES_CREATE = "create table "+
            TABLE_STATUSES + "(" + STATUSES_COLUMN_ID + " integer primary key autoincrement,"+
            STATUSES_COLUMN_INDIVIDUAL_ID + " integer, "+
            STATUSES_COLUMN_STATUS_LABEL_ID + " integer, "+
            STATUSES_COLUMN_ACCOUNTED + " integer default 1, "+
            STATUSES_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+

            " FOREIGN KEY ("+STATUSES_COLUMN_INDIVIDUAL_ID+") REFERENCES "+
            TABLE_INDIVIDUALS+" (" + INDIVIDUALS_COLUMN_ID + "),"+

            " FOREIGN KEY ("+STATUSES_COLUMN_STATUS_LABEL_ID+") REFERENCES "+
            TABLE_STATUS_LABELS+" (" + STATUS_LABELS_COLUMN_ID + ") );";

    private static final String TABLE_STRENGTH_CREATE = "create table "+
            TABLE_STRENGTH + "(" + STRENGTH_COLUMN_ID + " integer primary key autoincrement,"+
            STRENGTH_COLUMN_GROUP_ID + " integer, "+
            STRENGTH_COLUMN_CURRENT + " integer, "+
            STRENGTH_COLUMN_TOTAL + " integer, "+
            STRENGTH_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+

            " FOREIGN KEY ("+STRENGTH_COLUMN_GROUP_ID+") REFERENCES "+
            TABLE_GROUPS+" (" + GROUPS_COLUMN_ID + ") );";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase database){
        database.execSQL(TABLE_GROUPS_CREATE);
        database.execSQL(TABLE_INDIVIDUALS_CREATE);
        database.execSQL(TABLE_STATUS_LABELS_CREATE);
        database.execSQL(TABLE_STATUSES_CREATE);
        database.execSQL(TABLE_STRENGTH_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version "+ oldVersion +" to "
        + newVersion +" which will destroy all old data.");

        List<String> tables = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence"))
                tables.add(tableName);
            cursor.moveToNext();
        }
        cursor.close();

        for(String tableName:tables) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }

        onCreate(db);
    }
}
