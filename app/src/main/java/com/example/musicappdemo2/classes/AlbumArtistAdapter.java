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

public class AlbumArtistAdapter extends RecyclerView.Adapter {
    ArrayList<AlbumItem> albumItems;

    public AlbumArtistAdapter(ArrayList<AlbumItem> albumItems, Context context) {
        this.albumItems = albumItems;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_album_artist, parent, false);
        AlbumArtistViewHolder viewHolder = new AlbumArtistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumItem item = albumItems.get(position);
        AlbumArtistViewHolder albumArtistViewHolder =(AlbumArtistViewHolder) holder;
        albumArtistViewHolder.textViewName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return albumItems.size();
    }

    class AlbumArtistViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewAvatar;
        TextView textViewName;
        public AlbumArtistViewHolder(@NonNull View itemView) {

            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewItemArtistAvatar);
            textViewName = itemView.findViewById(R.id.textViewItemArtistName);
        }
    }
}
