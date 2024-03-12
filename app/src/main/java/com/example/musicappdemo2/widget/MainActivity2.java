package com.example.musicappdemo2.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.SongItem;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    private EditText edtIdSong, edtNameSong, edtAvatar, edtSinger, edtIdAlbum, edtFavourite, edtSongMp3;
    private Button btnAddSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initUI();
        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickAddAllSong();

            }
        });
    }

    private void initUI() {
        edtIdSong = findViewById(R.id.edt_id);
        edtNameSong = findViewById(R.id.edt_name);
        edtAvatar = findViewById(R.id.edt_avatar);
        edtSinger = findViewById(R.id.edt_avatar);
        edtIdAlbum = findViewById(R.id.edt_avatar);
        edtFavourite = findViewById(R.id.edt_avatar);
        edtSongMp3 = findViewById(R.id.edt_avatar);


        btnAddSong = findViewById(R.id.btn_add_album);

    }

    private void onClickAddAllSong() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("listSong");

        List<SongItem> listSong = new ArrayList<>();

        listSong.add(new SongItem("1", "Chắc Ai Đó Sẽ Về", "Sơn Tùng M-TP", "album006", "song1.jpg", 0, "chacaidoseve.mp3"));
        listSong.add(new SongItem("2", "Nắng Ấm Xa Dần", "Sơn Tùng M-TP", "album006", "song1.jpg", 0, "nangamxadan.mp3"));
        listSong.add(new SongItem("3", "Âm Thầm Bên Em", "Sơn Tùng M-TP", "album006", "song1.jpg", 0, "amthambenem.mp3"));
        listSong.add(new SongItem("4", "Nơi Này Có Anh", "Sơn Tùng M-TP", "album006", "song1.jpg", 0, "noinaycoanh.mp3"));

        listSong.add(new SongItem("5", "Thị Mầu", "Hòa Minzy", "album007", "song2.jpg", 0, "thimau.mp3"));
        listSong.add(new SongItem("6", "Rời Bỏ", "Hòa Minzy", "album007", "song2.jpg", 0, "roibo.mp3"));
        listSong.add(new SongItem("7", "Bật Tình Yêu Lên", "Hòa Minzy", "album007", "song2.jpg", 0, "battinhyeulen.mp3"));
        listSong.add(new SongItem("8", "Chấp Nhận", "Hòa Minzy", "album007", "song2.jpg", 0, "chapnhan.mp3"));

        listSong.add(new SongItem("9", "Lover", "Taylor Swift", "album008", "song3.jpg", 0, "lover.mp3"));
        listSong.add(new SongItem("10", "You Belong With Me", "Taylor Swift", "album008", "song3.jpg", 0, "youbelongwithme.mp3"));
        listSong.add(new SongItem("11", "Blank Space", "Taylor Swift", "album008", "song3.jpg", 0, "blankspace.mp3"));

        listSong.add(new SongItem("12", "Blueming", "IU", "album009", "song4.jpg", 0, "blueming.mp3"));
        listSong.add(new SongItem("13", "Love Wins All", "IU", "album009", "song4.jpg", 0, "lovewinsall.mp3"));
        listSong.add(new SongItem("14", "Good Day", "IU", "album009", "song4.jpg", 0, "goodday.mp3"));

        listSong.add(new SongItem("15", "Fire", "BTS", "album010", "song4.jpg", 0, "fire.mp3"));
        listSong.add(new SongItem("16", "Dynamite", "BTS", "album010", "song4.jpg", 0, "dynamite.mp3"));
        listSong.add(new SongItem("17", "Fake Love", "BTS", "album010", "song4.jpg", 0, "fakelove.mp3"));

        myRef.setValue(listSong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity2.this, "Add Album success", Toast.LENGTH_SHORT).show();
            }
        });


    }
}