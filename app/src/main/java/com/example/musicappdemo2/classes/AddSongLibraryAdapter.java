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

public class AddSongLibraryAdapter extends RecyclerView.Adapter{

    ArrayList<SongItem> songItems;
    Context context;
    ListSongOnClickListener listSongOnClickListener;
    OnButtonClickListener mListener;

    public AddSongLibraryAdapter(ArrayList<SongItem> songItems, Context context, ListSongOnClickListener listSongOnClickListener, OnButtonClickListener mListener) {
        this.songItems = songItems;
        this.context = context;
        this.listSongOnClickListener = listSongOnClickListener;
        this.mListener = mListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_add_song_library, parent, false);
        AddSongLibraryViewHolder viewHolder = new AddSongLibraryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SongItem item = songItems.get(position);
        AddSongLibraryViewHolder addSongLibraryViewHolder = (AddSongLibraryViewHolder) holder;
        addSongLibraryViewHolder.textViewSongName.setText(item.getNameSong());
        addSongLibraryViewHolder.textViewSingerName.setText(item.getNameSinger());

        //avatar
        addSongLibraryViewHolder.imageViewAvatar.setImageBitmap(Utils.loadBitmapFromAssets(context, item.getAvatar(), "default_album_avatar"));
        addSongLibraryViewHolder.itemView.setOnClickListener(v -> listSongOnClickListener.onClickAtItem(position));

        // Gán trình nghe sự kiện cho button trong item
        addSongLibraryViewHolder.btndeleteSonginLibrary.setOnClickListener(v -> mListener.onButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return songItems.size();
    }

    class AddSongLibraryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar;
        private TextView textViewSongName;
        private TextView textViewSingerName;

        //Xoa bai hat library
        private Button btndeleteSonginLibrary;



        public AddSongLibraryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatarAddSongLibrary);
            textViewSongName = itemView.findViewById(R.id.textViewNameAddSongLibrary);
            textViewSingerName = itemView.findViewById(R.id.textViewSingerNameAddSongLibrary);

            //Xoa bai hat library
            btndeleteSonginLibrary = itemView.findViewById(R.id.deleteSongLibrary);
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
