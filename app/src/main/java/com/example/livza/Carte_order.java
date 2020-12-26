package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Carte_order extends AppCompatActivity implements Carte_itemAdapter.OncartItemlistner {

    private RecyclerView Cart;
    private ArrayList<Carte_item> carte_items;
    public static int cart_pos=0;
    public static int add=0,delete=0;
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
        new ItemTouchHelper(itemTouchelperCallbak).attachToRecyclerView(Cart);
        Cart.setAdapter(carte_itemAdapter);



        //Trush
        Button trush_btn=findViewById(R.id.trush_btn);
        trush_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carte_items.clear();
                carte_itemAdapter.notifyDataSetChanged();
            }
        });


        carte_itemAdapter.setOnItemClickListener(new Carte_itemAdapter.OncartItemlistner() {
            @Override
            public void OncartItemlistner(int position) {

            }

            @Override
            public void onAdd_Qte_toItemCarte(int position) {

                int n=Integer.parseInt(carte_items.get(position).getItm_qte());
                n=n+1;
                carte_items.get(position).setItm_qte(String.valueOf(n));
                carte_itemAdapter.notifyItemChanged(position);
            }

            @Override
            public void onMinos_Qte_toItemCarte(int position) {
                int n=Integer.parseInt(carte_items.get(position).getItm_qte());
                n=n-1;
                carte_items.get(position).setItm_qte(String.valueOf(n));
                carte_itemAdapter.notifyItemChanged(position);
            }
        });

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

        //hna kol ma t'ajouter item tajouter l price ta3o l total_sum
        //Calcule_TotalSum();
    }

    //Swipe on an item to delete
    ItemTouchHelper.SimpleCallback itemTouchelperCallbak =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            carte_items.remove(viewHolder.getAdapterPosition());
            carte_itemAdapter.notifyDataSetChanged();
        }
    };


    //Total sum
    public void Calcule_TotalSum(ArrayList<Carte_item> carte_items, int pos){
        while(!(carte_items.isEmpty())){
            int Total= 0;
            int item_price=Integer.parseInt(carte_items.get(pos).getItem_price());
            Total=Total+item_price;
            TextView Total_num=findViewById(R.id.total_num);
            Total_num.setText(Total+"DA");
        }
    }

    //this function to remove the "DA" frome items price
    public void Delet_DA(String price){
        price=price.replace("DA","");
    }




    @Override
    public void OncartItemlistner(int position) {

    }

    @Override
    public void onAdd_Qte_toItemCarte(int position) {

    }

    @Override
    public void onMinos_Qte_toItemCarte(int position) {

    }



}