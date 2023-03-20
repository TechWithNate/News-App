package com.tech.apexnews;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;

public class VideoFragment extends Fragment {

    private RecyclerView videoRecycler;
    private SwipeRefreshLayout swipe;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private ArrayList<VideoModel> videos;
    private VideoAdapter adapter;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        videoRecycler = view.findViewById(R.id.video_recycler_view);
        swipe = view.findViewById(R.id.swipe);

        videoRecycler.setHasFixedSize(true);
        videoRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        videos = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("videos");

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                Collections.shuffle(videos);
                adapter.notifyDataSetChanged();
            }
        });

        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VideoModel video = snapshot.getValue(VideoModel.class);
                videos.add(video);

                adapter = new VideoAdapter(getContext(), videos);
                videoRecycler.setAdapter(adapter);
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
}
