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

public class PlayMusicActivity extends AppCompatActivity {

    String songMp3;

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

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        play = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        songTitle = findViewById(R.id.songTitle);
        imageView = findViewById(R.id.imageView);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);

        Intent intent = getIntent();
        item = intent.getParcelableExtra("SONG_ITEM_EXTRA_KEY_NAME");


        songTitle.setText(item.getNameSong());
        imageView.setImageBitmap(Utils.loadBitmapFromAssets(this,item.getAvatar(),"default_album_avatar"));

        songMp3 = item.getSongMp3();

        toolbarBack = findViewById(R.id.toolbarBackListSong);
        setSupportActionBar(toolbarBack);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //creating an ArrayList to store songs
        final ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0,R.raw.battinhyeulen);
        songs.add(1,R.raw.roibo);
        songs.add(2,R.raw.noinaycoanh);
        songs.add(3,R.raw.chacaidoseve);
        songs.add(4,R.raw.tungcautungchu);
        songs.add(5,R.raw.chodoicodangso);









        //intializing Mediaplayer
        mMediaPlayer = MediaPlayer.create(getApplicationContext(),songs.get(currentIndex));


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
                if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.pause_icon);
                }else {
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.pause_icon);
                }
                SongNames();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null){
                    play.setImageResource(R.drawable.pause_icon);
                }
                if(currentIndex < songs.size() - 1){
                    currentIndex++;
                }else{
                    currentIndex = 0;
                }
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                mMediaPlayer = mMediaPlayer.create(getApplicationContext(),songs.get(currentIndex));
                mMediaPlayer.start();
                SongNames();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null){
                    play.setImageResource(R.drawable.pause_icon);
                }
                if(currentIndex > 0){
                    currentIndex--;
                }else{
                    currentIndex = songs.size() - 1;
                }
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = mMediaPlayer.create(getApplicationContext(),songs.get(currentIndex));
                mMediaPlayer.start();
                SongNames();
            }
        });




    }

    private void SongNames(){
        if(currentIndex == 0){
            songTitle.setText("Bật Tình Yêu Lên");
            imageView.setImageResource(R.drawable.hoa_minzy);
        }
        if(currentIndex == 1){
            songTitle.setText("Rời Bỏ");
            imageView.setImageResource(R.drawable.hoa_minzy);
        }
        if(currentIndex == 2){
            songTitle.setText("Lover - Nhac Trung");
            imageView.setImageResource(R.drawable.lover);
        }
        if(currentIndex == 3){
            songTitle.setText("Wo Ai Ta - Nhac Trung");
            imageView.setImageResource(R.drawable.woaita);
        }
        if(currentIndex == 4){
            songTitle.setText("Tung Cau Tung Chu - Nhac Trung");
            imageView.setImageResource(R.drawable.tungcautungchu);
        }
        if(currentIndex == 5){
            songTitle.setText("Cho Doi Co Dang So");
            imageView.setImageResource(R.drawable.chodoi);
        }

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
                if(fromUser){
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
                while (mMediaPlayer != null){
                    try{
                        if(mMediaPlayer.isPlaying()){
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            //handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    //@SuppressLint("Handler Leak") Handler handler = new Handler()

}