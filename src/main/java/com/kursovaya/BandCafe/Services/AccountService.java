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

        List<Account> accountsLogins = accountRepo.getAllLogins();
        List<String> logins = new ArrayList<>();

        for (Account account : accountsLogins) {
            logins.add(account.getAccountLogin());
        }
        return logins;
    }

    public Account findByLogin(String login) {
        return accountRepo.findByLogin(login);
    }

    public void registration(String login, String password){
        accountRepo.registration(login, password);
    }

    public void editAccount(String oldlogin, String newlogin, String newpassword, Integer newroleid){
        accountRepo.editAccount(oldlogin, newlogin, newpassword, newroleid);
    }

    public void deleteAccount(String login){
        accountRepo.deleteAccount(login);
    }

}
