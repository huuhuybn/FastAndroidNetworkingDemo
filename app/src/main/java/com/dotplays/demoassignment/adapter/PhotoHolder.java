package com.dotplays.demoassignment.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dotplays.demoassignment.R;

class PhotoHolder extends RecyclerView.ViewHolder {

    public ImageView imgPhoto;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);
        imgPhoto = itemView.findViewById(R.id.imgPhoto);
    }
}
