package com.example.musicappdemo2.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    List<SongItem> searchFilterItems;
    List<SongItem> searchFilterItemsOld;

    public SearchFilterAdapter(List<SongItem> searchFilterItems, Context context, ListSongOnClickListener listSongOnClickListener ) {
        this.searchFilterItems = searchFilterItems;
        this.searchFilterItemsOld = searchFilterItems;
        this.context = context;
        this.listSongOnClickListener = listSongOnClickListener;
    }

    Context context;
    ListSongOnClickListener listSongOnClickListener;

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
                    List<SongItem> list = new ArrayList<>();

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

                searchFilterItems = (List<SongItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchFilterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSong;
        private TextView textViewSongName;
        private TextView textViewSingerName;


        public SearchFilterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSong = itemView.findViewById(R.id.imageViewAvatarAddSongLibrary);
            textViewSongName = itemView.findViewById(R.id.textViewSearchSongName);
            textViewSingerName = itemView.findViewById(R.id.textViewSearchSingerName);
        }
    }



    public  interface ListSongOnClickListener{
        void onClickAtItem(int position);
    }
}
