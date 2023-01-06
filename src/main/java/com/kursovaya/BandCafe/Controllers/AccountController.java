package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Entities.AccountRole;
import com.kursovaya.BandCafe.Entities.Profile;
import com.kursovaya.BandCafe.Services.AccountRoleService;
import com.kursovaya.BandCafe.Services.AccountService;
import com.kursovaya.BandCafe.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRoleService accountRoleService;

    @Autowired
    ProfileService profileService;

    @GetMapping("/registration")
    public String addingAccount(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addingAccount(@RequestParam String login,
                                @RequestParam String password,
                                String errorLogin,
                                Model model){

        List<String> logins = accountService.findAllLogins();

        if (logins.contains(login)) {
            errorLogin = "Такой логин уже существует";
            model.addAttribute("errorLogin", errorLogin);
            return "registration";
        }
        errorLogin = "";
        accountService.registration(login, password);
        return ("redirect:/login");
    }

    @GetMapping("/allaccountsforADMINonly")
    @PreAuthorize("hasAuthority('admin_role')")
    public String allAccounts(Model model){
        IdenticalPart(model);
        return "accounts";
    }

    @PostMapping("/allaccountsforADMINonly/filter")
    @PreAuthorize("hasAuthority('admin_role')")
    public String filter(@RequestParam String filter, Model model){
        IdenticalPart(model);
        Integer roleID = -1;
        switch (filter) {
            case "none":
                break;
            case "user":
                roleID = 3;
                break;
            case "member":
                roleID = 2;
                break;
            case "manager":
                roleID = 1;
                break;
            case "admin":
                roleID = 0;
                break;
        }
        if (roleID != -1) {
            List<Account> accounts = accountService.findSpecialRoleAccounts(roleID);
            model.addAttribute("accounts", accounts);
        } else {
            return "redirect:/allaccountsforADMINonly";
        }
        return "accounts";
    }
    @PostMapping("/allaccountsforADMINonly")
    @PreAuthorize("hasAuthority('admin_role')")
    public String allAccounts(Principal principal,
                              @RequestParam String role,
                              @RequestParam (required = false) String accountLogin,
                              Model model){
        IdenticalPart(model);
        Integer trueRoleID = -1;

        switch (role){
            case "user":
                trueRoleID = 3;
                break;
            case "manager":
                trueRoleID = 1;
                break;
            case default:
                break;
        }

        if (trueRoleID != -1) accountRoleService.editAccountRole(principal.getName(), accountLogin, trueRoleID);
        return "accounts";
    }

    @GetMapping("/acc/{login}")
    public String returnAccount(@PathVariable String login, Model model){
        Account account = accountService.findByLogin(login);
        String roleSelected = accountRoleService.findRoleName(account.getRoleID());
        Profile profile1 = profileService.findProfileByAccountLogin(login);

        String pathToImg = "";

        if (!Objects.equals(profile1.getProfileAvatarSource(), "")
                && (!Objects.equals(profile1.getProfileAvatarSource(), null)))
            pathToImg = profile1.getProfileAvatarSource();
        else pathToImg = "default.jpg";

        model.addAttribute("pathToImg", pathToImg);
        model.addAttribute("account", account);
        model.addAttribute("roleSelected", roleSelected);
        model.addAttribute("profile1", profile1);
        return "accountView";
    }

    static Account account2 = new Account();

    @GetMapping("/editacc/{login}")
    public String accountEditForm(@PathVariable String login,
                               Model model){

        Account account = accountService.findByLogin(login);
        account2 = account;

        model.addAttribute("account", account);
        return "editacc";
    }

    @PostMapping("/editacc/{login}")
    public  String accountEditForm(
            @PathVariable @RequestParam String login,
            @RequestParam String password,
            String errorLogin,
            Principal principal,
            Model model){

        List<String> logins = accountService.findAllLogins();

        model.addAttribute("account", account2);

        if (!account2.getAccountLogin().equals(login) && logins.contains(login)) {
            model.addAttribute("login", login);
            errorLogin = "Такой логин уже существует";
            model.addAttribute("errorLogin", errorLogin);
            return "editacc";
        }
        errorLogin = "";

        accountService.editAccount(account2.getAccountLogin(), login, password);
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }

    @GetMapping("/deleteacc/{login}")
    public String deleteAccount(@PathVariable String login){
        accountService.deleteAccount(login);
        return "redirect:/login";
    }

    public void IdenticalPart(Model model){
        List<Account> accounts = accountService.findAll();
        List<String> accountRoles = new ArrayList<>();
        accountRoles.add("user");
        accountRoles.add("manager");
        model.addAttribute("accounts", accounts);
        model.addAttribute("accountRoles", accountRoles);
    }
}
