package com.example.dereknam.strengthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek Nam on 18-07-2015.
 */
public class LabelsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {
            MySQLiteHelper.STATUS_LABELS_COLUMN_ID,
            MySQLiteHelper.STATUS_LABELS_COLUMN_LABEL
    };

    public LabelsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public Label createLabel(String label){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.STATUS_LABELS_COLUMN_LABEL, label);

        long insertId = database.insert(MySQLiteHelper.TABLE_STATUS_LABELS, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUS_LABELS, allColumns,
                MySQLiteHelper.STATUS_LABELS_COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();

        Label newLabel = cursorToLabel(cursor);
        cursor.close();
        return newLabel;
    }
    public Label getLabel(long status_label_id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUS_LABELS, allColumns,
                MySQLiteHelper.STATUS_LABELS_COLUMN_ID + " = " + String.valueOf(status_label_id),
                null, null, null, null);

        cursor.moveToFirst();
        Label label;
            label = cursorToLabel(cursor);
        return label;
    }
    public long getIdOfLabel(String label){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUS_LABELS, allColumns,
                MySQLiteHelper.STATUS_LABELS_COLUMN_LABEL+" = '"+label+"'",
                null,null,null,"1");

        cursor.moveToFirst();
        Label found;
        if(cursor!=null && cursor.getCount()>0) {
            found = cursorToLabel(cursor);
        }else{
            Log.d("DOOO","DIE");
            found = new Label();
        }

        return found.getId();
    }
    public ArrayList<Label> getAllLabels(){
        ArrayList<Label> labels = new ArrayList<Label>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STATUS_LABELS, allColumns, null, null,
                null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Label label = cursorToLabel(cursor);
            labels.add(label);
            cursor.moveToNext();
        }

        cursor.close();

        return labels;
    }


    private Label cursorToLabel(Cursor cursor){
        Label label = new Label();
        label.setId(cursor.getLong(0));
        label.setLabel(cursor.getString(1));
        return label;
    }
}
