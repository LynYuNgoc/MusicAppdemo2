package com.example.musicappdemo2.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.AlbumItem;
import com.example.musicappdemo2.classes.ProgressHelper;
import com.example.musicappdemo2.classes.SongItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtName, edtAvatar;
    private Button btnAddAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();


        btnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickAddAllAlbum();
                //FireStoreAddAllAlbum();


            }
        });

    }



    private void initUI() {
        edtId = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        btnAddAlbum = findViewById(R.id.btn_add_album);
        edtAvatar = findViewById(R.id.edt_avatar);
    }

    private void onClickAddAllAlbum() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("TheLoai");


        List<AlbumItem> albumItems = new ArrayList<>();

        albumItems.add(new AlbumItem("album001","Nhạc Việt", "nhac_viet.jpg"));
        albumItems.add(new AlbumItem("album002","Nhạc Hàn", "nhac_han.jpg"));
        albumItems.add(new AlbumItem("album003","Nhạc Trung", "nhac_trung.jpg"));
        albumItems.add(new AlbumItem("album004","Nhạc Âu Mỹ", "nhac_au_my.jpg"));
        albumItems.add(new AlbumItem("album005","Nhạc Nhật", "nhac_nhat.jpg"));





        DatabaseReference myRef2 = database.getReference("NgheSi");

        List<AlbumItem> albumItems2 = new ArrayList<>();
        albumItems2.add(new AlbumItem("album006","Sơn Tùng M-TP", "sontung1.jpg"));
        albumItems2.add(new AlbumItem("album007","Hoà Minzy", "hoa_minzy.jpg"));
        albumItems2.add(new AlbumItem("album008","Taylor Swift", "taylor.jpg"));
        albumItems2.add(new AlbumItem("album009","IU", "iu.jpg"));
        albumItems2.add(new AlbumItem("album010","BTS", "bts1.jpg"));



        DatabaseReference myRef3 = database.getReference("AlbumHot");

        List<AlbumItem> albumItems3 = new ArrayList<>();
        albumItems3.add(new AlbumItem("album011","Em Xinh", "emxinh1.jpg"));
        albumItems3.add(new AlbumItem("album012","Ditney Hits", "disney1.jpg"));
        albumItems3.add(new AlbumItem("album013","Peaceful guitar", "guitar.jpg"));
        albumItems3.add(new AlbumItem("album014","Vũ Trụ Cò Bay", "vutrucobay1.jpg"));




        DatabaseReference myRef4 = database.getReference("Chill");

        List<AlbumItem> albumItems4 = new ArrayList<>();
        albumItems4.add(new AlbumItem("album015","Jazz", "jazz.jpg"));
        albumItems4.add(new AlbumItem("album016","Acoustic", "Acoustic.jpg"));
        albumItems4.add(new AlbumItem("album017","Piano", "piano.jpg"));
        albumItems4.add(new AlbumItem("album018","Lofi", "lofibeats.jpg"));





        myRef1.setValue(albumItems, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Add Album success", Toast.LENGTH_SHORT).show();
            }
        });

        myRef2.setValue(albumItems2);
        myRef3.setValue(albumItems3);
        myRef4.setValue(albumItems4);


    }


    void FireStoreAddAllAlbum(){


        //    void firebaseRegisterNewUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        List<AlbumItem> albumItems = new ArrayList<>();

        albumItems.add(new AlbumItem("Nhạc Việt","Nhạc Việt", "nhac_viet.jpg"));
        albumItems.add(new AlbumItem("Nhạc Hàn","Nhạc Hàn", "nhac_han.jpg"));
        albumItems.add(new AlbumItem("Nhạc Trung","Nhạc Trung", "nhac_trung.jpg"));
        albumItems.add(new AlbumItem("Nhạc Âu Mỹ","Nhạc Âu Mỹ", "nhac_au_my.jpg"));

        albumItems.add(new AlbumItem("Sơn Tùng M-TP","Sơn Tùng M-TP", "sontung1.jpg"));
        albumItems.add(new AlbumItem("Hoà Minzy","Hoà Minzy", "hoa_minzy.jpg"));
        albumItems.add(new AlbumItem("IU","IU", "iu.jpg"));
        albumItems.add(new AlbumItem("BTS","BTS", "bts1.jpg"));

        albumItems.add(new AlbumItem("Em Xinh","Em Xinh", "emxinh1.jpg"));
        albumItems.add(new AlbumItem("Vũ Trụ Cò Bay","Vũ Trụ Cò Bay", "vutrucobay1.jpg"));

        albumItems.add(new AlbumItem("Jazz","Jazz", "jazz.jpg"));
        albumItems.add(new AlbumItem("Acoustic","Acoustic", "Acoustic.jpg"));
        albumItems.add(new AlbumItem("Lofi","Lofi", "lofibeats.jpg"));


// Add a new document with a generated ID
        db.collection("albumItems")
                .add(albumItems)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ProgressHelper.dismissDialog();
                        Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ProgressHelper.dismissDialog();
                        Log.w("MainActivity", "Error adding document", e);
                    }
                });
    }
}
