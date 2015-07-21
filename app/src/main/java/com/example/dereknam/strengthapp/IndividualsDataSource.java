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
 * Created by Derek Nam on 08-07-2015.
 */
public class IndividualsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {
            MySQLiteHelper.INDIVIDUALS_COLUMN_ID,
            MySQLiteHelper.INDIVIDUALS_COLUMN_NAME,
            MySQLiteHelper.INDIVIDUALS_COLUMN_DESCRIPTION,
            MySQLiteHelper.INDIVIDUALS_COLUMN_SHORT_FORM,
            MySQLiteHelper.INDIVIDUALS_COLUMN_GROUP_ID
    };

    public IndividualsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Individual createIndividual(String name, String description, String short_form,
                                       Long group_id){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.INDIVIDUALS_COLUMN_NAME,name);
        values.put(MySQLiteHelper.INDIVIDUALS_COLUMN_DESCRIPTION,description);
        values.put(MySQLiteHelper.INDIVIDUALS_COLUMN_SHORT_FORM,short_form);
        values.put(MySQLiteHelper.INDIVIDUALS_COLUMN_GROUP_ID,group_id);

        long insertId = database.insert(MySQLiteHelper.TABLE_INDIVIDUALS, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INDIVIDUALS, allColumns,
                MySQLiteHelper.INDIVIDUALS_COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();

        Individual newIndividual = cursorToIndividual(cursor);
        cursor.close();
        return newIndividual;
    }
    public Individual getIndividual(long id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_INDIVIDUALS, allColumns,
                MySQLiteHelper.INDIVIDUALS_COLUMN_ID + " = " + String.valueOf(id), null, null, null, null);

        cursor.moveToFirst();

        Individual newIndividual = cursorToIndividual(cursor);
        cursor.close();
        return newIndividual;
    }
    public ArrayList<Individual> getIndividualsByGroupId(long id){
        ArrayList<Individual> individuals = new ArrayList<Individual>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INDIVIDUALS,allColumns,
                MySQLiteHelper.INDIVIDUALS_COLUMN_GROUP_ID+"="+String.valueOf(id),null,null,
                null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Individual individual = cursorToIndividual(cursor);
            individuals.add(individual);
            cursor.moveToNext();
            Log.d("FAT", individual.getName());
        }
        return individuals;
    }
    public ArrayList<Individual> getAllIndividuals(){
        ArrayList<Individual> individuals = new ArrayList<Individual>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INDIVIDUALS,allColumns,null,null,null,
                null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Individual individual = cursorToIndividual(cursor);
            individuals.add(individual);
            cursor.moveToNext();
        }

        cursor.close();

        return individuals;
    }
    public void updateIndividual(long id,String name, String description, String short_form, long group_id){

        ContentValues updatedRow = new ContentValues();
        updatedRow.put(MySQLiteHelper.INDIVIDUALS_COLUMN_NAME,name);
        updatedRow.put(MySQLiteHelper.INDIVIDUALS_COLUMN_DESCRIPTION,description);
        updatedRow.put(MySQLiteHelper.INDIVIDUALS_COLUMN_SHORT_FORM,short_form);
        updatedRow.put(MySQLiteHelper.INDIVIDUALS_COLUMN_GROUP_ID, group_id);

        database.update(MySQLiteHelper.TABLE_INDIVIDUALS, updatedRow,
                MySQLiteHelper.INDIVIDUALS_COLUMN_ID + "=" + String.valueOf(id), null);


    }
    public void deleteIndividual(long id){
        database.delete(MySQLiteHelper.TABLE_INDIVIDUALS,
                MySQLiteHelper.INDIVIDUALS_COLUMN_ID + "=" + Long.toString(id),
                null);

    }
    private Individual cursorToIndividual(Cursor cursor){
        Individual individual = new Individual();
        individual.setId(cursor.getLong(0));
        individual.setName(cursor.getString(1));
        individual.setDescription(cursor.getString(2));
        individual.setShort_form(cursor.getString(3));
        individual.setGroup_id(cursor.getLong(4));

        return individual;
    }
}
