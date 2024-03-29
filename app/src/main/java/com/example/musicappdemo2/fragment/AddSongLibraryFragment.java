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
import android.widget.Button;
import android.widget.Toast;

import com.example.musicappdemo2.PlayMusicActivity;
import com.example.musicappdemo2.PlayMusicOnlineActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AddSongLibraryAdapter;
import com.example.musicappdemo2.classes.ListSongAdapter;
import com.example.musicappdemo2.classes.SongItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSongLibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSongLibraryFragment extends Fragment implements AddSongLibraryAdapter.ListSongOnClickListener, AddSongLibraryAdapter.OnButtonClickListener {

    ArrayList<SongItem> songItems;
    RecyclerView recyclerView;
    AddSongLibraryAdapter addSongLibraryAdapter;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSongLibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSongLibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSongLibraryFragment newInstance(String param1, String param2) {
        AddSongLibraryFragment fragment = new AddSongLibraryFragment();
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

//        getListAlbumFromFireStore();
        GetListFavouriteFromFireBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_song_library, container, false);

        songItems = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewAddSongLibrary);
        addSongLibraryAdapter = new AddSongLibraryAdapter(songItems, getContext(), this, this);
        recyclerView.setAdapter(addSongLibraryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }




    private void GetListFavouriteFromFireBase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Songs")
                .whereEqualTo("favorite", 1)
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
                                    favorite = ((Long) dataItem.get("favorite")).intValue();
                                }
                                if (dataItem.get("idAlbum") != null) {
                                    idAlbum = dataItem.get("idAlbum").toString();
                                }
                                if (dataItem.get("songMp3") != null) {
                                    songMp3 = dataItem.get("songMp3").toString();
                                }



                                SongItem item = new SongItem(idSong,nameSong,nameSinger,idAlbum,avatar,favorite,songMp3);
                                songItems.add(item);
                            }
                            addSongLibraryAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirebaseFirestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }






    @Override
    public void onClickAtItem(int position) {
        Intent intent = new Intent(getActivity(), PlayMusicOnlineActivity.class);
        intent.putExtra("SONG_ITEM_EXTRA_KEY_NAME",songItems.get(position));
        startActivity(intent);
    }

    @Override
    public void onButtonClick(int position) {
        // Xử lý sự kiện khi người dùng nhấn vào button trong một item của RecyclerView
        // cập nhật trên Cloud Firestore

        // cập nhật trường "favorite" của bài hát tại vị trí position
        SongItem clickedItem = songItems.get(position);
        clickedItem.setFavorite(1);

        // cập nhật trên Cloud Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Songs").document(clickedItem.getIdSong())
                .update("favorite", 0)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thành công
                        // Có thể thông báo cho người dùng
                        Toast.makeText(getContext(), "Bài hát đã được xóa khỏi thư viện", Toast.LENGTH_SHORT).show();
                        // Sau khi cập nhật xong, cập nhật lại dữ liệu trong RecyclerView
                        addSongLibraryAdapter.notifyItemChanged(position);

                        //reload lại màn hình

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi khi cập nhật
                        // Có thể xử lý lỗi ở đây
                        Log.e("ListSongFragment", "Error updating document", e);
                    }
                });
    }
}