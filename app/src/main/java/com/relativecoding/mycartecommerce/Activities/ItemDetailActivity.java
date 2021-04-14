package com.relativecoding.mycartecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.ActivityItemDetailBinding;

public class ItemDetailActivity extends AppCompatActivity {

    ActivityItemDetailBinding binding;

    ImageView itemImage;
    TextView itemPrice;
    TextView itemName;
    TextView itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent=getIntent();

        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        itemImage=findViewById(R.id.detailImage);
        itemPrice=findViewById(R.id.detailPrice);
        itemName=findViewById(R.id.detailName);
        itemDescription=findViewById(R.id.detailDescription);

        Glide.with(this).load(intent.getStringExtra("itemImage")).into(itemImage);

        itemPrice.setText(intent.getStringExtra("itemPrice"));
        itemName.setText(intent.getStringExtra("itemName"));
        itemDescription.setText(intent.getStringExtra("itemDescription"));

    }
}