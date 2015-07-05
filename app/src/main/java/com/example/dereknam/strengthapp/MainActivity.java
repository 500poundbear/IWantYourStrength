package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.dereknam.strengthapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] groups = getResources().getStringArray(R.array.groups);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,groups);

        ListView listView = (ListView) findViewById(R.id.listView) ;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg="";
                msg+=Integer.toString(position)+" ";
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),UpdatePage.class);
                String extra_message = Integer.toString(position);

                intent.putExtra(EXTRA_MESSAGE,extra_message);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch(id) {
            case R.id.action_settings:
                Toast.makeText(this,"DIENOW",Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_snapshots:
                intent = new Intent(this,Snapshots.class);

                startActivity(intent);

                break;
            case R.id.action_view_groups:
                intent = new Intent(this,ViewGroups.class);
                startActivity(intent);
                
                break;
            case R.id.action_view_individual:
                intent = new Intent(this,ViewIndividuals.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
