package com.kursovaya.BandCafe.Controllers;


import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
public class MainController {

    @Autowired
    AccountService accountService;

    @Autowired
    ProfileService profileService;

    @GetMapping("/login")
    public String main() {
        return "login";
    }

    @GetMapping("/")
    public String homepage(Principal principal, Model model) {
        model.addAttribute( "principal",principal);
        if (principal != null) {
            model.addAttribute("account", accountService.findByLogin(principal.getName()));
            model.addAttribute("profileID", profileService.findByLogin(principal.getName()));
        }
        return "homepage";
    }

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('admin_role')" )
    public String admin() {
        return "admin";
    }

}
