package com.tech.apexnews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<Model> models;
    private Context context;
    SelectedItem selectedItem;

    public HomeAdapter(Context context,ArrayList<Model> models, SelectedItem selectedItem) {
        this.context = context;
        this.models = models;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = models.get(position);
        holder.title.setText(model.getTitle());
        holder.news_source.setText(model.getNewsSource());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.parseLong(model.getTimeStamp())).getTime());
        holder.time_posted.setText(formattedDate);

        String imageUrl = model.getThumbnailURL();
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem.onClickedItem(model);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnail;
        private TextView title;
        private TextView news_source;
        private TextView time_posted;
        private RelativeLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            news_source = itemView.findViewById(R.id.news_source);
            time_posted = itemView.findViewById(R.id.time_posted);
            rootLayout = itemView.findViewById(R.id.rootLayout);

        }
    }
}
