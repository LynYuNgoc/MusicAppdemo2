package com.example.musicappdemo2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicappdemo2.AlbumSongActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumCategoryAdapter;
import com.example.musicappdemo2.classes.AlbumChillAdapter;
import com.example.musicappdemo2.classes.AlbumItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListHomePageChillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListHomePageChillFragment extends Fragment implements AlbumChillAdapter.AlbumChillOnClickListener{

    ArrayList<AlbumItem> albumItems;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListHomePageChillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListHomePageChillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListHomePageChillFragment newInstance(String param1, String param2) {
        ListHomePageChillFragment fragment = new ListHomePageChillFragment();
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

        View view = inflater.inflate(R.layout.fragment_list_home_page_chill, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewChill);
        AlbumChillAdapter albumChillAdapter = new AlbumChillAdapter(albumItems,getContext(), this);
        recyclerView.setAdapter(albumChillAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        return view;
    }

    void initSampleData() {
        albumItems = new ArrayList<AlbumItem>();
        albumItems.add(new AlbumItem("album015","Jazz", "jazz.jpg", "Jazz"));
        albumItems.add(new AlbumItem("album016","Acoustic", "Acoustic.jpg", "Acoustic"));
        albumItems.add(new AlbumItem("album017","Piano", "piano.jpg", "Piano"));
        albumItems.add(new AlbumItem("album018","Lofi", "lofibeats.jpg", "Lofi"));
    }

    @Override
    public void onClickAtItem(int position) {
        Intent i = new Intent(getActivity(), AlbumSongActivity.class);
        i.putExtra("ALBUM_ITEM_EXTRA_KEY_NAME",albumItems.get(position));
        startActivity(i);
    }
}