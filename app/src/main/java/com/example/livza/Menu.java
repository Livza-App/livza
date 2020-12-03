package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    private ListView menu;
    private ArrayList<Fooditem> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Fooditem hamburger=new Fooditem(R.drawable.burger1,"20min-30min","100DA","Simple Burger","description","4.0");

        Fooditem hamburger2=new Fooditem(R.drawable.burger2,"10min-20min","50DA","cheese Burger","desc","3.0");

        Fooditem hamburger3=new Fooditem(R.drawable.burger3,"00min-10min","45DA","chott Burger","L3 SIQ","5.0");
        foods=new ArrayList<>();
        foods.add(hamburger);
        foods.add(hamburger2);
        foods.add(hamburger3);
        MenuitemAdapter adapter=new MenuitemAdapter(this,foods);
        menu=findViewById(R.id.menuitems);
        menu.setAdapter(adapter);
    }
}