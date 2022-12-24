package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.*;
import com.kursovaya.BandCafe.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/forums")
public class ForumController {

    @Autowired
    ForumService forumService;

    @Autowired
    PostService postService;

    @Autowired
    ProfileService profileService;

    @Autowired
    AccountService accountService;

    @Autowired
    MemberGroupService memberGroupService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("")
    public String getGroups(Principal princopal, Model model) {
        List<Forum> forums = forumService.getAllForums();
        List<Forum> forums1 = new ArrayList<>();
        List<Forum> forums2 = new ArrayList<>();
        for (int i = 0; i < forums.size(); i++) {
            if (i % 2 == 0) {
                forums1.add(forums.get(i));
            } else {
                forums2.add(forums.get(i));
            }
        }
        Account account = accountService.findByLogin(princopal.getName());

        model.addAttribute("forums1", forums1);
        model.addAttribute("forums2", forums2);
        model.addAttribute("account", account);
        return "forums";
    }

    @GetMapping("/{forumName}")
    public String returnForum(@PathVariable String forumName,
                              Principal principal,
                              Model model){
        Forum forum = forumService.getForumByForumName(forumName.replace("%20", " "));
        List<Post> posts = postService.findAllOriginalByForumID(forum.getForumID());
        List<Post> posts2 = postService.findAllAnswersByForumID(forum.getForumID());
        Profile profile = profileService.findProfileByAccountLogin(principal.getName());
        List<Profile> profiles = profileService.getAllProfiles();
        List<String> profileLogins = new ArrayList<>();
        List<String> profileAvatars = new ArrayList<>();
        List<String> memberProfileIDs = profileService.getAllMembersProfiles(forum.getForumID());


        List<String> categories = postService.getAllCategories();

        for (Profile profile1 : profiles) {
            profileLogins.add(profile1.getAccountLogin());
            profileAvatars.add(profile1.getProfileAvatarSource());
        }


        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);
        model.addAttribute("posts2", posts2);
        model.addAttribute("categories", categories);
        model.addAttribute("login", principal.getName());
        model.addAttribute("profile", profile);
        model.addAttribute("profileLogins", profileLogins);
        model.addAttribute("profileAvatars", profileAvatars);
        model.addAttribute("memberProfileIDs", memberProfileIDs);
        return "forumView";
    }

    @PostMapping("/{forumName}")
    public String addPost(@PathVariable String forumName,
                          @RequestParam(value = "postText", required = false) String postText,
                          @RequestParam(value = "replyPostID", required = false) String replyPostID,
                          @RequestParam(value = "categoryPost", required = false) Integer categoryPost,
                          @RequestParam("fileImage") MultipartFile fileImage,
                          String postFilePath,
                          Principal principal,
                          Model model) throws UnsupportedEncodingException {
        Forum forum = forumService.getForumByForumName(forumName.replace("%20", " "));
        List<Post> posts = postService.findAllOriginalByForumID(forum.getForumID());
        List<Post> posts2 = postService.findAllAnswersByForumID(forum.getForumID());
        Profile profile = profileService.findProfileByAccountLogin(principal.getName());
        List<String> memberProfileIDs = profileService.getAllMembersProfiles(forum.getForumID());


        List<String> categories = postService.getAllCategories();

        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);
        model.addAttribute("posts2", posts2);
        model.addAttribute("categories", categories);
        model.addAttribute("login", principal.getName());
        model.addAttribute("profile", profile);
        model.addAttribute("memberProfileIDs", memberProfileIDs);

        if (postText == null && postText.isEmpty()) {
            return "forumView";
        }

        if (categoryPost == null || categoryPost > 10 || categoryPost < 0) {
           categoryPost = 99;
        }

        System.out.println((fileImage.getOriginalFilename()));

        if (!Objects.requireNonNull(fileImage.getOriginalFilename()).equals("")
                && !fileImage.isEmpty()){
            File uploadDir = new File(uploadPath + "/ForumIcons");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + fileImage.getOriginalFilename();


            try {
                fileImage.transferTo(new File(uploadDir + "/"+ resultFilename));
                postFilePath = resultFilename;
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            postFilePath = "";
        }

        postService.addOriginalPost(forum.getForumID(), principal.getName(), postText, postFilePath, "",  categoryPost);
        return "redirect:/forums/" + forumName;
    }

    @PostMapping("/{forumName}/reply")
    public String addPostReply(@PathVariable String forumName,
                          @RequestParam(value = "postTextReply", required = false) String postTextReply,
                          @RequestParam(value = "replyPostID2", required = false) String replyPostID2,
                          @RequestParam(value = "categoryPostReply", required = false) Integer categoryPostReply,
                          @RequestParam("fileImage2") MultipartFile fileImage2,
                          String postFilePath,
                          Principal principal,
                          Model model) throws UnsupportedEncodingException {
        Forum forum = forumService.getForumByForumName(forumName.replace("%20", " "));
        List<Post> posts = postService.findAllOriginalByForumID(forum.getForumID());
        List<Post> posts2 = postService.findAllAnswersByForumID(forum.getForumID());
        Profile profile = profileService.findProfileByAccountLogin(principal.getName());
        List<String> memberProfileIDs = profileService.getAllMembersProfiles(forum.getForumID());

        List<String> categories = postService.getAllCategories();

        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);
        model.addAttribute("posts2", posts2);
        model.addAttribute("categories", categories);
        model.addAttribute("login", principal.getName());
        model.addAttribute("profile", profile);
        model.addAttribute("memberProfileIDs", memberProfileIDs);

        if (postTextReply == null && postTextReply.isEmpty()) {
            return "forumView";
        }

        if (categoryPostReply == null || categoryPostReply > 10 || categoryPostReply < 0) {
            categoryPostReply = 99;
        }

        if (!Objects.requireNonNull(fileImage2.getOriginalFilename()).equals("")
                && !fileImage2.isEmpty()){
            File uploadDir = new File(uploadPath + "/ForumIcons");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + fileImage2.getOriginalFilename();


            try {
                fileImage2.transferTo(new File(uploadDir + "/"+ resultFilename));
                postFilePath = resultFilename;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            postFilePath = "";
        }

        postService.addOriginalPost(forum.getForumID(), principal.getName(), postTextReply, postFilePath, replyPostID2, categoryPostReply);
        return "redirect:/forums/" + forumName;
    }

    @GetMapping("/{forumName}/{postID}/delete")
    public String deletePost(@PathVariable("forumName") String forumName,
                             @PathVariable("postID") String postID,
                             Principal principal,
                             Model model) {
        postService.deletePost(postID, principal.getName());
        return "redirect:/forums/" + forumName;
    }

    static Forum forum2 = new Forum();

    @GetMapping("/{forumName}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String editForum(@PathVariable("forumName") String forumName, Model model) {
        Forum forum = forumService.getForumByForumName(forumName);
        forum2 = forum;
        model.addAttribute("forum", forum);
        return "editForum";
    }

    @PostMapping("/{forumName}/edit")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
