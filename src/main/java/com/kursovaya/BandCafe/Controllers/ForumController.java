package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @GetMapping("/{forumName}")
    public String returnForum(@PathVariable String forumName,
                              Model model){
        Forum forum = forumService.getForumByForumName(forumName);

        model.addAttribute("forum", forum);
        return "forumView";
    }

    static Forum forum2 = new Forum();

    @GetMapping("/{forumName}/edit")
    public String editForum(@PathVariable("forumName") String forumName, Model model) {
        Forum forum = forumService.getForumByForumName(forumName);
        forum2 = forum;
        model.addAttribute("forum", forum);
        return "editForum";
    }

    @PostMapping("/{forumName}/edit")
    public String editForum(@ModelAttribute("forum") @Validated Forum forum,
                            BindingResult bindingResult,
                            @PathVariable("forumName") String forumName,
                            String errorForum,
                            String errorForumDesc,
                            Model model) throws UnsupportedEncodingException {

        model.addAttribute("forum", forum);

        if (bindingResult.hasErrors()) {
            return "editForum";
        }

        List<String> allForumsName = forumService.getAllForumsName();

        if (forum.getForumName().equals("") || forum.getForumName() == null) {
            model.addAttribute("errorForum", "Введите название форума");
            return "editForum";
        }

        if (allForumsName.contains(forum.getForumName()) && !forum.getForumName().equals(forum2.getForumName())) {
            errorForum = "Такой форум уже существует";
            model.addAttribute("errorForum", errorForum);
            return "editForum";
        }

        if (forum.getForumDesc().equals("") || forum.getForumDesc() == null) {
            model.addAttribute("errorForumDesc", "Введите описание форума");
            return "editForum";
        }

        forum.setForumID(forum2.getForumID());

        forumService.editForum(forum);
        return "redirect:/forums/" + URLEncoder.encode(forum.getForumName(),"UTF-8").replace("+", "%20");
    }

}
