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

import com.example.musicappdemo2.PlayMusicActivity;
import com.example.musicappdemo2.PlayMusicOnlineActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.ListSongAdapter;
import com.example.musicappdemo2.classes.SongItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSongFragment extends Fragment implements ListSongAdapter.ListSongOnClickListener{

    ArrayList<SongItem> listSong;
    String albumName = "";
    ListSongAdapter listSongAdapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListSongFragment(String albumName) {
        // Required empty public constructor
        this.albumName = albumName;
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
        ListSongFragment fragment = new ListSongFragment("");
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
        getListAlbumFromFireStore();
    }

    private void getListAlbumFromFireStore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Songs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String,Object> dataItem = document.getData();
                                Log.d("FirebaseFirestore", document.getId() + " => " + document.getData());
                                String nameSong = "";
                                String idSong = "";
                                String nameSinger = "";
                                String avatar = "";
                                Integer favorite = 0;
                                String idAlbum = "";
                                String songMp3 ="";


                                    if (dataItem.get("nameSong") != null) {
                                        nameSong = dataItem.get("nameSong").toString();
                                }
                                if (dataItem.get("idSong") != null) {
                                    idSong = dataItem.get("idSong").toString();
                                }
                                if (dataItem.get("nameSinger") != null) {
                                    nameSinger = dataItem.get("nameSinger").toString();
                                }
                                if (dataItem.get("avatar") != null) {
                                    avatar = dataItem.get("avatar").toString();
                                }
                                if (dataItem.get("favorite") != null) {
//                                    favorite = (Integer) dataItem.get("favorite");
                                }
                                if (dataItem.get("idAlbum") != null) {
                                    idAlbum = dataItem.get("idAlbum").toString();
                                }
                                if (dataItem.get("songMp3") != null) {
                                    songMp3 = dataItem.get("songMp3").toString();
                                }



                                SongItem item = new SongItem(idSong,nameSong,nameSinger,idAlbum,avatar,0,songMp3);
                                if (item.getIdAlbum().equals(albumName)) {
                                    listSong.add(item);
                                }
                            }
                            listSongAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirebaseFirestore", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_song, container, false);
        // Inflate the layout for this fragment

        listSong = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewListSong);
        listSongAdapter = new ListSongAdapter(listSong, getContext(), this);
        recyclerView.setAdapter(listSongAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }




    @Override
    public void onClickAtItem(int position) {
        Intent intent = new Intent(getActivity(), PlayMusicOnlineActivity.class);
        intent.putExtra("SONG_ITEM_EXTRA_KEY_NAME",listSong.get(position));
        startActivity(intent);
    }

}