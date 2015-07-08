package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class EditGroup extends ActionBarActivity {
    private GroupsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        Intent intent = getIntent();
        //Toast.makeText(this,intent.getStringExtra("GROUP_NAME").toString(),Toast.LENGTH_LONG).show();

        dataSource = new GroupsDataSource(this);
        try{
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Group query = dataSource.getGroup(intent.getStringExtra("GROUP_NAME").toString());
        //Toast.makeText(getApplicationContext(),query.getDescription(),Toast.LENGTH_LONG).show();
        TextView viewGroupId = (TextView) findViewById(R.id.view_group_id);
        EditText editGroupName = (EditText) findViewById(R.id.edit_group_name);
        EditText editGroupDescription = (EditText) findViewById(R.id.edit_group_description);

        viewGroupId.setText(Long.toString(query.getId()));
        editGroupName.setText(query.getName());
        editGroupDescription.setText(query.getDescription());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_group, menu);
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

    public void updateGroup(View view){
        TextView viewGroupId = (TextView) findViewById(R.id.view_group_id);
        EditText editGroupName = (EditText) findViewById(R.id.edit_group_name);
        EditText editGroupDescription = (EditText) findViewById(R.id.edit_group_description);

        //Sanitisation here?
        long clean_id = Long.parseLong(viewGroupId.getText().toString());

        dataSource.updateGroup(clean_id,
                editGroupName.getText().toString(),
                editGroupDescription.getText().toString());

        Toast.makeText(getApplicationContext(),"ID: "+clean_id+" updated!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),ViewGroups.class);
        startActivity(intent);

    }
    public void deleteGroup(View view){
        TextView viewGroupId = (TextView) findViewById(R.id.view_group_id);

        //Sanitisation here?
        long clean_id = Long.parseLong(viewGroupId.getText().toString());
        dataSource.deleteGroup(clean_id);

        Toast.makeText(getApplicationContext(),"ID: "+clean_id+" deleted!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),ViewGroups.class);
        startActivity(intent);
    }
}
