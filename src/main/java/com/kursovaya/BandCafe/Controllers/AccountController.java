package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Entities.AccountRole;
import com.kursovaya.BandCafe.Services.AccountRoleService;
import com.kursovaya.BandCafe.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRoleService accountRoleService;

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

    @GetMapping("/acc/{login}")
    public String returnAccount(@PathVariable String login, Model model){
        Account account = accountService.findByLogin(login);
        String roleSelected = accountRoleService.findRoleName(account.getRoleID());
        model.addAttribute("account", account);
        model.addAttribute("roleSelected", roleSelected);
        return "accountView";
    }

    static Account account2 = new Account();

    @GetMapping("/editacc/{login}")
    public String accountEditForm(@PathVariable String login,
                               Model model){

        Account account = accountService.findByLogin(login);
        account2 = account;
        Integer roleSelected = account.getRoleID();
        List<AccountRole> accountRoles = accountRoleService.findAll();

        model.addAttribute("roleSelected", roleSelected);
        model.addAttribute("account", account);
        model.addAttribute("accountRoles", accountRoles);
        return "editacc";
    }

    @PostMapping("/editacc/{login}")
    public  String accountEditForm(
            @PathVariable @RequestParam String login,
            @RequestParam String password,
            @RequestParam Integer role,
            String errorLogin,
            Principal principal,
            Model model){

        List<String> logins = accountService.findAllLogins();

        Integer roleSelected = account2.getRoleID();
        List<AccountRole> accountRoles = accountRoleService.findAll();

        model.addAttribute("roleSelected", roleSelected);
        model.addAttribute("account", account2);
        model.addAttribute("accountRoles", accountRoles);

        if (!account2.getAccountLogin().equals(login) && logins.contains(login)) {
            model.addAttribute("login", login);
            errorLogin = "Такой логин уже существует";
            model.addAttribute("errorLogin", errorLogin);
            return "editacc";
        }
        errorLogin = "";

        if (password.isEmpty()){
            password = account2.getAccountPassword();
        }

        accountService.editAccount(account2.getAccountLogin(), login, password, role);
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }

    @GetMapping("/deleteacc/{login}")
    public String deleteAccount(@PathVariable String login){
        accountService.deleteAccount(login);
        return "redirect:/login";
    }
}
