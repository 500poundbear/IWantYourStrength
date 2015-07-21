package com.example.dereknam.strengthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Derek Nam on 21-07-2015.
 */
public class StrengthDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {
            MySQLiteHelper.STRENGTH_COLUMN_ID,
            MySQLiteHelper.STRENGTH_COLUMN_GROUP_ID,
            MySQLiteHelper.STRENGTH_COLUMN_CURRENT,
            MySQLiteHelper.STRENGTH_COLUMN_TOTAL,
            MySQLiteHelper.STRENGTH_COLUMN_TIMESTAMP
    };
    public StrengthDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }

    public Strength getLatestStrength(long group_id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STRENGTH, allColumns,
                MySQLiteHelper.STRENGTH_COLUMN_GROUP_ID+" = "+ String.valueOf(group_id),
                null,null,null,MySQLiteHelper.STRENGTH_COLUMN_ID+" DESC","1");

        Strength latestStrength = new Strength();

        if(cursor.getCount()==0){
            latestStrength.setId(-1);
        }else {
            cursor.moveToFirst();
            latestStrength = cursorToStrength(cursor);
        }
        cursor.close();
        return latestStrength;
    }
    public Strength addNewStrength(long group_id, int current, int total){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.STRENGTH_COLUMN_GROUP_ID,group_id);
        values.put(MySQLiteHelper.STRENGTH_COLUMN_CURRENT,current);
        values.put(MySQLiteHelper.STRENGTH_COLUMN_TOTAL,total);

        long insertId = database.insert(MySQLiteHelper.TABLE_STRENGTH, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STRENGTH, allColumns,
                MySQLiteHelper.STRENGTH_COLUMN_ID + " = " + insertId, null, null, null, null, null);

        cursor.moveToFirst();

        Strength newStrength = cursorToStrength(cursor);
        cursor.close();
        return newStrength;
    }
    private Strength cursorToStrength(Cursor cursor){
        Strength strength = new Strength();
        strength.setId(cursor.getLong(0));
        strength.setGroup_id(cursor.getLong(1));
        strength.setCurrent(cursor.getInt(2));
        strength.setTotal(cursor.getInt(3));
        strength.setTimestamp(cursor.getString(4));

        return strength;
    }
}
