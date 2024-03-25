package com.example.musicappdemo2.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappdemo2.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterAdapter extends RecyclerView.Adapter implements Filterable {


    ArrayList<SongItem> searchFilterItems;
    ArrayList<SongItem> searchFilterItemsOld;
    Context context;
    ListSongOnClickListener listSongOnClickListener;

    OnButtonClickListener mListener;

    public SearchFilterAdapter(ArrayList<SongItem> searchFilterItems, Context context, ListSongOnClickListener listSongOnClickListener, OnButtonClickListener mListener ) {
        this.searchFilterItems = searchFilterItems;
        this.searchFilterItemsOld = searchFilterItems;
        this.context = context;
        this.listSongOnClickListener = listSongOnClickListener;
        this.mListener = mListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_search_filter, parent, false);
        SearchFilterAdapter.SearchFilterViewHolder viewHolder = new SearchFilterAdapter.SearchFilterViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SongItem searchItem = searchFilterItems.get(position);

        if (searchItem == null){
            return;
        }
        SearchFilterViewHolder searchFilterViewHolder = (SearchFilterViewHolder) holder;
        searchFilterViewHolder.textViewSongName.setText(searchItem.getNameSong());
        searchFilterViewHolder.textViewSingerName.setText(searchItem.getNameSinger());

        searchFilterViewHolder.imageViewSong.setImageBitmap(Utils.loadBitmapFromAssets(context, searchItem.getAvatar(), "default_album_avatar"));
        searchFilterViewHolder.itemView.setOnClickListener(v -> listSongOnClickListener.onClickAtItem(position));

        // Gán trình nghe sự kiện cho button trong item
        searchFilterViewHolder.btnAddSongfromSearchtoLibrary.setOnClickListener(v -> mListener.onButtonClick(position));

    }

    @Override
    public int getItemCount() {

        if(searchFilterItems != null){
            return searchFilterItems.size();
        }

        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    searchFilterItems = searchFilterItemsOld;
                }
                else {
                    ArrayList<SongItem> list = new ArrayList<>();

                    for (SongItem searchFilterItem: searchFilterItemsOld){
                        if (searchFilterItem.getNameSong().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(searchFilterItem);

                        }
                    }
                    searchFilterItems = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchFilterItems;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                searchFilterItems = (ArrayList<SongItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchFilterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSong;
        private TextView textViewSongName;
        private TextView textViewSingerName;

        //button them bai hat vao library
        Button btnAddSongfromSearchtoLibrary;


        public SearchFilterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSong = itemView.findViewById(R.id.imageViewAvatarAddSongLibrary);
            textViewSongName = itemView.findViewById(R.id.textViewSearchSongName);
            textViewSingerName = itemView.findViewById(R.id.textViewSearchSingerName);

            //button them bai hat vao library
            btnAddSongfromSearchtoLibrary = itemView.findViewById(R.id.addSongfromSearch);
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
