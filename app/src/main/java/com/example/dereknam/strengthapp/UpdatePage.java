package com.example.dereknam.strengthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;


public class UpdatePage extends ActionBarActivity implements NumberPicker.OnValueChangeListener {
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
        int position = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT);

        final NumberPicker np = (NumberPicker) findViewById(R.id.np);
        np.post(new Runnable() {
            @Override
            public void run() {

                np.setMaxValue(5);
                np.setMinValue(0);
                np.setWrapSelectorWheel(false);
                np.setValue(5);
            }
        });
        final NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        np2.setOnValueChangedListener(this);

        np2.post(new Runnable() {
            @Override
            public void run() {

                np2.setMaxValue(100);
                np2.setMinValue(0);
                np2.setWrapSelectorWheel(false);
                np2.setValue(5);
            }
        });

        ArrayList<String> list = new ArrayList<String>();
        list.add("Hairy Godzilla");
        list.add("item2");
        list.add("Super Long Name Name Name here");
        list.add("item2");
        list.add("item1");
        list.add("item2");
        list.add("item1");
        list.add("item2");
        //instantiate custom adapter
        ItemArrayAdapter adapter = new ItemArrayAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listDetail);
        lView.setAdapter(adapter);
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
}
