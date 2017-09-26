package com.example.ferzi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ferzi on 11/09/2017.
 */

public class ListViewAdapter extends ArrayAdapter<Beer> {

    public ListViewAdapter(Context context, ArrayList<Beer> beers) {
        super(context, 0, beers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Beer beer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conf_list, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        //TextView img = (TextView) convertView.findViewById(R.id.main_list_img);
        // Populate the data into the template view using the data object
        name.setText(beer.getName());
        // Return the completed view to render on screen
        return convertView;
    }

}
