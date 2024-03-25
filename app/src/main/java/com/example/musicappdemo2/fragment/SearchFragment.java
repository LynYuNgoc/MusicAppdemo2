package com.example.musicappdemo2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicappdemo2.HomeActivity;
import com.example.musicappdemo2.PlayMusicActivity;
import com.example.musicappdemo2.PlayMusicOnlineActivity;
import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.SearchFilterAdapter;
import com.example.musicappdemo2.classes.SearchFilterItem;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment implements SearchFilterAdapter.ListSongOnClickListener, SearchFilterAdapter.OnButtonClickListener{

    private MenuItem menuItem;
    private SearchView searchView;
    Toolbar toolbar;
    SearchFilterAdapter searchFilterAdapter;
    RecyclerView recyclerView;
    ArrayList<SongItem> searchFilterItems;
    private HomeActivity homeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //menu

        homeActivity = (HomeActivity) getActivity();
        //homeActivity.hideToolbar(); // Ẩn thanh toolbar khi Fragment được tạo
        //onDestroy();
        getListAlbumFromFireStore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.materialToolbarSearch);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        searchFilterItems = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewSearchSong);
        searchFilterAdapter = new SearchFilterAdapter(searchFilterItems, getContext(), this, this);

        recyclerView.setAdapter(searchFilterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeActivity.showToolbar(); // Hiển thị lại thanh toolbar khi Fragment bị hủy
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        //searchView.setIconifiedByDefault(true);



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

    @Override
    public void onClickAtItem(int position) {
        Intent intent = new Intent(getActivity(), PlayMusicOnlineActivity.class);
        intent.putExtra("SONG_ITEM_EXTRA_KEY_NAME",searchFilterItems.get(position));

        startActivity(intent);
    }




    @Override
    public void onButtonClick(int position) {

        // Xử lý sự kiện khi người dùng nhấn vào button trong một item của RecyclerView
        // cập nhật trên Cloud Firestore

        // cập nhật trường "favorite" của bài hát tại vị trí position
        SongItem clickedItem = searchFilterItems.get(position);
        clickedItem.setFavorite(0);

        // cập nhật trên Cloud Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Songs").document(clickedItem.getIdSong())
                .update("favorite", 1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thành công
                        // Có thể thông báo cho người dùng
                        Toast.makeText(getContext(), "Bài hát đã được thêm vào thư viện", Toast.LENGTH_SHORT).show();
                        // Sau khi cập nhật xong, cập nhật lại dữ liệu trong RecyclerView
                        searchFilterAdapter.notifyItemChanged(position);
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
                                    favorite = ((Long) dataItem.get("favorite")).intValue();
                                }
                                if (dataItem.get("idAlbum") != null) {
                                    idAlbum = dataItem.get("idAlbum").toString();
                                }
                                if (dataItem.get("songMp3") != null) {
                                    songMp3 = dataItem.get("songMp3").toString();
                                }



                                SongItem item = new SongItem(idSong,nameSong,nameSinger,idAlbum,avatar,favorite,songMp3);
                                searchFilterItems.add(item);
                            }
                            searchFilterAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("FirebaseFirestore", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }



}