package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Menu extends AppCompatActivity implements CatitemAdapter.Oncategorielistner {
    private ListView menu;
    private RecyclerView cat;
    private ArrayList<Fooditem> foods,hamburger,pizza,hotdog;
    private ArrayList<Categorieitem> categories;
    private Fooditem hamburger1,hamburger2,hamburger3,pizza1,pizza2,pizza3,hotdog1,hotdog2,hotdog3;
    public static int cat_pos=0;
    private CatitemAdapter catadapter;
    MenuitemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initcomponents();
        //Design horizontal layout
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        cat.setLayoutManager(layoutManager);
        cat.setItemAnimator(new DefaultItemAnimator());
        //creating items and put them into arraylist for "categories_recycleview"
        initcategories();
        //putting arraylist inside the adapter and configure the adapter to the recycleview
        catadapter=new CatitemAdapter(this,categories,this);
        cat.setAdapter(catadapter);
        //creating items and put them into arraylist for "menuitems_listview"
        initmenuitems();
        foods=new ArrayList<>();
        foods=hamburger;
        //putting arraylist inside the adapter and configure the adapter to the listview
        adapter=new MenuitemAdapter(this,foods);
        menu.setAdapter(adapter);


    }
    public void initcomponents(){
        cat=findViewById(R.id.categories);
        menu=findViewById(R.id.menuitems);
    }
    public void initcategories(){
        Categorieitem cat1=new Categorieitem(R.drawable.cat_burger,"Burger");
        Categorieitem cat2=new Categorieitem(R.drawable.cat_pizza,"Pizza");
        Categorieitem cat3=new Categorieitem(R.drawable.cat_hotdog,"Hotdog");
        categories=new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
    }
    public void initmenuitems(){
        hamburger1=new Fooditem(R.drawable.burger1,"20min-30min","100DA","Simple Burger","description","4.0");
        hamburger2=new Fooditem(R.drawable.burger2,"10min-20min","50DA","cheese Burger","desc","3.0");
        hamburger3=new Fooditem(R.drawable.burger3,"00min-10min","45DA","chott Burger","L3 SIQ","5.0");
        hamburger=new ArrayList<>();
        hamburger.add(hamburger1);
        hamburger.add(hamburger2);
        hamburger.add(hamburger3);

        pizza1=new Fooditem(R.drawable.pizzasimple,"20min-30min","200DA","pizza simple","description","4.0");
        pizza2=new Fooditem(R.drawable.pizzafrites,"30min-40min","250DA","pizza frites","frites","4.2");
        pizza3=new Fooditem(R.drawable.megapizza,"30min-50min","1200DA","mega pizza","simple","5.0");
        pizza=new ArrayList<>();
        pizza.add(pizza1);
        pizza.add(pizza2);
        pizza.add(pizza3);

        hotdog1=new Fooditem(R.drawable.hotdogsimple,"10min-15min","200DA","hotdog simple","description","4.0");
        hotdog2=new Fooditem(R.drawable.chicagostylehotdog,"30min-40min","450DA","chicago hotdog","usa","4.2");
        hotdog3=new Fooditem(R.drawable.corndog,"00min-05min","50DA","cornsdog","Corn","5.0");
        hotdog=new ArrayList<>();
        hotdog.add(hotdog1);
        hotdog.add(hotdog2);
        hotdog.add(hotdog3);

    }
    //here we called the listener we have created at CatitemAdapter class(adapter)
    @Override
    public void oncategorieitemlistner(int position) {
        cat_pos=position;
        switch(position) {
            case 0:
                foods.clear();
                Fooditem hamburger1=new Fooditem(R.drawable.burger1,"20min-30min","100DA","Simple Burger","description","4.0");
                Fooditem hamburger2=new Fooditem(R.drawable.burger2,"10min-20min","50DA","cheese Burger","desc","3.0");
                Fooditem hamburger3=new Fooditem(R.drawable.burger3,"00min-10min","45DA","chott Burger","L3 SIQ","5.0");
                foods.add(hamburger1);
                foods.add(hamburger2);
                foods.add(hamburger3);

                adapter.notifyDataSetChanged();
                break;
            case 1:
                foods.clear();
                Fooditem pizza1=new Fooditem(R.drawable.pizzasimple,"20min-30min","200DA","pizza simple","description","4.0");
                Fooditem pizza2=new Fooditem(R.drawable.pizzafrites,"30min-40min","250DA","pizza frites","frites","4.2");
                Fooditem pizza3=new Fooditem(R.drawable.megapizza,"30min-50min","1200DA","mega pizza","simple","5.0");
                foods.add(pizza1);
                foods.add(pizza2);
                foods.add(pizza3);

                adapter.notifyDataSetChanged();
                break;
            case 2:
                foods.clear();
                Fooditem hotdog1=new Fooditem(R.drawable.hotdogsimple,"10min-15min","200DA","hotdog simple","description","4.0");
                Fooditem hotdog2=new Fooditem(R.drawable.chicagostylehotdog,"30min-40min","450DA","chicago hotdog","usa","4.2");
                Fooditem hotdog3=new Fooditem(R.drawable.corndog,"00min-05min","50DA","cornsdog","Corn","5.0");
                foods.add(hotdog1);
                foods.add(hotdog2);
                foods.add(hotdog3);

                adapter.notifyDataSetChanged();
                break;
            default:

                break;
        }

        catadapter.notifyDataSetChanged();
    }
}