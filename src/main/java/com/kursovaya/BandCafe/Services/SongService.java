package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Repos.SongRepo;
import com.kursovaya.BandCafe.Views.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    SongRepo songRepo;

    public void addSong(Song song) {
        songRepo.addSong(song.getAlbumID(), song.getSongName(),
                song.getSongDuration(), song.getSongMV());
    }

    public void editSong(Song song) {
        songRepo.editSong(song.getSongID(), song.getSongName(),
                song.getSongDuration(), song.getSongMV());
    }

    public void deleteSong(String songID) {
        songRepo.deleteSong(songID);
    }
    public Song getSongByName(String songName) {
        return songRepo.getSongByName(songName);
    }
    public List<Song> getSongsByAlbumName(String albumName) {
        return songRepo.getSongsByAlbumName(albumName);
    }

    public List<SongView> getSongsViewByAlbumName(String albumName) {
        return songRepo.getSongsViewByAlbumName(albumName);
    }

    public List<SongView> getSongsView() {
        return songRepo.getSongsView();
    }
}
