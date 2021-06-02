package com.relativecoding.mycartecommerce.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.relativecoding.mycartecommerce.Models.Users;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    FragmentSettingsBinding binding;

    DatabaseReference reference;
    FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_settings, container, false);

        binding=FragmentSettingsBinding.inflate(inflater);

        reference=FirebaseDatabase.getInstance().getReference();

        auth=FirebaseAuth.getInstance();

        reference.child("Users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users user=snapshot.getValue(Users.class);

                    Glide.with(container).load(user.getProfilePic())
                            .placeholder(R.drawable.ic_profile).into(binding.ivProfile);

                    binding.tvMainName.setText(user.getUsername());
                    binding.tvMainEmail.setText(user.getMail());
                    binding.tvName.setText(user.getUsername());
                    binding.tvEmail.setText(user.getMail());
                    if(user.getNumber()!=null)
                    binding.tvNumber.setText(user.getNumber());
                    if(user.getAddress()!=null)
                    binding.tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}