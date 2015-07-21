package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ViewLabels extends ActionBarActivity {

    private LabelsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_labels);

        dataSource = new LabelsDataSource(this);
        try{
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Label> values = dataSource.getAllLabels();

        final LabelsAdapter adapter = new LabelsAdapter(this, values);

        final ListView labelsList = (ListView)(findViewById(R.id.list_labels));
        labelsList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_labels, menu);
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
    public void moveToAddNewLabel(View view){
        Intent intent = new Intent(this,NewLabel.class);
        startActivity(intent);
    }
}
