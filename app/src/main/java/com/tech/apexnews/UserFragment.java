package com.tech.apexnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserFragment extends Fragment {

    private FloatingActionButton create_post;
    private FloatingActionButton ctp;
    private FloatingActionButton cvp;
    private RelativeLayout authLayout;
    private MaterialButton createAccount;
    private MaterialButton loginBtn;



    private FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        create_post = view.findViewById(R.id.create_post);
        ctp = view.findViewById(R.id.create_text_post);
        cvp = view.findViewById(R.id.create_video_post);
        authLayout = view.findViewById(R.id.authLayout);
        createAccount = view.findViewById(R.id.createAccount);
        loginBtn = view.findViewById(R.id.login);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null){
            authLayout.setVisibility(View.VISIBLE);
            create_post.setVisibility(View.INVISIBLE);
        }else {
            authLayout.setVisibility(View.INVISIBLE);
            create_post.setVisibility(View.VISIBLE);
        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateAccount.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        create_post.setOnClickListener(v -> {
            if (ctp.getVisibility() == View.INVISIBLE) {
                ctp.setVisibility(View.VISIBLE);
                cvp.setVisibility(View.VISIBLE);
            }else {
                ctp.setVisibility(View.INVISIBLE);
                cvp.setVisibility(View.INVISIBLE);
            }

        });

        ctp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostNews.class));
            }
        });

        cvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VideoPost.class));
            }
        });


        return view;

    }
}
