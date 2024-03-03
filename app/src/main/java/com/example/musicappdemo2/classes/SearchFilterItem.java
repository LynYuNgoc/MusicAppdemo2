package com.example.musicappdemo2.classes;

public class SearchFilterItem {
    String idSong;
    String nameSong;

    String idSinger;
    String nameSinger;

    String avatar;

    public SearchFilterItem(String idSong, String nameSong, String idSinger, String nameSinger, String avatar) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.idSinger = idSinger;
        this.nameSinger = nameSinger;
        this.avatar = avatar;
    }

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
}
