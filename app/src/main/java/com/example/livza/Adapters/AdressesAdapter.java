package com.example.livza.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.livza.FireClasses.Adresse;
import com.example.livza.R;

import java.util.ArrayList;


public class AdressesAdapter extends ArrayAdapter<Adresse> {
    private Activity context;
    private ArrayList<Adresse> adresses;

    //constructor
    public AdressesAdapter(Activity context, ArrayList<Adresse> adresses) {
        super(context, R.layout.menu_item, adresses);
        this.context = context;
        this.adresses = adresses;
    }

    //this method is responsable of displaying data into screen
    // it return a view which reference one row of menuitems listview
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adresse_item, null, true);
        TextView name;
        name = rowView.findViewById(R.id.adresse_item_text);
        name.setText(adresses.get(position).getName());
        return rowView;
    }
}
