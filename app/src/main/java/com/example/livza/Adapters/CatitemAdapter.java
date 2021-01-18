package com.example.livza.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livza.FireClasses.Categorieitem;
import com.example.livza.Menu;
import com.example.livza.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;



//this class is adapter for categories Recyclerview of activity_menu.xml + we are creating a listener<itemlistener> for this adapter
public class CatitemAdapter extends RecyclerView.Adapter<CatitemAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<Categorieitem> cat;
    private Oncategorielistner moncategorielistner;
    //constructor
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
    //this method is responsable of displaying data into screen
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
        String imgPath = cat.get(position).getImageid();
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mStorage.getReference().child("/Categorie/"+imgPath);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.catimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Glide.with(context)
                        .load(R.drawable.addtocart_cochetrue)
                        .into(holder.catimage);
            }
        });
        holder.categorie.setText(cat.get(position).getCategorie());
    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    //this class describe each column of our recyclerview
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
        //here we are configuring the oncategorieitemlistner
        @Override
        public void onClick(View v) {
            oncategorielistner.oncategorieitemlistner(getAdapterPosition());
        }
    }

    //interface of listner containing itemlistener
    public interface Oncategorielistner{
        void oncategorieitemlistner(int position);
    }
}
