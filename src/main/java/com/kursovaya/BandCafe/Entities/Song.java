package com.kursovaya.BandCafe.Entities;

public class Song {
    private String SongID;
    private String songName;
    private Integer songDuration;
    private String songMV;
    private String albumID;

    public String getSongID() {
        return SongID;
    }

    public void setSongID(String songID) {
        SongID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(Integer songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongMV() {
        return songMV;
    }

    public void setSongMV(String songMV) {
        this.songMV = songMV;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }
}
