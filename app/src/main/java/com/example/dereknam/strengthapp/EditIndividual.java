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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class EditIndividual extends ActionBarActivity {
    private GroupsDataSource groupsDataSource;
    private IndividualsDataSource dataSource;

    private RadioGroup mRadioGroup;
    private ArrayList<Long> groupIdList;

    private long individualId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_individual);

        Intent intent = getIntent();

        individualId = Long.parseLong(intent.getStringExtra("INDIVIDUAL_ID"));



        dataSource = new IndividualsDataSource(this);
        groupsDataSource = new GroupsDataSource(this);

        try{
            dataSource.open();
            groupsDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Individual query = dataSource.getIndividual(individualId);
        EditText editName = (EditText) findViewById(R.id.edit_individual_name);
        EditText editDescription = (EditText) findViewById(R.id.edit_individual_description);
        EditText editShortForm  = (EditText) findViewById(R.id.edit_individual_shortform);

        editName.setText(query.getName());
        editDescription.setText(query.getDescription());
        editShortForm.setText(query.getShort_form());

        Group belongsTo = groupsDataSource.getGroup(query.getGroup_id());

        //Prepare radio boxes - populate with list of groups
        List<Group> values = groupsDataSource.getAllGroups();

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

            if((element.getName()).equals(belongsTo.getName())){
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
        getMenuInflater().inflate(R.menu.menu_edit_individual, menu);
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

    public void updateIndividual(View view){

        EditText editIndividualName = (EditText)findViewById(R.id.edit_individual_name);
        EditText editIndividualDescription = (EditText)findViewById(R.id.edit_individual_description);
        EditText editIndividualShortForm = (EditText)findViewById(R.id.edit_individual_shortform);

        RadioGroup editIndividualGroup = (RadioGroup)findViewById(R.id.list_of_groups);

        long editGroupId = groupIdList.get(editIndividualGroup.getCheckedRadioButtonId());

        dataSource.updateIndividual(individualId,editIndividualName.getText().toString(),
                editIndividualDescription.getText().toString(),
                editIndividualShortForm.getText().toString(),
                editGroupId
        );

        Toast.makeText(getApplicationContext(),"Updated!",Toast.LENGTH_SHORT).show();
    }

    public void deleteIndividual(View view){

        dataSource.deleteIndividual(individualId);

        Toast.makeText(getApplicationContext(),"ID: "+individualId+" deleted!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),ViewIndividuals.class);
        startActivity(intent);

    }
}
