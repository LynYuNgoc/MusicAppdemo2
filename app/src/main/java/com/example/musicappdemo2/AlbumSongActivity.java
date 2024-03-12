package com.example.musicappdemo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.Utils;
import com.example.musicappdemo2.fragment.ListSongFragment;
import com.example.musicappdemo2.widget.MainActivity;

public class AlbumSongActivity extends AppCompatActivity {
    TextView textViewName;
    ImageView imageView;
    Toolbar toolbar;
    Button btPlayMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_song);
        setupBottomMenu();

        toolbar = findViewById(R.id.materialToolbarAlbumSong);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewName = findViewById(R.id.textViewAlbumName);
        imageView = findViewById(R.id.imageViewAlbum);


        Intent intent = getIntent();
        AlbumItem item = intent.getParcelableExtra("ALBUM_ITEM_EXTRA_KEY_NAME");

        textViewName.setText(item.getName());
        imageView.setImageBitmap(Utils.loadBitmapFromAssets(this,item.getAvatar(),"default_album_avatar"));



        btPlayMusic = findViewById(R.id.buttonPlaySong);
        btPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumSongActivity.this, PlayMusicActivity.class);
                startActivity(i);
            }
        });


    }

    void setupBottomMenu() {
        setFragment(new ListSongFragment());

    }

    void setFragment(Fragment newFragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.frameLayoutAlbumSong, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}