package com.example.musicappdemo2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicappdemo2.MainActivity;
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

    private MenuItem menuItem;
    private SearchView searchView;

    Toolbar toolbar;
    SearchFilterAdapter searchFilterAdapter;
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

        setHasOptionsMenu(true); //menu

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
        toolbar = view.findViewById(R.id.materialToolbarSearch);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");


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
        searchFilterItems.add(new SearchFilterItem("id 072019","id 072019", "singer001.jpg", "W/N", "song1.jpg"));
        searchFilterItems.add(new SearchFilterItem("song002","Chúng ta rồi sẽ hạnh phúc", "singer001.jpg", "singer001", "song2.jpg"));
        searchFilterItems.add(new SearchFilterItem("song003","Có hẹn với thanh xuân", "singer001.jpg", "singer001", "song3.jpg"));
        searchFilterItems.add(new SearchFilterItem("song004","Một Bước Yêu Vạn Dặm Đau", "singer001.jpg", "singer001", "song4.jpg"));
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

        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        //searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(true);
        //searchView.setIconifiedByDefault(true);


        //MenuItemCompat.expandActionView(menu.findItem(R.id.action_search)); //fix bug nhap chu
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFilterAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilterAdapter.getFilter().filter(newText);
                return true;
            }
        });


    }
}