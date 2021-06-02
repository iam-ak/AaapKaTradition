package com.relativecoding.mycartecommerce.ui.cart;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.relativecoding.mycartecommerce.Adapters.CartAdapter;
import com.relativecoding.mycartecommerce.Adapters.ItemAdapter;
import com.relativecoding.mycartecommerce.Models.Item;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.FragmentCartBinding;
import com.relativecoding.mycartecommerce.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CartFragment extends Fragment {

    ArrayList<Item> items;
    FirebaseDatabase database;
    DatabaseReference reference;
    CartAdapter adapter;
    FragmentCartBinding binding;
    FirebaseStorage storage;
    StorageReference storageReference;

    Parcelable mListState;
    RecyclerView.LayoutManager layoutManager;

    int totalPrice;

    DataSnapshot itemCart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //View root = inflater.inflate(R.layout.fragment_cart, container, false);

        binding=FragmentCartBinding.inflate(inflater,container,false);


        items=new ArrayList<>();
//        adapter=new CartAdapter(getContext(), items);
        adapter=new CartAdapter();
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        //binding.recyclerView.addItemDecoration(new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL));

//        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration Hdivider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
//        DividerItemDecoration Vdivider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        Hdivider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
//        Vdivider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
//        binding.recyclerView.addItemDecoration(Hdivider);
//        binding.recyclerView.addItemDecoration(Vdivider);


        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        reference.child("cart");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference.child("Images");

        reference.child("cart").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemCart=snapshot;
                items.clear();
                totalPrice=0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Item item=dataSnapshot.getValue(Item.class);
                    item.setItemId(dataSnapshot.getKey());

                    items.add(item);
                    totalPrice=totalPrice+Integer.parseInt(item.getItemPrice());
                }
                adapter.submitList(items);
                adapter.notifyDataSetChanged();
                binding.tvTotal.setText("Rs "+totalPrice);
                if(adapter.getItemCount()!=0) {
                    binding.cartBottomRow.setVisibility(View.VISIBLE);
                    binding.noItemToShow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                reference.child("cart").child(FirebaseAuth.getInstance().getUid()).child(adapter.getItemAt(viewHolder.getAdapterPosition()).getItemId()).removeValue();
                if(viewHolder.getAdapterPosition()==0){
                    binding.cartBottomRow.setVisibility(View.GONE);
                    binding.noItemToShow.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(binding.recyclerView);

        if(adapter.getItemCount()==0){
            binding.cartBottomRow.setVisibility(View.GONE);
            binding.noItemToShow.setVisibility(View.VISIBLE);
        }

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference=database.getReference("Orders");

                HashMap hashMap=new HashMap();

                ArrayList<String> itemId=new ArrayList<>();

                for(DataSnapshot dataSnapshot:itemCart.getChildren()){
                    itemId.add(dataSnapshot.getKey());
                }

                hashMap.put("itemId",itemId);

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
                String formattedDate = df.format(c);

                hashMap.put("date", formattedDate);

                hashMap.put("totalPrice",binding.tvTotal.getText().toString());


                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().updateChildren(hashMap);

                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

}