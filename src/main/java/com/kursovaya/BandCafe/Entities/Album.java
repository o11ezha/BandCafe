package com.kursovaya.BandCafe.Entities;

import java.util.Date;

public class Album {
    private String albumID;
    private String albumName;
    private Date albumReleaseDate;
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

    public Date getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(Date albumReleaseDate) {
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
}
