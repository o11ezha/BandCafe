package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.AlbumService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String getGroups(Principal principal, Model model) throws FileNotFoundException {
        List<MemberGroup> groups = memberGroupService.getAllGroups();
        List<MemberGroup> groups1 = new ArrayList<>();
        List<MemberGroup> groups2 = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            if (i % 2 == 0) {
                groups1.add(groups.get(i));
            } else {
                groups2.add(groups.get(i));
            }
        }

        Account account = accountService.findByLogin(principal.getName());

        model.addAttribute("groups1", groups1);
        model.addAttribute("groups2", groups2);
        model.addAttribute("account", account);
        return "groups";
    }

    @GetMapping("/{groupName}")
    public String returnGroup(@PathVariable String groupName,Principal principal, Model model) throws FileNotFoundException {
        MemberGroup group = memberGroupService.getGroupByGroupName(groupName);
        List<Album> albums = albumService.getAlbumsByGroupName(groupName);
        File file = new File(uploadPath + "/GroupDesc/" + group.getGroupDescSource());
        Scanner sc = new Scanner(file);
        Account account = accountService.findByLogin(principal.getName());


        if (albums.size() > 0) {
            model.addAttribute("albums", albums);
        } else {
            model.addAttribute("albums", null);
        }

        model.addAttribute("group", group);
        model.addAttribute("account", account);
        model.addAttribute("groupdesc",  sc.nextLine());
        model.addAttribute("manager", group.getGroupManager());
        model.addAttribute("members", memberService.getMembersByGroupID(group.getGroupID()));
        return "groupView";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String addGroup(Model model) {
        countryList(model);
        model.addAttribute("group", new MemberGroup());
        return "addGroup";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
            model.addAttribute("errorGroup", "Введите название группы");
            return "addGroup";
        }

        if (allGroupNames.contains(group.getGroupName())){
            model.addAttribute("errorGroup", "Такая группа уже существует");
            return "addGroup";
        }

        if (group.getGroupCountry().equals("") || group.getGroupCountry() == null) {
            model.addAttribute("errorCountry", "Введите страну группы");
            return "addGroup";
        }
        if (group.getGroupDebutDate().equals("") || group.getGroupDebutDate() == null) {
            model.addAttribute("errorDebut", "Введите дату дебюта группы");
            return "addGroup";
        }
        if (group.getGroupFandom().equals("") || group.getGroupFandom() == null) {
            model.addAttribute("errorFandom", "Введите название фэндома группы");
            return "addGroup";
        }

        if (allGroupsFandoms.contains(group.getGroupFandom())){
            errorFandom = "Такой фандом уже существует";
            model.addAttribute("errorFandom", errorFandom);
            return "addGroup";
        }

        if (Objects.requireNonNull(filegroup.getOriginalFilename()).equals("") && filegroup.getOriginalFilename() == null) {
            File uploadDir = new File(uploadPath + "/GroupDesc");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filegroup.getOriginalFilename();


            try {
                filegroup.transferTo(new File(uploadDir + "/"+ resultFilename));
                group.setGroupDescSource(resultFilename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        group.setGroupManager(principal.getName());

        memberGroupService.addGroup(group);

        return "redirect:/bands";
    }

    static MemberGroup group2 = new MemberGroup();
    @GetMapping("/edit/{groupID}")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
    public String editGroup(@PathVariable String groupID, Model model) {
        MemberGroup group = memberGroupService.getGroupByGroupID(groupID);
        group2 = group;
        model.addAttribute("group", group);
        model.addAttribute("managers", accountService.getAllManagersLogins());
        model.addAttribute("manager", group.getGroupManager());
        return "editGroup";
    }

    @PostMapping("/edit/{groupID}")
    @PreAuthorize("hasAnyAuthority('admin_role', 'manager_role')")
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
            model.addAttribute("errorGroup", "Введите название группы");
            return "editGroup";
        }

        if (allGroupNames.contains(group.getGroupName()) && !group.getGroupName().equals(group2.getGroupName())) {
            model.addAttribute("errorGroup", "Такая группа уже существует");
            return "editGroup";
        }

        if (group.getGroupFandom().equals("") || group.getGroupFandom() == null) {
            model.addAttribute("errorFandom", "Введите название фандома группы");
            return "editGroup";
        }

        if (allGroupsFandoms.contains(group.getGroupFandom()) && !group.getGroupFandom().equals(group2.getGroupFandom())) {
            model.addAttribute("errorFandom", "Такой фандом уже существует");
            return "editGroup";
        }

        if (group.getGroupManager().equals("") || group.getGroupManager() == null) {
            model.addAttribute("errorManager", "Выберите менеджера");
            return "editGroup";
        }

        memberGroupService.editGroup(group);

        return "redirect:/bands";
    }

    public void countryList(Model model){
        List<String> countries = new ArrayList<>();

        for (String country : Locale.getISOCountries())
        {countries.add(new Locale("", country).getDisplayCountry());}
        model.addAttribute("countries", countries);

    }
}
