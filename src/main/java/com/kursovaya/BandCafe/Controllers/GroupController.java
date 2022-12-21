package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/bands")
public class GroupController {

    @Autowired
    MemberGroupService memberGroupService;

    @Autowired
    AccountService accountService;

    @Autowired
    MemberService memberService;

    @Autowired
    AlbumService albumService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("")
    public String getGroups(Model model) throws FileNotFoundException {
        List<String> groupsID = memberGroupService.getAllGroupsID();
        List<String> groupDesc = memberGroupService.getAllGroupsDescriptionSource();
        List<String> Desc = new ArrayList<>();
        List<String> groupNames = memberGroupService.getAllGroupsName();
        List<String> groupFandoms = memberGroupService.getAllGroupsFandomName();

        for (String desc : groupDesc) {
            File file = new File(uploadPath + "/GroupDesc/" + desc);
            Scanner sc = new Scanner(file);
            Desc.add(sc.nextLine());
        }

        model.addAttribute("groupsID", groupsID);
        model.addAttribute("groupDesc", Desc);
        model.addAttribute("groupNames", groupNames);
        model.addAttribute("groupFandoms", groupFandoms);
        return "groups";
    }

    @GetMapping("/{groupName}")
    public String returnGroup(@PathVariable String groupName, Model model) throws FileNotFoundException {
        MemberGroup group = memberGroupService.getGroupByGroupName(groupName);
        List<Album> albums = albumService.getAlbumsByGroupName(groupName);
        File file = new File(uploadPath + "/GroupDesc/" + group.getGroupDescSource());
        Scanner sc = new Scanner(file);

        if (albums.size() > 0) {
            model.addAttribute("albums", albums);
        } else {
            model.addAttribute("albums", null);
        }

        model.addAttribute("group", group);
        model.addAttribute("groupdesc",  sc.nextLine());
        model.addAttribute("manager", group.getGroupManager());
        model.addAttribute("members", memberService.getMembersByGroupID(group.getGroupID()));
        return "groupView";
    }

    @GetMapping("/add")
    public String addGroup(Model model) {
        countryList(model);
        model.addAttribute("group", new MemberGroup());
        return "addGroup";
    }

    @PostMapping("/add")
    public String addGroup(@ModelAttribute("group") @Validated MemberGroup group,
                           BindingResult bindingResult,
                           @RequestParam("filegroup") MultipartFile filegroup,
                           String errorGroup,
                           String errorCountry,
                           String errorDebut,
                           String errorFandom,
                           Principal principal,
                           Model model) {
        countryList(model);
        model.addAttribute("group", group);

        if (bindingResult.hasErrors()) {
            return "addGroup";
        }
        List<String> allGroupNames = memberGroupService.getAllGroupsName();
        List<String> allGroupsFandoms = memberGroupService.getAllGroupsFandomName();

        if (group.getGroupName().equals("") || group.getGroupName() == null) {
            errors(errorGroup, "Введите название группы", "addGroup", model);
        }

        if (allGroupNames.contains(group.getGroupName())){
            errorGroup = "Такая группа уже существует";
            model.addAttribute("errorGroup", errorGroup);
            return "addGroup";
        }

        if (group.getGroupCountry().equals("") || group.getGroupCountry() == null) {
            errors(errorCountry, "Введите страну группы", "addGroup", model);
        }
        if (group.getGroupDebutDate().equals("") || group.getGroupDebutDate() == null) {
            errors(errorDebut,"Введите дату дебюта группы", "addGroup", model);
        }
        if (group.getGroupFandom().equals("") || group.getGroupFandom() == null) {
            errors(errorFandom,"Введите название фандома группы", "addGroup", model);
        }

        if (allGroupsFandoms.contains(group.getGroupFandom())){
            errorFandom = "Такой фандом уже существует";
            model.addAttribute("errorFandom", errorFandom);
            return "addGroup";
        }

        if (filegroup != null){
            File uploadDir = new File(uploadPath + "/GroupDesc");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filegroup.getOriginalFilename();


            try {
                filegroup.transferTo(new File(uploadDir + "/"+ resultFilename));
                group.setGroupDescSource(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        group.setGroupManager(principal.getName());

        System.out.println(group.toString());
        memberGroupService.addGroup(group);

        return "redirect:/bands";
    }

    static MemberGroup group2 = new MemberGroup();
    @GetMapping("/edit/{groupID}")
    public String editGroup(@PathVariable String groupID, Model model) {
        MemberGroup group = memberGroupService.getGroupByGroupID(groupID);
        group2 = group;
        model.addAttribute("group", group);
        model.addAttribute("managers", accountService.getAllManagersLogins());
        model.addAttribute("manager", group.getGroupManager());
        return "editGroup";
    }

    @PostMapping("/edit/{groupID}")
    public String editGroup(@ModelAttribute("group") @Validated MemberGroup group,
                            BindingResult bindingResult,
                            @PathVariable("groupID") String groupID,
                            String errorGroup,
                            String errorFandom,
                            String errorManager,
                            Model model) {
        model.addAttribute("group", group);
        model.addAttribute("managers", accountService.getAllManagersLogins());
        model.addAttribute("manager", group.getGroupManager());

        group.setGroupCountry(group2.getGroupCountry());
        group.setGroupDebutDate(group2.getGroupDebutDate());
        group.setGroupDescSource(group2.getGroupDescSource());

        System.out.println(group2.toString());
        System.out.println(group.toString());

        if (bindingResult.hasErrors()) {
            System.out.println(
                    //print all errors
                    bindingResult.getAllErrors()
            );
            return "editGroup";
        }

        List<String> allGroupNames = memberGroupService.getAllGroupsName();
        List<String> allGroupsFandoms = memberGroupService.getAllGroupsFandomName();

        if (group.getGroupName().equals("") || group.getGroupName() == null) {
            errors(errorGroup, "Введите название группы", "editGroup", model);
        }

        if (allGroupNames.contains(group.getGroupName()) && !group.getGroupName().equals(group2.getGroupName())) {
            errorGroup = "Такая группа уже существует";
            model.addAttribute("errorGroup", errorGroup);
            return "editGroup";
        }

        if (group.getGroupFandom().equals("") || group.getGroupFandom() == null) {
            errors(errorFandom, "Введите название фандома группы", "editGroup", model);
        }

        if (allGroupsFandoms.contains(group.getGroupFandom()) && !group.getGroupFandom().equals(group2.getGroupFandom())) {
            errorFandom = "Такой фандом уже существует";
            model.addAttribute("errorFandom", errorFandom);
            return "editGroup";
        }

        if (group.getGroupManager().equals("") || group.getGroupManager() == null) {
            errors(errorManager, "Выберите менеджера", "editGroup", model);
        }

        memberGroupService.editGroup(group);

        return "redirect:/bands";
    }

    public String errors(String finalerror, String texterror,
                         String returnPage, Model model) {

            finalerror = texterror;
            model.addAttribute( '"' + finalerror + '"', finalerror);
            return '"' +  returnPage + '"';
    }
    public void countryList(Model model){
        List<String> countries = new ArrayList<>();

        for (String country : Locale.getISOCountries())
        {countries.add(new Locale("", country).getDisplayCountry());}
        model.addAttribute("countries", countries);

    }
}
