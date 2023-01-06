package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    AlbumService albumService;

    @GetMapping("/bands/{groupName}/{albumID}/addSong")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String addSong(@PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           Model model) {
        Song song = new Song();
        model.addAttribute("song", song);
        return "addSong";
    }

    @PostMapping("/bands/{groupName}/{albumID}/addSong")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
            model.addAttribute("errorSongName", "Введите название песни");
            return  "addSong";
        }

        if (song.getSongDuration().equals(0) || song.getSongDuration() == null) {
            model.addAttribute("errorSongDuration", "Введите длительность песни");
            return  "addSong";
        }

        song.setAlbumID(albumID);
        Album album = albumService.getAlbumByAlbumID(albumID);

        songService.addSong(song);
        return "redirect:/bands/" + groupName + "/"
                 + album.getAlbumName();
    }

    static Song song2 = new Song();

    @GetMapping("bands/{groupName}/{albumID}/{songID}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String editSong(@PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           @PathVariable("songID") String songID,
                           Model model) {
        Song song = songService.getSongBySongID(songID);
        song2 = song;
        model.addAttribute("song", song);
        return "editSong";
    }

    @PostMapping("bands/{groupName}/{albumID}/{songID}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String editSong(@ModelAttribute("song") Song song,
            BindingResult bindingResult,
            @PathVariable("groupName") String groupName,
                           @PathVariable("albumID") String albumID,
                           @PathVariable("songID") String songID,
                           String errorSongName,
                           String errorSongDuration,
                           Model model) throws UnsupportedEncodingException {
        model.addAttribute("song", song);
        if (bindingResult.hasErrors()) {
            return "editSong";
        }

        if (song.getSongName().equals("") || song.getSongName() == null) {
            model.addAttribute("errorSongName", "Введите название песни");
            return  "editSong";
        }

        if (song.getSongDuration().equals(0) || song.getSongDuration() == null) {
            model.addAttribute("errorSongDuration", "Введите длительность песни");
            return  "editSong";
        }

        song.setSongID(song2.getSongID());
        Album album = albumService.getAlbumByAlbumID(albumID);

        songService.editSong(song);

        return "redirect:/bands/" + groupName + "/"
                + album.getAlbumName();
    }

    @GetMapping("bands/{groupName}/{albumID}/{songID}/delete")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String deleteSong(@PathVariable("groupName") String groupName,
                             @PathVariable("albumID") String albumID,
                             @PathVariable("songID") String songID,
                             Model model) throws UnsupportedEncodingException {

        songService.deleteSong(songID);
        Album album = albumService.getAlbumByAlbumID(albumID);
        return "redirect:/bands/" + groupName + "/"+ album.getAlbumName();
    }

}
