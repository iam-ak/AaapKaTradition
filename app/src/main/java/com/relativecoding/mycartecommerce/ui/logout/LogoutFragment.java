package com.relativecoding.mycartecommerce.ui.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.relativecoding.mycartecommerce.HomeActivity;
import com.relativecoding.mycartecommerce.R;
import com.relativecoding.mycartecommerce.SignInActivity;

public class LogoutFragment extends Fragment {



    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_logout, container, false);

        auth=FirebaseAuth.getInstance();
        auth.signOut();
        Intent intent=new Intent(getContext(), SignInActivity.class);

        intent.putExtra("Logout","Logout");
        startActivity(intent);

        getActivity().finish();

        return view;
    }
}