package com.kursovaya.BandCafe.Entities;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Album {
    private String albumID;
    @NotNull(message = "Название альбома не может быть пустым")
    private String albumName;
    @NotNull(message = "Дата релиза не может быть пустой")
    private String albumReleaseDate;
    private String albumCover;
    private String groupOwnerID;

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getGroupOwnerID() {
        return groupOwnerID;
    }

    public void setGroupOwnerID(String groupOwnerID) {
        this.groupOwnerID = groupOwnerID;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumID='" + albumID + '\'' +
                ", albumName='" + albumName + '\'' +
                ", albumReleaseDate='" + albumReleaseDate + '\'' +
                ", albumCover='" + albumCover + '\'' +
                ", groupOwnerID='" + groupOwnerID + '\'' +
                '}';
    }
}
