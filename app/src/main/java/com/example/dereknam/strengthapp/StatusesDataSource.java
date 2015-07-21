package com.example.dereknam.strengthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Derek Nam on 18-07-2015.
 */
public class StatusesDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {
            MySQLiteHelper.STATUSES_COLUMN_ID,
            MySQLiteHelper.STATUSES_COLUMN_INDIVIDUAL_ID,
            MySQLiteHelper.STATUSES_COLUMN_STATUS_LABEL_ID,
            MySQLiteHelper.STATUSES_COLUMN_ACCOUNTED,
            MySQLiteHelper.STATUSES_COLUMN_TIMESTAMP
    };

    public StatusesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Status getLatestStatus(long individual_id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUSES, allColumns,
                MySQLiteHelper.STATUSES_COLUMN_INDIVIDUAL_ID+" = "+ String.valueOf(individual_id),
                null,null,null,MySQLiteHelper.STATUSES_COLUMN_ID+" DESC","1");
        Status latestStatus = new Status();
        if(cursor.getCount()==0){
            //INSERT NEW STATUS HERE FOR FREE
            //*DS*DFS*DF*SDF IMPORTANT ***
            /// FOR NOW JUST POPULATE RANDOMLY

            latestStatus = addNewStatus(individual_id,1,true);
        }else {
            cursor.moveToFirst();

            latestStatus = cursorToStatus(cursor);
        }
        cursor.close();
        return latestStatus;
    }

    public Status addNewStatus(long individual_id, long status_label_id,
                             boolean available){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.STATUSES_COLUMN_INDIVIDUAL_ID,individual_id);
        values.put(MySQLiteHelper.STATUSES_COLUMN_STATUS_LABEL_ID,status_label_id);
        values.put(MySQLiteHelper.STATUSES_COLUMN_ACCOUNTED, available);

        long insertId = database.insert(MySQLiteHelper.TABLE_STATUSES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUSES, allColumns,
                MySQLiteHelper.STATUSES_COLUMN_ID + " = " + insertId, null, null, null, null, null);

        cursor.moveToFirst();

        Status newStatus = cursorToStatus(cursor);
        cursor.close();
        return newStatus;
    }

    private Status cursorToStatus(Cursor cursor){
        Status status = new Status();
        status.setId(cursor.getLong(0));
        status.setIndividual_id(cursor.getLong(1));
        status.setStatus_label_id(cursor.getLong(2));
        if(cursor.getLong(3)==0){
            status.setAccounted(false);
        }else{
            status.setAccounted(true);
        }
        status.setTimestamp(cursor.getString(4));
        return status;
    }

}
