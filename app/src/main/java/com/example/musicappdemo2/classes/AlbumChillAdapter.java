package com.example.musicappdemo2.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappdemo2.R;

import java.util.ArrayList;

public class AlbumChillAdapter extends RecyclerView.Adapter {
    ArrayList<AlbumItem> albumItems;

    public AlbumChillAdapter(ArrayList<AlbumItem> albumItems, Context context) {
        this.albumItems = albumItems;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_album_chill, parent, false);
        AlbumChillViewHolder viewHolder = new AlbumChillViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumItem item = albumItems.get(position);
        AlbumChillViewHolder albumChillViewHolder = (AlbumChillViewHolder) holder;
        albumChillViewHolder.textViewName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return albumItems.size();
    }

    class AlbumChillViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewAvatar;
        TextView textViewName;
        public AlbumChillViewHolder(@NonNull View itemView) {

            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewItemChillAvatar);
            textViewName = itemView.findViewById(R.id.textViewItemChillName);
        }
    }
}