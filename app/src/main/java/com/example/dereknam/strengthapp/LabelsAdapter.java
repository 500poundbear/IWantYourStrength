package com.example.dereknam.strengthapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dereknam.strengthapp.Individual;
import com.example.dereknam.strengthapp.Label;

import java.util.ArrayList;

/**
 * Created by Derek Nam on 18-07-2015.
 */
public class LabelsAdapter extends ArrayAdapter<Label> {

    public LabelsAdapter(Context context, ArrayList<Label> labels){
        super(context, 0, labels);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Label label = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.label_view,parent, false);
        }

        TextView tvLabel = (TextView) convertView.findViewById(R.id.label_label);
        tvLabel.setText(label.getLabel());

        return convertView;
    }

}
