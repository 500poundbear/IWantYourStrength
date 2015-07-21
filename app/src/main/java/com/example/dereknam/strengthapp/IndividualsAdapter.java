package com.example.dereknam.strengthapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Derek Nam on 15-07-2015.
 */
public class IndividualsAdapter extends ArrayAdapter<Individual>{
    public IndividualsAdapter(Context context, ArrayList<Individual> individuals){
        super(context, 0, individuals);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Individual individual = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.individual_view,parent, false);
        }

        TextView tvShortform = (TextView) convertView.findViewById(R.id.individual_shortform);

        tvShortform.setText(individual.getName());

        return convertView;
    }
}
