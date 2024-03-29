package com.example.musicappdemo2.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappdemo2.R;

import java.util.ArrayList;

public class ListSongAdapter extends RecyclerView.Adapter{

    ArrayList<SongItem> songItems;
    Context context;
    ListSongOnClickListener listSongOnClickListener;

    OnButtonClickListener mListener;


    public ListSongAdapter(ArrayList<SongItem> songItems, Context context, ListSongOnClickListener listSongOnClickListener, OnButtonClickListener mListener) {
        this.songItems = songItems;
        this.context = context;
        this.listSongOnClickListener = listSongOnClickListener;
        this.mListener = mListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_list_song, parent, false);
        ListSongViewHolder viewHolder = new ListSongViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SongItem item = songItems.get(position);
        ListSongViewHolder listSongViewHolder = (ListSongViewHolder) holder;
        listSongViewHolder.textViewSongName.setText(item.getNameSong());
        listSongViewHolder.textViewSingerName.setText(item.getNameSinger());

        //avatar
        listSongViewHolder.imageViewAvatar.setImageBitmap(Utils.loadBitmapFromAssets(context, item.getAvatar(), "default_album_avatar"));
        listSongViewHolder.itemView.setOnClickListener(v -> listSongOnClickListener.onClickAtItem(position));



        // Gán trình nghe sự kiện cho button trong item
        listSongViewHolder.btnAddSongfromListSongtoLibrary.setOnClickListener(v -> mListener.onButtonClick(position));
    }

    @Override
    public int getItemCount() {

        if (songItems != null){
            return songItems.size();
        }

        return 0;
    }

    class ListSongViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewAvatar;
        TextView textViewSongName;
        TextView textViewSingerName;

        //button them bai hat vao library
        Button btnAddSongfromListSongtoLibrary;

        public ListSongViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatarSong);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            textViewSingerName = itemView.findViewById(R.id.textViewSingerName);

            //button them bai hat vao library
            btnAddSongfromListSongtoLibrary = itemView.findViewById(R.id.addSongfromListSong);
        }
    }

    public  interface ListSongOnClickListener{
        void onClickAtItem(int position);
    }



    // Định nghĩa interface cho trình nghe sự kiện
    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
}
