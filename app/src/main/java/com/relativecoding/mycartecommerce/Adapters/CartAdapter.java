package com.relativecoding.mycartecommerce.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.relativecoding.mycartecommerce.Activities.ItemDetailActivity;
import com.relativecoding.mycartecommerce.Models.Item;
import com.relativecoding.mycartecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends ListAdapter<Item, CartAdapter.CartHolder> {

    private Context context;
    private ArrayList<Item> list;

    public CartAdapter() {
        super(DiFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Item> DiFF_CALLBACK= new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getItemId()==newItem.getItemId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getItemName().equals(newItem.getItemName())
                    && oldItem.getItemDescription().equals(newItem.getItemDescription())
                    && oldItem.getItemPrice().equals(newItem.getItemPrice());
        }
    };

//    public CartAdapter(Context context, ArrayList<Item> list) {
//        this.context = context;
//        this.list = list;
//    }




    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

//        Item item=list.get(position);

        Item item=getItem(position);

        Picasso.get().load(item.getItemImage()).placeholder(R.drawable.ic_ecommerce).into(holder.itemImage);

        //holder.itemImage.setImageBitmap(item.getBitmap());

        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText("Rs "+item.getItemPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemDetailActivity.class);
                intent.putExtra("title",item.getItemName());

                intent.putExtra("itemName", item.getItemName());
                intent.putExtra("itemPrice", item.getItemPrice());
                intent.putExtra("itemDescription", item.getItemDescription());
                intent.putExtra("itemImage", item.getItemImage());
                intent.putExtra("itemId", item.getItemId());

                context.startActivity(intent);
            }
        });

    }


    public Item getItemAt(int position){
        return getItem(position);
    }

//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    public class CartHolder extends RecyclerView.ViewHolder{

        private TextView itemName;
        private TextView itemPrice;
        private ImageView itemImage;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.cartItemName);
            itemPrice=itemView.findViewById(R.id.cartItemPrice);
            itemImage=itemView.findViewById(R.id.cartItemImage);
        }
    }
}
