package com.example.musicappdemo2.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongItem implements Parcelable {

    String idSong;
    String nameSong;
    String nameSinger;
    String idAlbum;
    String avatar;
    Integer favorite;           //1 true; 0 false
    String songMp3;

    public SongItem(String idSong, String nameSong, String nameSinger, String idAlbum, String avatar, Integer favorite, String songMp3) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.nameSinger = nameSinger;
        this.idAlbum = idAlbum;
        this.avatar = avatar;
        this.favorite = favorite;
        this.songMp3 = songMp3;
    }

    protected SongItem(Parcel in) {
        idSong = in.readString();
        nameSong = in.readString();
        nameSinger = in.readString();
        idAlbum = in.readString();
        avatar = in.readString();
        if (in.readByte() == 0) {
            favorite = null;
        } else {
            favorite = in.readInt();
        }
        songMp3 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSong);
        dest.writeString(nameSong);
        dest.writeString(nameSinger);
        dest.writeString(idAlbum);
        dest.writeString(avatar);
        if (favorite == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(favorite);
        }
        dest.writeString(songMp3);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public String getSongMp3() {
        return songMp3;
    }

    public void setSongMp3(String songMp3) {
        this.songMp3 = songMp3;
    }

    public SongItem() {
    }

    @Override
    public String toString() {
        return "SongItem{" +
                "idSong='" + idSong + '\'' +
                ", nameSong='" + nameSong + '\'' +
                ", nameSinger='" + nameSinger + '\'' +
                ", idAlbum='" + idAlbum + '\'' +
                ", avatar='" + avatar + '\'' +
                ", favorite=" + favorite +
                ", songMp3='" + songMp3 + '\'' +
                '}';
    }
}
