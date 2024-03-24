package com.example.musicappdemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.ListSongAdapter;
import com.example.musicappdemo2.classes.SongItem;
import com.example.musicappdemo2.classes.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class PlayMusicOnline2Activity extends AppCompatActivity {

    ArrayList<SongItem> listsong;
    SongItem item;
    Toolbar toolbarBack;
    ImageView play, prev, next, imageView;
    TextView songTitle;
    SeekBar mSeekBarTime, mSeekBarVol;
    static MediaPlayer mMediaPlayer;
    private Runnable runnable;
    private AudioManager mAudioManager;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);

        listsong = new ArrayList<>(); // Thêm dòng này để khởi tạo listsong
        getListAlbumFromFireStore();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        play = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        songTitle = findViewById(R.id.songTitle);
        imageView = findViewById(R.id.imageView);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);

//        Intent intent = getIntent();
//        item = intent.getParcelableExtra("SONG_ITEM_EXTRA_KEY_NAME");
//
//
//        songTitle.setText(item.getNameSong());
//        imageView.setImageBitmap(Utils.loadBitmapFromAssets(this,item.getAvatar(),"default_album_avatar"));
//
//        //check bai hat = IdSong va lay bai hat tu thu muc raw de phat nhac
//        int resID=getResources().getIdentifier(item.getIdSong(), "raw", getPackageName());
//        mMediaPlayer=MediaPlayer.create(this,resID);


        toolbarBack = findViewById(R.id.toolbarBackListSong);
        setSupportActionBar(toolbarBack);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //seekBar volume
        int maxV = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSeekBarVol.setMax(maxV);
        mSeekBarVol.setProgress(curV);
        mSeekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.play_white_button_icon);
                } else {
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.pause_white_button_icon);
                }
                SongNames();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null) {
                    play.setImageResource(R.drawable.pause_white_button_icon);
                }
                if (!listsong.isEmpty()) {
                    currentIndex = (currentIndex + 1) % listsong.size();
                    playSong();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null) {
                    play.setImageResource(R.drawable.pause_white_button_icon);
                }
                if (!listsong.isEmpty()) {
                    currentIndex = (currentIndex - 1 + listsong.size()) % listsong.size();
                    playSong();
                }
            }
        });

    }

    private void playSong() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        item = listsong.get(currentIndex);
        songTitle.setText(item.getNameSong());
        imageView.setImageBitmap(Utils.loadBitmapFromAssets(this, item.getAvatar(), "default_album_avatar"));

        int resID = getResources().getIdentifier(item.getIdSong(), "raw", getPackageName());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
        mMediaPlayer.start();
        SongNames();
    }

    private void SongNames() {

        //seekBar duration(khoang thoi gian)
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mSeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer != null) {
                    try {
                        if (mMediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            //handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    //@SuppressLint("Handler Leak") Handler handler = new Handler()


    void getListAlbumFromFireStore() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Songs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> dataItem = document.getData();
                                Log.d("FirebaseFirestore", document.getId() + " => " + document.getData());
                                String nameSong = "";
                                String idSong = "";
                                String nameSinger = "";
                                String avatar = "";
                                Integer favorite = 0;
                                String idAlbum = "";
                                String songMp3 = "";


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


                                item = new SongItem(idSong, nameSong, nameSinger, idAlbum, avatar, favorite, songMp3);
                                listsong.add(item);
                            }
                            if (!listsong.isEmpty()) {
                                playSong();
                            }
                        } else {
                            Log.d("FirebaseFirestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}