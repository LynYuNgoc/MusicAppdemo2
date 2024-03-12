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

public class AlbumAlbumHotAdapter extends RecyclerView.Adapter{
    ArrayList<AlbumItem> albumItems;

    public AlbumAlbumHotAdapter(ArrayList<AlbumItem> albumItems, Context context, AlbumAlbumHotOnClickListener albumAlbumHotOnClickListener) {
        this.albumItems = albumItems;
        this.context = context;
        this.albumAlbumHotOnClickListener =albumAlbumHotOnClickListener;
    }

    Context context;
    AlbumAlbumHotOnClickListener albumAlbumHotOnClickListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_album_albumhot, parent, false);
        AlbumAlbumHotViewHolder viewHolder = new AlbumAlbumHotViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumItem item = albumItems.get(position);
        AlbumAlbumHotViewHolder albumAlbumHotViewHolder = (AlbumAlbumHotViewHolder) holder;
        albumAlbumHotViewHolder.textViewName.setText(item.getName());

        albumAlbumHotViewHolder.imageViewAvatar.setImageBitmap(Utils.loadBitmapFromAssets(context, item.getAvatar(), "default_album_avatar"));

        albumAlbumHotViewHolder.itemView.setOnClickListener(v -> albumAlbumHotOnClickListener.onClickAtItem(position));
    }

    @Override
    public int getItemCount() {

        if (albumItems != null){
            return albumItems.size();
        }

        return 0;
    }

    class AlbumAlbumHotViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewAvatar;
        TextView textViewName;
        public AlbumAlbumHotViewHolder(@NonNull View itemView) {

            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewItemAlbumHotAvatar);
            textViewName = itemView.findViewById(R.id.textViewItemAlbumHotName);
        }
    }

    public interface AlbumAlbumHotOnClickListener {
        void onClickAtItem(int position);
    }
}
