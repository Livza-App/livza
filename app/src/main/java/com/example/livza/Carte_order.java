package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Carte_order extends AppCompatActivity implements Carte_itemAdapter.OncartItemlistner {

    private RecyclerView Cart;

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
        carte_itemAdapter=new Carte_itemAdapter(this,Menu.cart,this);
        new ItemTouchHelper(itemTouchelperCallbak).attachToRecyclerView(Cart);
        Cart.setAdapter(carte_itemAdapter);



        //Trush
        Button trush_btn=findViewById(R.id.trush_btn);
        trush_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.cart.clear();
                carte_itemAdapter.notifyDataSetChanged();
                TextView Total_num=findViewById(R.id.total_num);
                Total_num.setText(" 0 DA");
            }
        });


        //add&dell_item_Qte
        carte_itemAdapter.setOnItemClickListener(new Carte_itemAdapter.OncartItemlistner() {
            @Override
            public void OncartItemlistner(int position) {

            }

            @Override
            public void onAdd_Qte_toItemCarte(int position) {

                float n=Float.parseFloat(Menu.cart.get(position).getItm_qte());
                n=n+1;
                int i=(int) n;
                Menu.cart.get(position).setItm_qte(String.valueOf(i));
                float baseprice= Float.parseFloat(Menu.cart.get(position).getItem_base_price());
                float itemprice=baseprice*n;
                Menu.cart.get(position).setItem_price(String.valueOf((int) itemprice)+" DA");
                carte_itemAdapter.notifyItemChanged(position);
                Calcule_TotalSum(Menu.cart,cart_pos);

            }

            @Override
            public void onMinos_Qte_toItemCarte(int position) {
                float n=Float.parseFloat(Menu.cart.get(position).getItm_qte());
                n=n-1;
                if(n<=0){
                    Menu.cart.get(position).setItm_qte("1");
                    Toast toast=Toast.makeText(getApplicationContext(),"you can't set quantity to zero value",Toast.LENGTH_SHORT);
                    toast.show();
                    Toast swipe=Toast.makeText(getApplicationContext(),"if you want to delete this item swipe it left or right!",Toast.LENGTH_SHORT);
                    swipe.show();
                    Menu.cart.get(position).setItem_price(Menu.cart.get(position).getItem_base_price()+" DA");
                    carte_itemAdapter.notifyItemChanged(position);
                }else {
                    int i=(int) n;
                    Menu.cart.get(position).setItm_qte(String.valueOf(i));
                    float baseprice= Float.parseFloat(Menu.cart.get(position).getItem_base_price());
                    float itemprice=baseprice*n;
                    Menu.cart.get(position).setItem_price(String.valueOf((int) itemprice)+" DA");
                    carte_itemAdapter.notifyItemChanged(position);
                }
                Calcule_TotalSum(Menu.cart,cart_pos);
            }
        });

        //Total_summ
        Calcule_TotalSum(Menu.cart,cart_pos);

        //btn_back
        Button btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Carte_order.this,Menu.class));
            }
        });
    }

    public void initcomponents(){
        Cart=findViewById(R.id.recyclerView);
    }

    // here we add the item to or card from the layout of categories "Amine hna kol ma y3abezzz Add to card tb3atli name,photo,pric,w angraidiants"
    public void To_Add_Cart_Item(){
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
            Menu.cart.remove(viewHolder.getAdapterPosition());
            carte_itemAdapter.notifyDataSetChanged();
            Calcule_TotalSum(Menu.cart,cart_pos);

        }
    };


    //Total sum
    public void Calcule_TotalSum(ArrayList<Carte_item> carte_items,int cart_pos){
        float Total= 0;
        TextView Total_num=findViewById(R.id.total_num);
        if (carte_items.isEmpty()){
            Total_num.setText(" 0 DA");
        }else{
            for (cart_pos=0;cart_pos<(carte_items.size());cart_pos++){
                float item_price=carte_items.get(cart_pos).get_price_value();
                Total=Total+item_price;
            }
            Total_num.setText((int) Total+" DA");
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