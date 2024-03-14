package com.example.musicappdemo2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicappdemo2.AlbumSongActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumArtistAdapter;
import com.example.musicappdemo2.classes.AlbumCategoryAdapter;
import com.example.musicappdemo2.classes.AlbumItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListHomePageArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListHomePageArtistFragment extends Fragment implements AlbumArtistAdapter.AlbumArtistOnClickListener {

    ArrayList<AlbumItem> albumItems;
    AlbumArtistAdapter albumArtistAdapter;
    RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListHomePageArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListHomePageArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListHomePageArtistFragment newInstance(String param1, String param2) {
        ListHomePageArtistFragment fragment = new ListHomePageArtistFragment();
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
//        initSampleData();
        //getListAlbumFromRealTimeDatabase();
        getListAlbumFromFireStore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_home_page_artist, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewArtist);

        albumItems = new ArrayList<>();
        albumArtistAdapter = new AlbumArtistAdapter(albumItems, getContext(), this);
        recyclerView.setAdapter(albumArtistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

//    void initSampleData() {
//        albumItems = new ArrayList<AlbumItem>();
//        albumItems.add(new AlbumItem("album006","Sơn Tùng M-TP", "sontung1.jpg"));
//        albumItems.add(new AlbumItem("album007","Hoà Minzy", "hoa_minzy.jpg"));
//        albumItems.add(new AlbumItem("album008","Taylor Swift", "taylor.jpg"));
//        albumItems.add(new AlbumItem("album009","IU", "iu.jpg"));
//        albumItems.add(new AlbumItem("album010","BTS", "bts1.jpg"));
//    }

    @Override
    public void onClickAtItem(int position) {
        Intent i = new Intent(getActivity(), AlbumSongActivity.class);
        i.putExtra("ALBUM_ITEM_EXTRA_KEY_NAME", albumItems.get(position));
        startActivity(i);
    }

    private void getListAlbumFromRealTimeDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NgheSi");


        //Cach 1: add all list vao recyclerview
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                albumItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AlbumItem albumItem = dataSnapshot.getValue(AlbumItem.class);
                    albumItems.add(albumItem);


                }
                albumArtistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Add List Album failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getListAlbumFromFireStore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("NgheSi").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            Log.e("FireStore error", error.getMessage());
                            return;

                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                albumItems.add(dc.getDocument().toObject(AlbumItem.class));
                            }
                            albumArtistAdapter.notifyDataSetChanged();

                        }
                    }
                });


    }
}