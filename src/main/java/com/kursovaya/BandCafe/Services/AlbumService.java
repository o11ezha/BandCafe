package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Repos.AlbumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumRepo albumRepo;

    public void addAlbum(Album album){
        albumRepo.addAlbum(album.getGroupOwnerID(), album.getAlbumName(),
                album.getAlbumReleaseDate(), album.getAlbumCover());
    }

    public void editAlbum(Album album) {
        albumRepo.editAlbum(album.getAlbumID(), album.getAlbumName(),
                album.getAlbumReleaseDate(), album.getAlbumCover());
    }

    public void deleteAlbum(String albumID) {
        albumRepo.deleteAlbum(albumID);
    }

    public Album getAlbumByAlbumName(String albumName) {
        return albumRepo.getAlbumByAlbumName(albumName);
    }

    public Album getAlbumByAlbumID(String albumID) {
        return albumRepo.getAlbumByAlbumID(albumID);
    }

    public List<Album> getAlbumsByGroupName(String groupName) {
        return albumRepo.getAlbumsByGroupName(groupName);
    }
}
