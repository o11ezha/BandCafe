package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.SongService;
import com.kursovaya.BandCafe.Views.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
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

    @Autowired
    AccountService accountService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("bands/{groupName}/{albumName}")
    public String returnAlbum(@PathVariable("groupName") String groupName,
                              @PathVariable("albumName") String albumName,
                              Principal principal,
                              Model model) {
        Album album = albumService.getAlbumByAlbumName(albumName.replace("%20"," "));
        Account account = accountService.findByLogin(principal.getName());
        model.addAttribute("account", account);

        List<SongView> songs = songService.getSongsViewByAlbumName(albumName);
        model.addAttribute("songs", songs);

        String pathToImg = "";

        if (!Objects.equals(album.getAlbumCover(), "") && !Objects.equals(album.getAlbumCover(), null)) {
            pathToImg = album.getAlbumCover();
        }
        else pathToImg = "default.jpg";


        model.addAttribute("pathToImg", pathToImg);
        model.addAttribute("album", album);
        return "albumView";
    }

    @GetMapping("bands/{groupName}/add")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String addAlbum(@PathVariable("groupName") String groupName,
                            Model model) {
        Album album = new Album();
        model.addAttribute("album", album);
        return "addAlbum";
    }

    @PostMapping("bands/{groupName}/add")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
            model.addAttribute("errorAlbumName", "Введите название альбома");
            return "addAlbum";
        }

        if (album.getAlbumReleaseDate().equals("") || album.getAlbumReleaseDate() == null) {
            model.addAttribute("errorAlbumDate", "Введите дату релиза альбома");
            return "addAlbum";
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            album.setAlbumCover("default.jpg");
        }

        String groupID = memberGroupService.getGroupByGroupName(groupName).getGroupID();
        album.setGroupOwnerID(groupID);


        albumService.addAlbum(album);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8");
    }

    static Album album2 = new Album();

    @GetMapping("bands/{groupName}/{albumID}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String editAlbum(@PathVariable("groupName") String groupName,
                              @PathVariable("albumID") String albumID,
                              Model model) {
        Album album = albumService.getAlbumByAlbumID(albumID);
        album2 = album;
        model.addAttribute("album", album);
        return "editAlbum";
    }

    @PostMapping("bands/{groupName}/{albumID}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
            model.addAttribute("errorAlbumName", "Введите название альбома");
            return "editAlbum";
        }

        if (album.getAlbumReleaseDate().equals("") || album.getAlbumReleaseDate() == null) {
            model.addAttribute("errorAlbumDate", "Ввыберите дату релиза альбома");
            return "editAlbum";
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

        return "redirect:/bands/" + groupName  + "/" + album.getAlbumName();
    }

    @GetMapping("bands/{groupName}/{albumID}/delete")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String deleteAlbum(@PathVariable("groupName") String groupName,
                            @PathVariable("albumID") String albumID,
                            Model model) throws UnsupportedEncodingException {

        albumService.deleteAlbum(albumID);
        return "redirect:/bands/" + URLEncoder.encode(groupName, StandardCharsets.UTF_8);
    }
}
