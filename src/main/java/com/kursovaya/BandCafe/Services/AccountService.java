package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.Repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public List<String> findAllLogins(){

        List<Account> accountsLogins = accountRepo.getAll();
        List<String> logins = new ArrayList<>();

        for (Account account : accountsLogins) {
            logins.add(account.getAccountLogin());
        }
        return logins;
    }

    public List<String> getAllManagersLogins() {
        List<Account> managers = accountRepo.getAllManager();
        List<String> managerslogins = new ArrayList<>();
        for (Account manager : managers) {
            managerslogins.add(manager.getAccountLogin());
        }
        return managerslogins;
    }

    public List<Account> findAll() { return accountRepo.getAll(); }
    public List<Account> findSpecialRoleAccounts(Integer roleID) { return accountRepo.findSpecialRoleAccounts(roleID); }
    public Account findByLogin(String login) {
        return accountRepo.findByLogin(login);
    }

    public void registration(String login, String password){
        accountRepo.registration(login, password);
    }

    public void editAccount(String oldlogin, String newlogin, String newpassword){
        accountRepo.editAccount(oldlogin, newlogin, newpassword);
    }

    public void deleteAccount(String login){
        accountRepo.deleteAccount(login);
    }

}
