package com.tech.apexnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HomeFragment extends Fragment implements SelectedItem{

    private SwipeRefreshLayout swipe;
    private RecyclerView home_recycler_view;
    private ArrayList<Model> blogs;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private HomeAdapter adapter;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        home_recycler_view = view.findViewById(R.id.home_recycler_view);
        swipe = view.findViewById(R.id.swipe);
        home_recycler_view.setHasFixedSize(true);
        home_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        blogs = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference().child("Blog");
        mDatabaseRef.keepSynced(true);


        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            Collections.shuffle(blogs);
            adapter.notifyDataSetChanged();
        });

        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Model articles = snapshot.getValue(Model.class);
                blogs.add(articles);

                adapter = new HomeAdapter(getContext(), blogs, HomeFragment.this);
                home_recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

    @Override
    public void onClickedItem(Model model) {
        Intent intent = new Intent(getContext(), BlogPost.class);
        intent.putExtra("blog", model);
        startActivity(intent);
    }
}
