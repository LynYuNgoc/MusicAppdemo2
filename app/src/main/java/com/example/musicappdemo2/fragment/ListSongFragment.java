package com.example.musicappdemo2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.ListSongAdapter;
import com.example.musicappdemo2.classes.SongItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSongFragment extends Fragment {

    ArrayList<SongItem> songItems;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListSongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListSongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListSongFragment newInstance(String param1, String param2) {
        ListSongFragment fragment = new ListSongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initSampleData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_song, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerViewListSong);
        ListSongAdapter listSongAdapter = new ListSongAdapter(songItems, getContext());
        recyclerView.setAdapter(listSongAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    void initSampleData() {
        songItems = new ArrayList<SongItem>();
        songItems.add(new SongItem("song001","song001", "singer001.jpg", "singer001", "song01.jpg"));
        songItems.add(new SongItem("song002","song002", "singer001.jpg", "singer001", "song02.jpg"));
        songItems.add(new SongItem("song003","song003", "singer001.jpg", "singer001", "song03.jpg"));
        songItems.add(new SongItem("song004","song004", "singer001.jpg", "singer001", "song04.jpg"));
        songItems.add(new SongItem("song005","song005", "singer001.jpg", "singer001", "song05.jpg"));
        songItems.add(new SongItem("song006","song006", "singer001.jpg", "singer001", "song06.jpg"));
        songItems.add(new SongItem("song007","song007", "singer001.jpg", "singer001", "song07.jpg"));
        songItems.add(new SongItem("song008","song008", "singer001.jpg", "singer001", "song08.jpg"));
        songItems.add(new SongItem("song009","song009", "singer001.jpg", "singer001", "song09.jpg"));
        songItems.add(new SongItem("song010","song010", "singer001.jpg", "singer001", "song10.jpg"));

    }
}