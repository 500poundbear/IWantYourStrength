package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class NewLabel extends ActionBarActivity {

    private LabelsDataSource labelsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_label);

        /*groupsDataSource = new GroupsDataSource(this);
        try{
            groupsDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        labelsDataSource = new LabelsDataSource(this);
        try{
            labelsDataSource.open();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_label, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addLabel(View view){

        EditText editLabel = (EditText)findViewById(R.id.edit_label);

        String cleanLabel = editLabel.getText().toString();

        labelsDataSource.createLabel(cleanLabel);

        Toast.makeText(this, "Label " + cleanLabel + " created!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,ViewLabels.class);
        startActivity(intent);

    }
}
