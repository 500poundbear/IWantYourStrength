package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UpdatePage extends ActionBarActivity implements NumberPicker.OnValueChangeListener {

    private ArrayList<Status> list;

    /////////////TO REMOVE/////////////////////////
    private ArrayList<String> listIds;
    private ArrayList<Boolean> listAvailability;
    private ArrayList<Long> listStatusLabelId;
    /////////////END OF TO REMOVE//////////////////

    private IndividualsDataSource individualsDataSource;
    private StatusesDataSource statusesDataSource;
    ItemArrayAdapter adapter;

    private ArrayList<Individual> values;

    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        final int nVal = newVal;
        np.post(new Runnable() {
            @Override
            public void run() {
                np.setWrapSelectorWheel(false);
                np.setMaxValue(nVal);
                np.setMinValue(0);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);

        Intent intent=getIntent();
        long groupId = Long.parseLong(intent.getStringExtra("GROUP_ID"));

        individualsDataSource = new IndividualsDataSource(this);
        statusesDataSource = new StatusesDataSource(this);
        try{
            individualsDataSource.open();
            statusesDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        values = individualsDataSource.getIndividualsByGroupId(groupId);

        TextView currentTally = (TextView)findViewById(R.id.current_tally);
        TextView currentTotal = (TextView)findViewById(R.id.current_total);

        currentTally.setText("Currently: x/"+values.size());
        currentTotal.setText(String.valueOf(values.size()));

        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        np.post(new Runnable() {
            @Override
            public void run() {

                np.setMaxValue(values.size());
                np.setMinValue(0);
                np.setWrapSelectorWheel(false);
                np.setValue(0);
            }
        });

        list = new ArrayList<Status>();
        for(Individual x:values){
            Status currentRecord = statusesDataSource.getLatestStatus(x.getId());

            Individual queryIndividual = individualsDataSource.getIndividual(x.getId());
            currentRecord.setIndividual(queryIndividual);
            list.add(currentRecord);
            Log.d("DOOO","Currently: "+currentRecord.getAccounted());
        }
        ArrayAdapter<Status> adapter = new StatusesAdapter(this,list);

        ListView lView = (ListView)findViewById(R.id.listDetail);
        lView.setAdapter(adapter);

        /*listIds = new ArrayList<String>();
        for(Individual x:values){
            listIds.add(String.valueOf(x.getId()));
        }

        //instantiate custom adapter
        adapter = new ItemArrayAdapter(listIds, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listDetail);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),String.valueOf(position)+" id: "+String.valueOf(id),Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_page, menu);
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
    public void updateStatuses(View view){
        ListView lView = (ListView)findViewById(R.id.listDetail);

        int visibleChildCount = (lView.getLastVisiblePosition() - lView.getFirstVisiblePosition()) + 1;

        for(int q=0;q<visibleChildCount;q++){
            ToggleButton x = (ToggleButton)lView.getChildAt(q).findViewById(R.id.togglebutton);
            Log.d("DOOO","pos: "+String.valueOf(x.isChecked()));
        }
    }
}
