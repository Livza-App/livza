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

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
//this class is adapter for menuitems listview of activity_menu.xml
public class MenuitemAdapter extends ArrayAdapter<Fooditem> {
    private  Activity context;
    private  ArrayList<Fooditem> food;

    //constructor
    public MenuitemAdapter(Activity context, ArrayList<Fooditem> food) {
        super(context,R.layout.menu_item,food);
        this.context=context;
        this.food=food;

    }
    //this method is responsable of displaying data into screen
    // it return a view which reference one row of menuitems listview
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.menu_item, null,true);
        TextView time,price,title,subtitle,rating;
        time=rowView.findViewById(R.id.time); time.setText(food.get(position).getTime());
        price=rowView.findViewById(R.id.price); price.setText(food.get(position).getPrice());
        title=rowView.findViewById(R.id.foodtitle); title.setText(food.get(position).getTitle());
        subtitle=rowView.findViewById(R.id.subtitle); subtitle.setText(food.get(position).getSubtitle());
        rating=rowView.findViewById(R.id.rating); rating.setText(food.get(position).getRating());
        ImageView foodimg=rowView.findViewById(R.id.food_image);

        //readImage
        readImage(foodimg,food.get(position).getImgid());
        return rowView;

    };

    private void readImage(ImageView imgPerfil,String imageID){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child(imageID);
        Glide.with(context.getApplicationContext())
                .load(imagesRef)
                .into(imgPerfil);
    }
}
