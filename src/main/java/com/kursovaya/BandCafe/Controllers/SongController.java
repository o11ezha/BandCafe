package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    AlbumService albumService;

    @GetMapping("/bands/{groupName}/{albumID}/addSong")
    public String addSong(@PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           Model model) {
        Song song = new Song();
        model.addAttribute("song", song);
        return "addSong";
    }

    @PostMapping("/bands/{groupName}/{albumID}/addSong")
    public String addSong(@ModelAttribute("song") Song song,
                           BindingResult bindingResult,
                           @PathVariable("groupName") String groupName,
                           @PathVariable("albumID")  String albumID,
                           String errorSongName,
                           String errorSongDuration,
                           Model model) throws UnsupportedEncodingException {
        model.addAttribute("song", song);
        if (bindingResult.hasErrors()) {
            return "addSong";
        }

        if (song.getSongName().equals("") || song.getSongName() == null) {
            errors(errorSongName, "Введите название песни", "addSong", model);
        }

        if (song.getSongDuration().equals(0) || song.getSongDuration() == null) {
            errors(errorSongDuration, "Введите длительность песни", "addSong", model);
        }

        song.setAlbumID(albumID);
        Album album = albumService.getAlbumByAlbumID(albumID);

        songService.addSong(song);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8") + "/"
                 + URLEncoder.encode(album.getAlbumName(), "UTF-8");
    }

    static Song song2 = new Song();

    @GetMapping("bands/{groupName}/{albumID}/{songName}")
    public String editSong(@PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           @PathVariable("songName") String songName,
                           Model model) {
        Song song = songService.getSongByName(songName);
        song2 = song;
        model.addAttribute("song", song);
        return "editSong";
    }

    @PostMapping("bands/{groupName}/{albumID}/{songName}")
    public String editSong(@ModelAttribute("song") Song song,
            BindingResult bindingResult,
            @PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           @PathVariable("songName") String songName,
                           String errorSongName,
                           String errorSongDuration,
                           Model model) throws UnsupportedEncodingException {
        model.addAttribute("song", song);
        if (bindingResult.hasErrors()) {
            return "editSong";
        }

        if (song.getSongName().equals("") || song.getSongName() == null) {
            errors(errorSongName, "Введите название песни", "editSong", model);
        }

        if (song.getSongDuration().equals(0) || song.getSongDuration() == null) {
            errors(errorSongDuration, "Введите длительность песни", "editSong", model);
        }

        song.setSongID(song2.getSongID());
        Album album = albumService.getAlbumByAlbumName(albumID);

        songService.editSong(song);

        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8") + "/"
                + URLEncoder.encode(album.getAlbumName(), "UTF-8");
    }

    @GetMapping("bands/{groupName}/{albumID}/{songName}/delete")
    public String deleteSong(@PathVariable("groupName") String groupName,
                             @PathVariable("albumID") String albumID,
                             @PathVariable("songName") String songName,
                             Model model) throws UnsupportedEncodingException {
        Song song = songService.getSongByName(songName);

        songService.deleteSong(song.getSongID());
        Album album = albumService.getAlbumByAlbumID(albumID);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8") + "/"
                + URLEncoder.encode(album.getAlbumName(), "UTF-8");
    }

    public String errors(String finalerror, String texterror,
                         String returnPage, Model model) {

        finalerror = texterror;
        model.addAttribute( '"' + finalerror + '"', finalerror);
        return '"' +  returnPage + '"';
    }
}
