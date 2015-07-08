package com.example.dereknam.strengthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek Nam on 04-07-2015.
 */
public class GroupsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.GROUPS_COLUMN_ID,
            MySQLiteHelper.GROUPS_COLUMN_NAME,
            MySQLiteHelper.GROUPS_COLUMN_DESCRIPTION
    };

    public GroupsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public Group createGroup(String name, String description){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.GROUPS_COLUMN_NAME,name);
        values.put(MySQLiteHelper.GROUPS_COLUMN_DESCRIPTION,description);

        long insertId = database.insert(MySQLiteHelper.TABLE_GROUPS, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_GROUPS, allColumns,
                MySQLiteHelper.GROUPS_COLUMN_ID+" = "+insertId, null,null,null,null);

        cursor.moveToFirst();

        Group newGroup = cursorToGroup(cursor);
        cursor.close();
        return newGroup;
    }
    public List<Group> getAllGroups(){
        List<Group> groups = new ArrayList<Group>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_GROUPS,allColumns,null,null,null,
                null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Group group = cursorToGroup(cursor);
            groups.add(group);
            cursor.moveToNext();
        }

        cursor.close();

        return groups;
    }
    public Group getGroup(String groupName){


        Cursor cursor = database.query(MySQLiteHelper.TABLE_GROUPS, allColumns,
                MySQLiteHelper.GROUPS_COLUMN_NAME+" = '"+groupName+"'", null, null, null, null);

        cursor.moveToFirst();

        Group queriedGroup = cursorToGroup(cursor);
        cursor.close();
        return queriedGroup;
    }
    public void updateGroup(long id,String name, String description){

        ContentValues updatedRow = new ContentValues();
        updatedRow.put(MySQLiteHelper.GROUPS_COLUMN_NAME,name);
        updatedRow.put(MySQLiteHelper.GROUPS_COLUMN_DESCRIPTION, description);

        database.update(MySQLiteHelper.TABLE_GROUPS, updatedRow,
                MySQLiteHelper.GROUPS_COLUMN_ID + id, null);
    }

    public void deleteGroup(long id){
        database.delete(MySQLiteHelper.TABLE_GROUPS,
                MySQLiteHelper.GROUPS_COLUMN_ID+"="+Long.toString(id),
                null);

    }
    private Group cursorToGroup(Cursor cursor){
        Group group = new Group();
        group.setId(cursor.getLong(0));
        group.setName(cursor.getString(1));
        group.setDescription(cursor.getString(2));
        return group;
    }
}
