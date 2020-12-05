package com.example.livza;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MenuitemAdapter extends ArrayAdapter<Fooditem> {
    private  Activity context;
    private  ArrayList<Fooditem> food;


    public MenuitemAdapter(Activity context, ArrayList<Fooditem> food) {
        super(context,R.layout.menu_item,food);
        this.context=context;
        this.food=food;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.menu_item, null,true);
        TextView time,price,title,subtitle,rating;
        time=rowView.findViewById(R.id.time); time.setText(food.get(position).getTime());
        price=rowView.findViewById(R.id.price); price.setText(food.get(position).getPrice());
        title=rowView.findViewById(R.id.foodtitle); title.setText(food.get(position).getTitle());
        subtitle=rowView.findViewById(R.id.subtitle); subtitle.setText(food.get(position).getSubtitle());
        rating=rowView.findViewById(R.id.rating); rating.setText(food.get(position).getRating());
        ImageView foodimg=rowView.findViewById(R.id.food_image); foodimg.setImageResource(food.get(position).getImgid());



        return rowView;

    };
}
