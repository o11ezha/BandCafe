package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.AccountRole;
import com.kursovaya.BandCafe.Repos.AccountRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRoleService {

    @Autowired
    AccountRoleRepo accountRoleRepo;

    public void editAccountRole(String adminLogin, String userLogin, Integer roleID){
        accountRoleRepo.editAccountRole(adminLogin, userLogin, roleID);
    }

    public String findRoleName(Integer roleid) {
        return accountRoleRepo.findRoleName(roleid).getRoleName();
    }

    public List<AccountRole> findAll() {
        return accountRoleRepo.findAll();
    }
}
