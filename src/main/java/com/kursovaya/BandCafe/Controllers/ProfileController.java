package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Entities.Profile;
import com.kursovaya.BandCafe.Services.AccountRoleService;
import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.MemberService;
import com.kursovaya.BandCafe.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProfileController {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRoleService accountRoleService;

    @Autowired
    ProfileService profileService;

    @Autowired
    MemberService memberService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/profile/{profileID}")
    public String returnProfile(@PathVariable("profileID") String profileID,
                                Principal principal, Model model) {
        Account account = accountService.findByLogin(principal.getName());
        String roleSelected = accountRoleService.findRoleName(account.getRoleID()).replace("_role", "");
        Profile profile = profileService.findByProfileID(profileID);
        model.addAttribute("account", account);

        System.out.println(profile.toString());

        if (roleSelected.equals("member")){
            String memberInfo = memberService.getSpecialStageNameFromProfID(profileID);
            String memberDate = memberService.getSpecialDateFromProfID(profileID);

            System.out.println(memberInfo + " " + memberDate);

            model.addAttribute("memberInfo", memberInfo);
            model.addAttribute("memberDate", memberDate);

        }
        String pathToImg = "";

        if (!Objects.equals(profile.getProfileAvatarSource(), "")
                && (!Objects.equals(profile.getProfileAvatarSource(), null)))
            pathToImg = profile.getProfileAvatarSource();
        else pathToImg = "default.jpg";

        model.addAttribute("pathToImg", pathToImg);

        model.addAttribute("profile", profile);
        model.addAttribute("roleSelected", roleSelected);
        return "profileView";
    }

    static Profile profile2 = new Profile();
    @GetMapping("/profile/{profileID}/edit")
    public String editProfile(@PathVariable("profileID") String profileID,
                              Model model, Principal principal) {
        Account account = accountService.findByLogin(principal.getName());
        String roleSelected = accountRoleService.findRoleName(account.getRoleID()).replace("_role", "");
        Profile profile = profileService.findByProfileID(profileID);
        profile2 = profile;

        model.addAttribute("account", account);
        model.addAttribute("roleSelected", roleSelected);
        model.addAttribute("profile", profile);
        return "editProfile";
    }

    @PostMapping("/profile/{profileID}/edit")
    public String editProfile(@ModelAttribute("profile") @Validated Profile profile,
                              @PathVariable("profileID") String profileID,
                              BindingResult bindingResult,
                              Principal principal,
                              @RequestParam("avatarImage") MultipartFile avatarImage,
                              Model model){

        model.addAttribute("profile", profile);

        if (bindingResult.hasErrors()) {
            System.out.println(
                    //print all errors
                    bindingResult.getAllErrors()
            );
            return "editProfile";
        }

        if (!Objects.requireNonNull(avatarImage.getOriginalFilename()).equals("")
                && !avatarImage.isEmpty()) {
            File uploadDir = new File(uploadPath + "/ProfileImages");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + avatarImage.getOriginalFilename();

            try {
                avatarImage.transferTo(new File(uploadDir + "/" + resultFilename));
                profile.setProfileAvatarSource(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (profile2.getProfileAvatarSource() != null){
                if (profile2.getProfileAvatarSource().equals(""))
                    profile.setProfileAvatarSource("default.jpg");
                else profile.setProfileAvatarSource(profile2.getProfileAvatarSource());
            } else profile.setProfileAvatarSource("default.jpg");
        }

        profile.setAccountLogin(principal.getName());

        profileService.editProfile(profile);
        return ("redirect:/profile/{profileID}");
    }
}
