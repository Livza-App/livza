package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

public class Menu extends AppCompatActivity implements CatitemAdapter.Oncategorielistner{
    private ListView menu;
    private RecyclerView cat;
    private ArrayList<Fooditem> foods,hamburger,pizza,hotdog;
    private ArrayList<Categorieitem> categories;
    private Fooditem hamburger1,hamburger2,hamburger3,pizza1,pizza2,pizza3,hotdog1,hotdog2,hotdog3;
    private DatabaseReference mReference;
    public static int cat_pos=0;
    private CatitemAdapter catadapter;
    private ValueEventListener categorieEvent,foodEvent;
    private MenuitemAdapter adapter;
    private CountDownLatch cat_done=new CountDownLatch(1),food_done=new CountDownLatch(0);
    private boolean firstTime=true;
    private Button btn_menu_drawer,go_card_btn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Menu nav_menu ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setStatusBarColor(getResources().getColor(R.color.menu_status_color));
        init();
        initFirebase();
        //Design horizontal layout
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        cat.setLayoutManager(layoutManager);
        cat.setItemAnimator(new DefaultItemAnimator());
        //creating items and put them into arraylist for "categories_recycleview"
        //initcategories();
       // reloadData();
        //putting arraylist inside the adapter and configure the adapter to the recycleview

        //creating items and put them into arraylist for "menuitems_listview"
        //initmenuitems();

        //putting arraylist inside the adapter and configure the adapter to the listview

        //show Drawer Menu

        button_drawer_menu();
        //for the drawer menu listner
        change_menu();


        //intent to the card Actyvity
        go_card_btn=findViewById(R.id.card_btn);
        go_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent card=new Intent(Menu.this,Carte_order.class);
                startActivity(card);
            }
        });
    }

    public void button_drawer_menu(){
        btn_menu_drawer=findViewById(R.id.menu_drawer_btn);
        btn_menu_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    private void loadFood(String categorie){

    }

    private void initFirebase(){
        mReference= FirebaseDatabase.getInstance().getReference();
        categorieEvent=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categories.clear();
                foods.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    String imageid=ds.child("imageid").getValue(String.class);
                    String categorie=ds.getKey();
                    categories.add(new Categorieitem(imageid,categorie));
                }
                catadapter.notifyDataSetChanged();
                for(DataSnapshot ds:snapshot.child(categories.get(cat_pos).getCategorie()).getChildren()){
                    Log.i("cat",categories.get(cat_pos).getCategorie());
                    if(!ds.getKey().equals("imageid")){
                        foods.add(ds.getValue(Fooditem.class));
                    }
                }
                adapter.notifyDataSetChanged();
                //cat_done.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //cat_done.countDown();

            }
        };
        mReference.child("categorie").addValueEventListener(categorieEvent);

    }

    public void init(){
        //view
        cat=findViewById(R.id.categories);
        menu=findViewById(R.id.menuitems);
        drawerLayout=findViewById(R.id.drawer_Layout);
        navigationView=findViewById(R.id.nav_view);

        //Arraylist
        foods=new ArrayList<>();
        categories=new ArrayList<>();
        //categorie
        catadapter=new CatitemAdapter(this,categories,this);
        cat.setAdapter(catadapter);
        //food
        adapter=new MenuitemAdapter(this,foods);
        menu.setAdapter(adapter);

        //Drawer_Menu
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setStatusBarBackground(R.color.white);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            btn_menu_drawer.setVisibility(View.GONE);
            TextView Title=findViewById(R.id.item_txt);
            Title.setVisibility(View.GONE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status));

        }
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void oncategorieitemlistner(int position) {
        cat_pos=position;
        mReference.child("categorie").child(categories.get(0).getCategorie()).child("imageid").setValue(categories.get(0).getImageid());
        cat_pos=position;
        catadapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }




    public void change_menu(){

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.main_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_setings:
                        startActivity(new Intent(Menu.this,Profile_User.class));
                        break;
                    case R.id.Languge:
                        //startActivity(new Intent(Menu.this,multi_activity.class));
                        break;
                    case R.id.contact_us:
                        //startActivity(new Intent(Menu.this,multi_activity.class));
                        break;
                    case R.id.help:

                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }


}