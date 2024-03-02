package com.example.musicappdemo2.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongItem implements Parcelable {

    String idSong;
    String nameSong;

    String idSinger;
    String nameSinger;

    String avatar;

    public SongItem(String idSong, String nameSong, String idSinger, String nameSinger, String avatar) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.idSinger = idSinger;
        this.nameSinger = nameSinger;
        this.avatar = avatar;
    }

    protected SongItem(Parcel in) {
        idSong = in.readString();
        nameSong = in.readString();
        idSinger = in.readString();
        nameSinger = in.readString();
        avatar = in.readString();
    }

    public static final Creator<SongItem> CREATOR = new Creator<SongItem>() {
        @Override
        public SongItem createFromParcel(Parcel in) {
            return new SongItem(in);
        }

        @Override
        public SongItem[] newArray(int size) {
            return new SongItem[size];
        }
    };

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(String idSinger) {
        this.idSinger = idSinger;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idSong);
        dest.writeString(nameSong);
        dest.writeString(idSinger);
        dest.writeString(nameSinger);
        dest.writeString(avatar);
    }
}
