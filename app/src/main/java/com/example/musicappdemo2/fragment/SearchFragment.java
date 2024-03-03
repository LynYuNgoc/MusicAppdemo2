package com.example.musicappdemo2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.SearchFilterAdapter;
import com.example.musicappdemo2.classes.SearchFilterItem;
import com.example.musicappdemo2.classes.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    List<SearchFilterItem> searchFilterItems;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initSampleData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearchSong);
        SearchFilterAdapter searchFilterAdapter = new SearchFilterAdapter(searchFilterItems,getContext());
        recyclerView.setAdapter(searchFilterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    void initSampleData() {
        searchFilterItems = new ArrayList<SearchFilterItem>();
        searchFilterItems.add(new SearchFilterItem("song001","song001", "singer001.jpg", "singer001", "song01.jpg"));
        searchFilterItems.add(new SearchFilterItem("song002","song002", "singer001.jpg", "singer001", "song02.jpg"));
        searchFilterItems.add(new SearchFilterItem("song003","song003", "singer001.jpg", "singer001", "song03.jpg"));
        searchFilterItems.add(new SearchFilterItem("song004","song004", "singer001.jpg", "singer001", "song04.jpg"));
        searchFilterItems.add(new SearchFilterItem("song005","song005", "singer001.jpg", "singer001", "song05.jpg"));
        searchFilterItems.add(new SearchFilterItem("song006","song006", "singer001.jpg", "singer001", "song06.jpg"));
        searchFilterItems.add(new SearchFilterItem("song007","song007", "singer001.jpg", "singer001", "song07.jpg"));
        searchFilterItems.add(new SearchFilterItem("song008","song008", "singer001.jpg", "singer001", "song08.jpg"));
        searchFilterItems.add(new SearchFilterItem("song009","song009", "singer001.jpg", "singer001", "song09.jpg"));
        searchFilterItems.add(new SearchFilterItem("song010","song010", "singer001.jpg", "singer001", "song10.jpg"));

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }
}