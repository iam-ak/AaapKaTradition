package com.relativecoding.mycartecommerce.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.relativecoding.mycartecommerce.Activities.ItemDetailActivity;
import com.relativecoding.mycartecommerce.Models.Item;
import com.relativecoding.mycartecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private Context context;
    private ArrayList<Item> list;

    public ItemAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        Item item=list.get(position);

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        private TextView itemName;
        private TextView itemPrice;
        private ImageView itemImage;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.itemName);
            itemPrice=itemView.findViewById(R.id.itemPrice);
            itemImage=itemView.findViewById(R.id.itemImage);
        }
    }
}
