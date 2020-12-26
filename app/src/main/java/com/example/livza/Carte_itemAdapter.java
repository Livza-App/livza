package com.example.livza;

import android.app.Activity;
import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Carte_itemAdapter extends RecyclerView.Adapter<Carte_itemAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Carte_item> Cart_items;
    private OncartItemlistner oncartItemlistner;

    public Carte_itemAdapter(Activity context, ArrayList<Carte_item> cart_items, OncartItemlistner oncartItemlistner) {
        this.context = context;
        Cart_items = cart_items;
        this.oncartItemlistner = oncartItemlistner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view,oncartItemlistner);
    }

    //this method is responsable of displaying data into screen
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.item_icn.setImageResource(Cart_items.get(position).getImgid());
            holder.item_name.setText(Cart_items.get(position).getItm_name());
            holder.item_price.setText(Cart_items.get(position).getItem_price());
            holder.item_qte.setText(Cart_items.get(position).getItm_qte());
    }

    @Override
    public int getItemCount() {
        return Cart_items.size();
    }

    //this class describe each row of our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout item_layout;
        CardView item_carde;
        TextView item_name,item_price,item_qte,add_qte,minos_qte;
        ImageView item_icn;
        OncartItemlistner OncartItemlistner;
        public ViewHolder(@NonNull View itemView,OncartItemlistner OncartItemlistner) {
            super(itemView);
            this.OncartItemlistner=OncartItemlistner;
            item_carde=itemView.findViewById(R.id.item_card);
            item_layout=itemView.findViewById(R.id.l1);
            item_name=itemView.findViewById(R.id.item_name);
            item_price=itemView.findViewById(R.id.item_price);
            item_qte=itemView.findViewById(R.id.item_qte);
            item_icn=itemView.findViewById(R.id.img_itm);
            add_qte=itemView.findViewById(R.id.plus_qte);
            minos_qte=itemView.findViewById(R.id.minos_qte);
            itemView.setOnClickListener(this);

            //to add an qte to an item_Card
            add_qte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( OncartItemlistner!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            OncartItemlistner.onAdd_Qte_toItemCarte(position);
                        }
                    }
                }
            });
            // minos an qte to an item_Card
            minos_qte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( OncartItemlistner!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            OncartItemlistner.onMinos_Qte_toItemCarte(position);

                        }

                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            OncartItemlistner.OncartItemlistner(getAdapterPosition());
        }
    }




    //interface of listner containing itemlistener
    public interface OncartItemlistner{
        void OncartItemlistner(int position);
        //to add an qte to an item_Card
        void onAdd_Qte_toItemCarte(int posistion);
        // minos an qte to an item_Card
        void onMinos_Qte_toItemCarte(int position);
    }
}

