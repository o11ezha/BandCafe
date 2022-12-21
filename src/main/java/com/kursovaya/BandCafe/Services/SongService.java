package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Repos.SongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    SongRepo songRepo;

    public List<Song> getSongsByAlbumName(String albumName) {
        return songRepo.getSongsByAlbumName(albumName);
    }
}
