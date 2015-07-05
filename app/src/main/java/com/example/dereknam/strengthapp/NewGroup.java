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


public class NewGroup extends ActionBarActivity {

    private GroupsDataSource groupsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        groupsDataSource = new GroupsDataSource(this);
        try{
            groupsDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_group, menu);
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
    public void createNewGroup(View view) {
        EditText inpName = (EditText)findViewById(R.id.create_new_group_name);
        EditText inpDescription = (EditText) findViewById(R.id.create_new_group_description);

        String cleanName = inpName.getText().toString();
        String cleanDescription = inpDescription.getText().toString();

        groupsDataSource.createGroup(cleanName,cleanDescription);

        Toast.makeText(this,"Group "+cleanName+" created!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,ViewGroups.class);
        startActivity(intent);
    }
}
