package com.example.livza;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatitemAdapter extends RecyclerView.Adapter<CatitemAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<Categorieitem> cat;
    private Oncategorielistner moncategorielistner;

    public CatitemAdapter(Context context, ArrayList<Categorieitem> cat,Oncategorielistner oncategorielistner) {
        this.context =context;
        this.cat = cat;
        this.moncategorielistner=oncategorielistner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_item, parent, false);
        return new ViewHolder(view,moncategorielistner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == Menu.cat_pos){
            holder.imageholder.setBackground(context.getResources().getDrawable(R.drawable.solidcircle_white));
            holder.main.setBackground(context.getResources().getDrawable(R.drawable.cat_rounded_vector_pink));
            holder.categorie.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            holder.imageholder.setBackground(context.getResources().getDrawable(R.drawable.solidcircle));
            holder.main.setBackground(context.getResources().getDrawable(R.drawable.cat_rounded_vector));
            holder.categorie.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.catimage.setImageResource(cat.get(position).getImageid());
        holder.categorie.setText(cat.get(position).getCategorie());
    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout main,imageholder;
        ImageView catimage;
        TextView categorie;
        Oncategorielistner oncategorielistner;
        public ViewHolder(@NonNull View itemView,Oncategorielistner oncategorielistner ) {
            super(itemView);
            this.oncategorielistner=oncategorielistner;
            catimage=itemView.findViewById(R.id.caticon);
            categorie=itemView.findViewById(R.id.categorie);
            main=itemView.findViewById(R.id.rounde_shape);
            imageholder=itemView.findViewById(R.id.circle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            oncategorielistner.oncategorieitemlistner(getAdapterPosition());
        }
    }


    public interface Oncategorielistner{
        void oncategorieitemlistner(int position);
    }
}
