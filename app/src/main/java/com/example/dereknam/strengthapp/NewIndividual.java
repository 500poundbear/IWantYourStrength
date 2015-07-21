package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class NewIndividual extends ActionBarActivity {
    private GroupsDataSource dataSource;
    private IndividualsDataSource individualsDataSource;

    private RadioGroup mRadioGroup;
    private ArrayList<Long> groupIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_individual);

        dataSource = new GroupsDataSource(this);
        try{
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        individualsDataSource = new IndividualsDataSource(this);
        try{
            individualsDataSource.open();
        } catch(SQLException e){
            e.printStackTrace();
        }

        //Query the groups available
        List<Group> values = dataSource.getAllGroups();

        groupIdList = new ArrayList<Long>(100);

        mRadioGroup = (RadioGroup) findViewById(R.id.list_of_groups);

        RadioButton[] buttons = new RadioButton[100]; //ASSUMPTION: NUM GROUP LIMIT IS 100

        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        int q=0;
        for(ListIterator<Group> iter = values.listIterator(); iter.hasNext();){
            Group element = iter.next();
            buttons[q] = new RadioButton(this);
            buttons[q].setText(element.getName());
            buttons[q].setId(q);
            if(q==0){
                buttons[q].setChecked(true);
            }
            mRadioGroup.addView(buttons[q],q,layoutParams);

            groupIdList.add(q, element.getId());

            q++;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_individual, menu);
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
    public void addIndividual(View view){

        Toast.makeText(getApplicationContext(),"YRS",Toast.LENGTH_SHORT).show();

        EditText fullName = (EditText)findViewById(R.id.full_name);
        EditText description = (EditText)findViewById(R.id.description);
        EditText shortForm = (EditText)findViewById(R.id.short_form);
        RadioGroup x = (RadioGroup)findViewById(R.id.list_of_groups);
        Toast.makeText(getApplicationContext(),"HI "+groupIdList.get(x.getCheckedRadioButtonId()),Toast.LENGTH_LONG).show();

        individualsDataSource.createIndividual(fullName.getText().toString(),
                description.getText().toString(),
                shortForm.getText().toString(),
                groupIdList.get(x.getCheckedRadioButtonId()));

        Intent intent = new Intent(getApplicationContext(),ViewIndividuals.class);
        startActivity(intent);
    }
}
