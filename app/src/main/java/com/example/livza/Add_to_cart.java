package com.example.livza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.livza.Adapters.Ingredient_item_Adapter;
import com.example.livza.FireClasses.Carte_item;
import com.example.livza.FireClasses.Ingredient_item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Add_to_cart extends AppCompatActivity {
    private ShapeableImageView imageView;
    private ArrayList<Ingredient_item> ingredient_items;
    private Ingredient_item_Adapter ing_adapter;
    private ListView ing_listview;
    private String imgPath,price,title,time;
    private ArrayList<String> ingredients;
    private TextView tprice,ttitle,ttime,quantity;
    private Button gotocart,goback,add;
    private ImageView minusbtn,plusbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));
        loaddata();
        init_components();
        load_image();
        filllistview();
        plusclick();
        minusclick();
        back();
        opencartactivity();
        addclick();
        animate();
    }

    private void loaddata() {
        //getting intent info
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgPath = extras.getString("image");
            price = extras.getString("price");
            ingredients = extras.getStringArrayList("ingredients");
            time = extras.getString("time");
            title = extras.getString("title");

        }

    }

    public void init_components(){
        imageView=findViewById(R.id.addtocart_foodimage);
        ing_listview=findViewById(R.id.ing_listview);
        tprice=findViewById(R.id.addtocart_price);
        tprice.setText(price);
        ttime=findViewById(R.id.addtocart_time);
        ttime.setText(time);
        ttitle=findViewById(R.id.addtocart_title);
        ttitle.setText(title);
        quantity=findViewById(R.id.addtocart_quantity);
        quantity.setText("1");
        minusbtn=findViewById(R.id.addtocart_minusbutton);
        plusbtn=findViewById(R.id.addtocart_plusbutton);
        gotocart=findViewById(R.id.addtocart_gotocart);
        goback=findViewById(R.id.addtocart_backbtn);
        add=findViewById(R.id.addtocart_btn);

    }
    public void load_image(){
        //load image from firebase
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = mStorage.getReference().child("/"+imgPath);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(imageView);
                setImage_radius();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Glide.with(getApplicationContext())
                        .load(R.drawable.megapizza)
                        .into(imageView);
                setImage_radius();
            }
        });

    }
    public void setImage_radius(){
        imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED,0)
                .setTopLeftCorner(CornerFamily.ROUNDED,0)
                .setBottomLeftCorner(CornerFamily.ROUNDED,75)
                .setBottomRightCorner(CornerFamily.ROUNDED,75)
                .build());
    }
    public void  filllistview() {
        ingredient_items=new ArrayList<>();

        for (int i=0; i<ingredients.size(); i++){
            Ingredient_item ingredient_item=new Ingredient_item(R.drawable.addtocart_cochefalse,ingredients.get(i));
            ingredient_items.add(ingredient_item);
        }

        ing_adapter=new Ingredient_item_Adapter(this,ingredient_items);
        ing_listview.setAdapter(ing_adapter);
        ing_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ingredient_items.get(position).getCheckbox() == R.drawable.addtocart_cochefalse){
                    ingredient_items.get(position).setCheckbox(R.drawable.addtocart_cochetrue);
                }else {
                    ingredient_items.get(position).setCheckbox(R.drawable.addtocart_cochefalse);

                }
                ing_adapter.notifyDataSetChanged();
            }
        });
    }
    public void plusclick(){
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt(quantity.getText().toString());
                i++;
                quantity.setText(String.valueOf(i));
            }
        });

    }

    public void minusclick(){
        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=Integer.parseInt(quantity.getText().toString());
                i--;
                if(i<=0){
                    quantity.setText("1");
                    Toast toast=Toast.makeText(getApplicationContext(),"you can't set quantity to zero value",Toast.LENGTH_SHORT);

                    toast.show();
                }else {
                    quantity.setText(String.valueOf(i));
                }
            }
        });
          }


    public void back(){
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void opencartactivity(){
        Animation scaleup=AnimationUtils.loadAnimation(this,R.anim.anime_scale_up);
        Animation scaledwon=AnimationUtils.loadAnimation(this,R.anim.anim_scale_down);
        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Carte_order.class);
                startActivity(intent);
            }
        });

    }
    public void addclick(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //title,price,quantity,ingredient,imgid;
                String ingredient=getingredients();
                String qnt=quantity.getText().toString();
                String basic_price=price.substring(0, price.length()-2).trim();
                float qntxprice= Float.parseFloat(qnt)*Float.parseFloat(basic_price);
                String totalprice=(int)qntxprice+" DA";
                Carte_item item=new Carte_item(imgPath, title, totalprice, qnt, ingredient,basic_price);
                Menu.cart.add(item);

                //Toast toast=Toast.makeText(getApplicationContext(),ingredient,Toast.LENGTH_SHORT);
                //toast.show();
            }
        });
    }

    public String getingredients(){
        String ingredient="nothing";
        for(int i=0; i<ingredient_items.size();i++){
            if(ingredient_items.get(i).getCheckbox() == R.drawable.addtocart_cochetrue){
                if(ingredient.equals("nothing")){
                    ingredient = ingredient_items.get(i).getName();
                }else {
                    ingredient = ingredient + "-" + ingredient_items.get(i).getName();
                }

            }
        }
        if(ingredient.equals("nothing")){
            return "";
        }else{
            return ingredient;
        }
    }

    //this function to make animation for our View
    public void animate(){
        Animation fadeIn,bonce;
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.anim_fade_in);
        bonce=AnimationUtils.loadAnimation(this,R.anim.anim_bonce);bonce.setInterpolator(new BounceInterpolator());

        LinearLayout price=findViewById(R.id.imageView4);price.setAnimation(bonce);
        TextView title=findViewById(R.id.addtocart_title);title.setAnimation(fadeIn);
        TextView time=findViewById(R.id.addtocart_time);time.setAnimation(bonce);
        CardView card=findViewById(R.id.cardView3);card.setAnimation(bonce);
    }
}