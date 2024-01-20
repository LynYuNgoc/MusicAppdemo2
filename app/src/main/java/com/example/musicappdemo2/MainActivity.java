package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        play = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        songTitle = findViewById(R.id.songTitle);
        imageView = findViewById(R.id.imageView);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);


        //creating an ArrayList to store songs
        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0,R.raw.sound1);
        songs.add(0,R.raw.sound2);
        songs.add(0,R.raw.sound3);
        songs.add(0,R.raw.sound4);

        //intializing Mediaplayer
        mMediaPlayer = MediaPlayer.create(getApplicationContext(),songs.get(currentIndex));

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.play_btn);
                }else {
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.pause_btn);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null){
                    play.setImageResource(R.drawable.pause_btn);
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
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null){
                    play.setImageResource(R.drawable.pause_btn);
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
    }
}