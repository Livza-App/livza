package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Carte_order extends AppCompatActivity implements Carte_itemAdapter.OncartItemlistner {

    private RecyclerView Cart;
    private ArrayList<Carte_item> carte_items;
    public static int cart_pos=0;
    private Carte_itemAdapter carte_itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_order);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));

        initcomponents();


        LinearLayoutManager Lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Cart.setLayoutManager(Lm);
        Cart.setItemAnimator(new DefaultItemAnimator());


        //ADD items to my cart function
        To_Add_Cart_Item();

        //putting arraylist inside the adapter and configure the adapter to the recycleview
        carte_itemAdapter=new Carte_itemAdapter(this,carte_items,this);
        Cart.setAdapter(carte_itemAdapter);

    }

    public void initcomponents(){
        Cart=findViewById(R.id.recyclerView);
    }

    // here we add the item to or card from the layout of categories "Amine hna kol ma y3abezzz Add to card tb3atli name,photo,pric,w angraidiants"
    public void To_Add_Cart_Item(){
        Carte_item itm1=new Carte_item(R.drawable.cat_burger,"Bob burger","700","2");
        Carte_item itm2=new Carte_item(R.drawable.cat_pizza,"Pizza","300","1");
        Carte_item itm3=new Carte_item(R.drawable.cat_hotdog,"Hotdog","250","1");
        carte_items=new ArrayList<>();
        carte_items.add(itm1);
        carte_items.add(itm2);
        carte_items.add(itm3);

        //hna amine dir getExtras wla teaa intent w jibli les donnes mlhdak lyout
    }



    @Override
    public void OncartItemlistner(int position) {

    }
}