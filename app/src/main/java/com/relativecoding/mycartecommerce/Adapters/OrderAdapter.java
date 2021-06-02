package com.relativecoding.mycartecommerce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.relativecoding.mycartecommerce.Models.Orders;
import com.relativecoding.mycartecommerce.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    ArrayList<Orders> list;

    Context context;

    public OrderAdapter(ArrayList<Orders> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {

        Orders order=list.get(position);
        holder.orderId.setText("Order Id: "+order.getOrderId());
        holder.orderDate.setText("Ordred on: "+order.getOrderDate());
        holder.totalPrice.setText(order.getTotalPrice());
        //holder.recyclerView.

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {


        TextView orderId;
        TextView orderDate;
        TextView totalPrice;

        RecyclerView recyclerView;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.orderId);
            orderDate=itemView.findViewById(R.id.orderDate);
            recyclerView=itemView.findViewById(R.id.orderRecyclerView);
            totalPrice=itemView.findViewById(R.id.totalPrice);

        }
    }
}
