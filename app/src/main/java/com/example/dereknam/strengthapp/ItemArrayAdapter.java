package com.example.dereknam.strengthapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Derek Nam on 29-06-2015.
 */
public class ItemArrayAdapter extends BaseAdapter implements ListAdapter {

    private IndividualsDataSource dataSource;
    private StatusesDataSource statusesDataSource;

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    public ItemArrayAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;

        dataSource = new IndividualsDataSource(context);
        statusesDataSource = new StatusesDataSource(context);
        try{
            dataSource.open();
            statusesDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public int getCount() {
        return list.size();
    }
    public Object getItem(int pos) {
        return list.get(pos);
    }
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.itemwithdel, null);
        }

        //Fetch row from database related to user
        Individual currentIndividual = dataSource.getIndividual(Long.parseLong(list.get(position)));

        //using individual's id to query latest status record found
        Status currentRecord = statusesDataSource.getLatestStatus(currentIndividual.getId());

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.togglebutton);
        listItemText.setText(currentIndividual.getName());

        toggleButton.setChecked(true);

        //Handle buttons and add onClickListeners
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        spinner.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });

        return view;
    }
}
