package com.example.dereknam.strengthapp;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek Nam on 21-07-2015.
 */
public class StatusesAdapter extends ArrayAdapter<Status> {
    private final List<Status> list;
    private final Activity context;
    private final List<String> labels;

    public StatusesAdapter(Activity context, List<Status> list, List<String> labels){
        super(context, R.layout.itemwithdel);
        this.context = context;
        this.list = list;
        this.labels = labels;
    }
    static class ViewHolder{
        protected TextView text;
        protected ToggleButton button;
        protected Spinner spinner;

    }
    public int getCount(){
        return list.size();
    }
    public View getView(int position, View convertView, ViewGroup parent) {

       View view = null;

        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.itemwithdel,null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.text = (TextView)(view.findViewById(R.id.list_item_string));
            viewHolder.button = (ToggleButton)(view.findViewById(R.id.togglebutton));
            viewHolder.spinner = (Spinner) view.findViewById(R.id.spinner);


            viewHolder.text.setText(list.get(position).getIndividual().getName());
            viewHolder.button.setChecked(list.get(position).getAccounted());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, labels);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.spinner.setAdapter(adapter);

            viewHolder.spinner.setSelection(adapter.getPosition(list.get(position).getLabel().getLabel()));

        }else{
            view = convertView;
        }
        return view;
    }
}
