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

public class AlbumCategoryAdapter extends RecyclerView.Adapter {
    ArrayList<AlbumItem> albumItems;

    public AlbumCategoryAdapter(ArrayList<AlbumItem> albumItems, Context context, AlbumCategoryOnClickListener albumCategoryOnClickListener) {
        this.albumItems = albumItems;
        this.context = context;
        this.albumCategoryOnClickListener = albumCategoryOnClickListener;
    }

    Context context;
    AlbumCategoryOnClickListener albumCategoryOnClickListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_album_category, parent, false);
        AlbumCategoryViewHolder viewHolder = new AlbumCategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumItem item = albumItems.get(position);
        AlbumCategoryViewHolder albumCategoryViewHolder = (AlbumCategoryViewHolder) holder;
        albumCategoryViewHolder.textViewName.setText(item.getName());

        //avatar
        albumCategoryViewHolder.imageViewAvatar.setImageBitmap(Utils.loadBitmapFromAssets(context, item.getAvatar(), "default_album_avatar"));
        albumCategoryViewHolder.itemView.setOnClickListener(v -> albumCategoryOnClickListener.onClickAtItem(position));
    }

    @Override
    public int getItemCount() {

        if (albumItems != null){
            return albumItems.size();
        }
        return 0;
    }

    class AlbumCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName;
        public AlbumCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewItemCategoryAvatar);
            textViewName = itemView.findViewById(R.id.textViewItemCategoryName);
        }
    }

    public interface AlbumCategoryOnClickListener {
        void onClickAtItem(int position);
    }
}
