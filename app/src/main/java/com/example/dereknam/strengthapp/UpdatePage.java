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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UpdatePage extends ActionBarActivity implements NumberPicker.OnValueChangeListener {
    ArrayList<String> labels;
    private ArrayList<Status> list;

    /////////////TO REMOVE/////////////////////////
    private ArrayList<String> listIds;
    private ArrayList<Boolean> listAvailability;
    private ArrayList<Long> listStatusLabelId;
    /////////////END OF TO REMOVE//////////////////

    private IndividualsDataSource individualsDataSource;
    private StatusesDataSource statusesDataSource;
    private LabelsDataSource labelsDataSource;
    private StrengthDataSource strengthDataSource;
    ItemArrayAdapter adapter;

    private ArrayList<Individual> values;

    private long groupId;

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
        groupId = Long.parseLong(intent.getStringExtra("GROUP_ID"));

        individualsDataSource = new IndividualsDataSource(this);
        statusesDataSource = new StatusesDataSource(this);
        labelsDataSource = new LabelsDataSource(this);
        strengthDataSource = new StrengthDataSource(this);
        try{
            individualsDataSource.open();
            statusesDataSource.open();
            labelsDataSource.open();
            strengthDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        values = individualsDataSource.getIndividualsByGroupId(groupId);

        //The below code checks if there has been a strength saved. If yes, it will populate fields




         TextView currentTotal = (TextView)findViewById(R.id.current_total);
        currentTotal.setText(String.valueOf(values.size()));

        refreshBoard();





        list = new ArrayList<Status>();
        for(Individual x:values){
            Status currentRecord = statusesDataSource.getLatestStatus(x.getId());

            Individual queryIndividual = individualsDataSource.getIndividual(x.getId());
            currentRecord.setIndividual(queryIndividual);

            Label queryLabel = labelsDataSource.getLabel(currentRecord.getStatus_label_id());
            currentRecord.setLabel(queryLabel);

            list.add(currentRecord);
        }
        ArrayList<Label> getAllLabels = labelsDataSource.getAllLabels();
        labels = new ArrayList<String>();

        for(Label x:getAllLabels){
            labels.add(x.getLabel());
        }

        ArrayAdapter<Status> adapter = new StatusesAdapter(this,list,labels);

        ListView lView = (ListView)findViewById(R.id.listDetail);
        lView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_page, menu);
        return true;
    }
    public void refreshBoard(){
        TextView currentTally = (TextView)findViewById(R.id.current_tally);

        final Strength getLatestStrength = strengthDataSource.getLatestStrength(groupId);

        final NumberPicker np = (NumberPicker) findViewById(R.id.np);

        if(getLatestStrength.getId()==-1){
            currentTally.setText("No previously saved strength.");
            np.post(new Runnable() {
                @Override
                public void run() {

                    np.setMaxValue(values.size());
                    np.setMinValue(0);
                    np.setWrapSelectorWheel(false);
                    np.setValue(0);
                }
            });
        }else{
            currentTally.setText("Previously: "+getLatestStrength.getCurrent()+" / "+getLatestStrength.getTotal());

            np.post(new Runnable() {
                @Override
                public void run() {

                    np.setMaxValue(values.size());
                    np.setMinValue(0);
                    np.setWrapSelectorWheel(false);
                    np.setValue(getLatestStrength.getCurrent());
                }
            });
        }


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
    public void updateStrength(View view){
        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.np);
        TextView currentTotal = (TextView)findViewById(R.id.current_total);

        int current = numberPicker.getValue();
        int total = Integer.parseInt(currentTotal.getText().toString());

        Strength checkStrength = strengthDataSource.addNewStrength(groupId, current, total);
        Log.d("DOOO",checkStrength.toString());
        Log.d("DOOO",String.valueOf(checkStrength.getCurrent()));
        Log.d("DOOO",String.valueOf(checkStrength.getTotal()));
        Toast.makeText(getApplicationContext(),"CHANGED TO "+current+"/"+total,Toast.LENGTH_SHORT).show();
        refreshBoard();
    }
    public void updateStatuses(View view){
        ListView lView = (ListView)findViewById(R.id.listDetail);

        int visibleChildCount = (lView.getLastVisiblePosition() - lView.getFirstVisiblePosition()) + 1;

        for(int q=0;q<visibleChildCount;q++){
            ToggleButton x = (ToggleButton)lView.getChildAt(q).findViewById(R.id.togglebutton);
            Spinner y = (Spinner)lView.getChildAt(q).findViewById(R.id.spinner);

            Boolean originalCheck = list.get(q).getAccounted();
            Boolean possiblyAlteredCheck = x.isChecked();

            String possiblyAlteredLabel=y.getSelectedItem().toString();
            String originalLabel = list.get(q).getLabel().getLabel();
            if(originalCheck != possiblyAlteredCheck || !originalLabel.equals(possiblyAlteredLabel)  ){
                /*Log.d("DOOO","Hey at index "+q+" the value has changed");
                Log.d("DOOO",list.get(q).getLabel().getLabel());
                Log.d("DOOO",y.getSelectedItem().toString());
                */

                /* If this triggers, create a new status*/
                Status oldStatus = list.get(q);

                //find id in database

                statusesDataSource.addNewStatus(oldStatus.getIndividual_id(),
                        labelsDataSource.getIdOfLabel(possiblyAlteredLabel),
                        possiblyAlteredCheck);

            }
        }
    }
}
