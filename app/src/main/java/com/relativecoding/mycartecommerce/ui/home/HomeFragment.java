package com.relativecoding.mycartecommerce.ui.home;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.relativecoding.mycartecommerce.Adapters.ItemAdapter;
import com.relativecoding.mycartecommerce.Adapters.UsersAdapter;
import com.relativecoding.mycartecommerce.Models.Item;
import com.relativecoding.mycartecommerce.Models.Users;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.FragmentHomeBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ArrayList<Item> items;
    FirebaseDatabase database;
    DatabaseReference reference;
    ItemAdapter adapter;
    FragmentHomeBinding binding;
    FirebaseStorage storage;
    StorageReference storageReference;

    Parcelable mListState;
    RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_home, container, false);


        binding=FragmentHomeBinding.inflate(inflater,container,false);

        items=new ArrayList<>();
        adapter=new ItemAdapter(getContext(), items);
        binding.recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration Hdivider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration Vdivider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Hdivider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        Vdivider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
//        binding.recyclerView.addItemDecoration(Hdivider);
//        binding.recyclerView.addItemDecoration(Vdivider);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        reference.child("Items");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference.child("Images");

        reference.child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Item item=dataSnapshot.getValue(Item.class);
                    item.setItemId(dataSnapshot.getKey());

                    //

//                    storageReference=storageReference.child("Images/"+item.getItemImage());
//                    try {
//                        final File file=File.createTempFile("WhatsApp Image 2021-04-02 at 8.56.42 AM","jpeg");
//                        storageReference.getFile(file)
//                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                                        item.setBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }



                    items.add(item);
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