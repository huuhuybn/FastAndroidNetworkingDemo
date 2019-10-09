package com.dotplays.demoassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dotplays.demoassignment.R;
import com.dotplays.demoassignment.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    public Context context;
    public List<Photo> photos;

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo,
                parent, false);

        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo photo = photos.get(position);

        Glide.with(context).load(photo.getUrlS()).into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
