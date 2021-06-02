package com.relativecoding.mycartecommerce.ui.my_orders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.relativecoding.mycartecommerce.Adapters.OrderAdapter;
import com.relativecoding.mycartecommerce.Models.Orders;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.FragmentMyOrdersBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MyOrdersFragment extends Fragment {

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    FragmentMyOrdersBinding binding;

    ArrayList<Orders> list;
    OrderAdapter adapter;

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentMyOrdersBinding.inflate(inflater);

        list=new ArrayList<>();
        adapter=new OrderAdapter(list, getContext());
        binding.recyclerViewOrder.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewOrder.setLayoutManager(layoutManager);

        reference= FirebaseDatabase.getInstance().getReference("Orders");
        auth=FirebaseAuth.getInstance();

        reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    HashMap<String, Object> hashMap= (HashMap<String, Object>) dataSnapshot.getValue();
                    //Orders order=dataSnapshot.getValue(Orders.class);

                    Orders order=new Orders();
                    order.setOrderDate(hashMap.get("date").toString());
                    order.setItemId((ArrayList) hashMap.get("itemId"));
                    order.setTotalPrice(hashMap.get("totalPrice").toString());

                    order.setOrderId(dataSnapshot.getKey());
                    list.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}