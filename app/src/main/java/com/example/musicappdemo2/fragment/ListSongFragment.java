package com.example.musicappdemo2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicappdemo2.PlayMusicActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.ListSongAdapter;
import com.example.musicappdemo2.classes.SongItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSongFragment extends Fragment implements ListSongAdapter.ListSongOnClickListener{

    ArrayList<SongItem> listSong;

    ArrayList<AlbumItem> listSongAlbum;
    ListSongAdapter listSongAdapter;
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
        //initSampleData();
        getListSongFromRealTimeDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_song, container, false);
        // Inflate the layout for this fragment

        listSongAlbum = new ArrayList<>();
        listSong = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewListSong);
        listSongAdapter = new ListSongAdapter(listSong, getContext(), this);
        recyclerView.setAdapter(listSongAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }




    @Override
    public void onClickAtItem(int position) {
        Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
        intent.putExtra("SONG_ITEM_EXTRA_KEY_NAME",listSong.get(position));
        startActivity(intent);
    }








    private void getListSongFromRealTimeDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("listSong");
        DatabaseReference alRef = database.getReference("NgheSi");

        //Cach 1: add all list vao recyclerview

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSong.clear();

                // Loop through each child in the "listSong" node
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Retrieve the idAlbum value from the current child in "listSong"
                    String songIdAlbum = dataSnapshot.child("idAlbum").getValue(String.class);

                    // Now, you need to compare this value with the id value in "NgheSi"
                    Query ngheSiQuery = alRef.orderByChild("id").equalTo(songIdAlbum);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Add List Album failed", Toast.LENGTH_SHORT).show();
            }
        });








        //Cach 2:

//        DatabaseReference alRef = database.getReference("NgheSi");
//        DatabaseReference alRef1 = alRef.child("0");
//        DatabaseReference alRef2 = alRef1.child("listsong");
//        alRef2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listSong.clear();
//
//                // Loop through each child in the "listSong" node
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    // Retrieve the idAlbum value from the current child in "listSong"
//                    String songIdAlbum = dataSnapshot.child("idAlbum").getValue(String.class);
//
//                    // Construct a query to find a matching id in "NgheSi"
//                    Query ngheSiQuery = alRef.orderByChild("id").equalTo(songIdAlbum);
//
//                    ngheSiQuery.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot ngheSiSnapshot) {
//                            // Check if any matching record is found
//                            if (ngheSiSnapshot.exists()) {
//                                // If a match is found, add the SongItem to the list
//                                SongItem songItem = dataSnapshot.getValue(SongItem.class);
//                                listSong.add(songItem);
//                            }
//
//                            // Notify the adapter after the query is completed
//                            listSongAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(getContext(), "Error fetching NgheSi data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Add List Album failed", Toast.LENGTH_SHORT).show();
//            }
//        });





        //Cach 3:
//        alRef2.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String songIdAlbum = snapshot.child("idAlbum").getValue(String.class);
//
//                // Construct a query to find a matching id in "NgheSi"
//                Query ngheSiQuery = alRef.orderByChild("id").equalTo(songIdAlbum);
//
//                    if (ngheSiQuery != null){
//                        SongItem songItem = snapshot.getValue(SongItem.class);
//                        listSong.add(songItem);
//                    }
//
//                ngheSiQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ngheSiSnapshot) {
//                        // Check if any matching record is found
//                        if (ngheSiSnapshot.exists()) {
//                            // If a match is found, add the SongItem to the list
//                            SongItem songItem = ngheSiSnapshot.getValue(SongItem.class);
//                            listSong.add(songItem);
//                        }
//
//                        // Notify the adapter after the query is completed
//                        listSongAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), "Error fetching NgheSi data", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });










        //Cach 4:
//        myRef.child("listSong").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                String songIdAlbum = dataSnapshot.child("idAlbum").getValue(String.class);
//
//                alRef.child("NgheSi").orderByChild("id").equalTo(songIdAlbum).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ngheSiSnapshot) {
//                        if (ngheSiSnapshot.exists()) {
//                            SongItem songItem = dataSnapshot.getValue(SongItem.class);
//                            listSong.add(songItem);
//                            listSongAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), "Error fetching NgheSi data", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            // Other necessary overrides (onChildChanged, onChildRemoved, onChildMoved) can be implemented based on your requirements
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                // Handle changes if needed
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                // Handle removals if needed
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
//                // Handle movements if needed
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Add List Album failed", Toast.LENGTH_SHORT).show();
//            }
//        });





        //Cach 5:
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference alRef = database.getReference("listSong");
//        alRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                SongItem albumItem = snapshot.getValue(SongItem.class);
//                if (albumItem != null){
//                    listSong.add(albumItem);
//                    listSongAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        //Cach 6:
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference alRef = database.getReference("NgheSi");
//
//        alRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
//                    Log.i(TAG, zoneSnapshot.child("ZNAME").getValue(String.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "onCancelled", databaseError.toException());
//            }
//        });


    }

}