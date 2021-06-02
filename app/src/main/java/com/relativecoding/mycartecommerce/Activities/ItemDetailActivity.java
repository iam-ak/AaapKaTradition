package com.relativecoding.mycartecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.relativecoding.mycartecommerce.HomeActivity;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.ActivityItemDetailBinding;
import com.relativecoding.mycartecommerce.databinding.FragmentCartBinding;
import com.relativecoding.mycartecommerce.ui.cart.CartFragment;

public class ItemDetailActivity extends AppCompatActivity {

    ActivityItemDetailBinding binding;

    ImageView itemImage;
    TextView itemPrice;
    TextView itemName;
    TextView itemDescription;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_detail);

        binding= ActivityItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        getSupportActionBar().setTitle(intent.getStringExtra("title"));


        itemImage = findViewById(R.id.detailImage);
        itemPrice = findViewById(R.id.detailPrice);
        itemName = findViewById(R.id.detailName);
        itemDescription = findViewById(R.id.detailDescription);

        Glide.with(this).load(intent.getStringExtra("itemImage")).into(itemImage);

        itemPrice.setText(intent.getStringExtra("itemPrice"));
        itemName.setText(intent.getStringExtra("itemName"));
        itemDescription.setText(intent.getStringExtra("itemDescription"));

        reference = FirebaseDatabase.getInstance().getReference("cart");

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reference.child("cart").child(getIntent().getStringExtra("itemId")).child(getIntent().getStringExtra("itemId"));

                
                reference.child(FirebaseAuth.getInstance().getUid()).child(getIntent().getStringExtra("itemId")).child("itemName").setValue(getIntent().getStringExtra("itemName"));
                reference.child(FirebaseAuth.getInstance().getUid()).child(getIntent().getStringExtra("itemId")).child("itemDescription").setValue(getIntent().getStringExtra("itemDescription"));
                reference.child(FirebaseAuth.getInstance().getUid()).child(getIntent().getStringExtra("itemId")).child("itemPrice").setValue(getIntent().getStringExtra("itemPrice"));
                reference.child(FirebaseAuth.getInstance().getUid()).child(getIntent().getStringExtra("itemId")).child("itemImage").setValue(getIntent().getStringExtra("itemImage"));

                Toast.makeText(ItemDetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartFragment cartFragment=new CartFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, cartFragment, cartFragment.getClass().getSimpleName())
                        .addToBackStack(null)
                        .commit();

            }
        });


    }
}