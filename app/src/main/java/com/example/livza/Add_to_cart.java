package com.example.livza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

public class Add_to_cart extends AppCompatActivity {
    private ShapeableImageView imageView;
    private LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        imageView=findViewById(R.id.imageview1999);
        getWindow().setStatusBarColor(getResources().getColor(R.color.pink));

        imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED,0)
                .setTopLeftCorner(CornerFamily.ROUNDED,0)
                .setBottomLeftCorner(CornerFamily.ROUNDED,75)
                .setBottomRightCorner(CornerFamily.ROUNDED,75)
                .build());
        //linearlayout =findViewById(R.id.holder);
        //linearlayout.setTranslationZ(2);


    }
}