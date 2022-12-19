package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/forums")
public class ForumController {

    @Autowired
    ForumService forumService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("")
    public String getGroups(Model model) throws FileNotFoundException {
        List<String> forumID = forumService.getAllForumsID();
        List<String> forumDesc = forumService.getAllForumsDescription();
        List<String> forumNames = forumService.getAllForumsName();
        List<String> forumGroups = forumService.getAllForumsGroupName();

        model.addAttribute("forumID", forumID);
        model.addAttribute("forumDesc", forumDesc);
        model.addAttribute("forumNames", forumNames);
        model.addAttribute("forumGroups", forumGroups);
        return "forums";
    }
}
