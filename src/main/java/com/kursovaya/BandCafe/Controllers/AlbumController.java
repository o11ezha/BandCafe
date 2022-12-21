package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.SongService;
import com.kursovaya.BandCafe.Views.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @Autowired
    SongService songService;

    @Autowired
    MemberGroupService memberGroupService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("bands/{groupName}/{albumName}")
    public String returnAlbum(@PathVariable("groupName") String groupName,
                              @PathVariable("albumName") String albumName,
                              Model model) {
        Album album = albumService.getAlbumByAlbumName(albumName.replace("%20"," "));

        List<SongView> songs = songService.getSongsViewByAlbumName(albumName);
        model.addAttribute("songs", songs);

        String pathToImg = "";

        if (!Objects.equals(album.getAlbumCover(), "") && !Objects.equals(album.getAlbumCover(), null)) {
            System.out.println(album.getAlbumCover());
            pathToImg = album.getAlbumCover();
        }
        else pathToImg = "default.jpg";

        System.out.println(pathToImg);

        model.addAttribute("pathToImg", pathToImg);
        model.addAttribute("album", album);
        return "albumView";
    }

    @GetMapping("bands/{groupName}/add")
    public String addAlbum(@PathVariable("groupName") String groupName,
                            Model model) {
        Album album = new Album();
        model.addAttribute("album", album);
        return "addAlbum";
    }

    @PostMapping("bands/{groupName}/add")
    public String addAlbum(@PathVariable("groupName") String groupName,
                           @Validated Album album,
                           BindingResult bindingResult,
                           String errorAlbumName,
                           String errorAlbumDate,
                           @RequestParam("filecover") MultipartFile filecover,
                           Model model) throws UnsupportedEncodingException {

        model.addAttribute("album", album);

        if (bindingResult.hasErrors()) {
            return "addAlbum";
        }

        if (album.getAlbumName().equals("") || album.getAlbumName() == null) {
            errors(errorAlbumName, "Введите название альбома", "addAlbum", model);
        }

        if (album.getAlbumReleaseDate().equals("") || album.getAlbumReleaseDate() == null) {
            errors(errorAlbumDate, "Ввыберите дату релиза альбома", "addAlbum", model);
        }

        if (!Objects.requireNonNull(filecover.getOriginalFilename()).equals("")) {
            File uploadDir = new File(uploadPath + "/CoverImages");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filecover.getOriginalFilename();


            try {
                filecover.transferTo(new File(uploadDir + "/" + resultFilename));
                album.setAlbumCover(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            album.setAlbumCover("default.jpg");
        }

        String groupID = memberGroupService.getGroupByGroupName(groupName).getGroupID();
        album.setGroupOwnerID(groupID);

        System.out.println(album.toString());

        albumService.addAlbum(album);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8");
    }

    static Album album2 = new Album();

    @GetMapping("bands/{groupName}/{albumID}/edit")
    public String editAlbum(@PathVariable("groupName") String groupName,
                              @PathVariable("albumID") String albumID,
                              Model model) {
        Album album = albumService.getAlbumByAlbumName(albumID);
        album2 = album;
        model.addAttribute("album", album);
        return "editAlbum";
    }

    @PostMapping("bands/{groupName}/{albumID}/edit")
    public String editAlbum(@ModelAttribute("album") Album album,
                            BindingResult bindingResult,
                            String errorAlbumName,
                            String errorAlbumDate,
                            @PathVariable("groupName") String groupName,
                            @PathVariable("albumID") String albumID,
                            @RequestParam("filecover") MultipartFile filecover,
                            Model model) throws UnsupportedEncodingException {
        model.addAttribute("album", album);

        if (bindingResult.hasErrors()) {
            return "editAlbum";
        }

        if (album.getAlbumName().equals("") || album.getAlbumName() == null) {
            errors(errorAlbumName, "Введите название альбома", "editAlbum", model);
        }

        if (album.getAlbumReleaseDate().equals("") || album.getAlbumReleaseDate() == null) {
            errors(errorAlbumDate, "Ввыберите дату релиза альбома", "editAlbum", model);
        }

        if(!Objects.requireNonNull(filecover.getOriginalFilename()).equals("")
                && !filecover.isEmpty()) {
            File uploadDir = new File(uploadPath + "/CoverImages");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filecover.getOriginalFilename();

            try {
                filecover.transferTo(new File(uploadDir + "/" + resultFilename));
                album.setAlbumCover(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (album2.getAlbumCover().equals("")) {
                album.setAlbumCover("default.jpg");
            } else album.setAlbumCover(album2.getAlbumCover());
        }

        album.setGroupOwnerID(memberGroupService.getGroupByGroupName(groupName).getGroupID());
        album.setAlbumID(albumID);
        albumService.editAlbum(album);

        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8")  + "/" + URLEncoder.encode(album.getAlbumName(), "UTF-8");
    }

    @GetMapping("bands/{groupName}/{albumID}/delete")
    public String deleteAlbum(@PathVariable("groupName") String groupName,
                            @PathVariable("albumID") String albumID,
                            Model model) throws UnsupportedEncodingException {

        albumService.deleteAlbum(albumID);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8");
    }


    public String errors(String finalerror, String texterror,
                         String returnPage, Model model) {

        finalerror = texterror;
        model.addAttribute( '"' + finalerror + '"', finalerror);
        return '"' +  returnPage + '"';
    }
}
