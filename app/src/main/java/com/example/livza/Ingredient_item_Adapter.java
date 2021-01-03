package com.example.livza;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
//this class is adapter for menuitems listview of activity_menu.xml
public class Ingredient_item_Adapter extends ArrayAdapter<Ingredient_item> {
    private  Activity context;
    private  ArrayList<Ingredient_item> ingredient_item;

    //constructor
    public Ingredient_item_Adapter(Activity context, ArrayList<Ingredient_item> ingredient_item) {
        super(context,R.layout.ingredient_item,ingredient_item);
        this.context=context;
        this.ingredient_item=ingredient_item;

    }
    //this method is responsable of displaying data into screen
    // it return a view which reference one row of menuitems listview
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.ingredient_item, null,true);
        TextView ing_name=rowView.findViewById(R.id.ingredient_name);
        ImageView ing_checkbox=rowView.findViewById(R.id.ingredient_checkbox);
        ing_name.setText(ingredient_item.get(position).getName());
        ing_checkbox.setImageResource(ingredient_item.get(position).getCheckbox());

        return rowView;

    };
}
