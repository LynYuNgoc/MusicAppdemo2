package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
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


        //creating an ArrayList to store songs
        final ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0,R.raw.sound1);
        songs.add(1,R.raw.sound2);
        songs.add(2,R.raw.sound3);
        songs.add(3,R.raw.sound4);
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

        //button login
        Button login = (Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayMusicActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void SongNames(){
        if(currentIndex == 0){
            songTitle.setText("Anh Mo - Hoang Dung");
            imageView.setImageResource(R.drawable.anhmo);
        }
        if(currentIndex == 1){
            songTitle.setText("Tan Bien - Quan AP");
            imageView.setImageResource(R.drawable.tanbien);
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