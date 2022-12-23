package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Merch;
import com.kursovaya.BandCafe.Repos.MerchRepo;
import com.kursovaya.BandCafe.Views.MerchView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchService {

    @Autowired
    MerchRepo merchRepo;

    public void addMerch(Merch merch, String login){
        merchRepo.addMerch(login, merch.getMerchName(),
                merch.getMerchPrice(), merch.getMerchAmount(),
                merch.getMerchDescSource(),
                merch.getGroupID());
    }

    public void editMerch(Merch merch){
        merchRepo.editMerch(merch.getMerchID(), merch.getMerchName(),
                merch.getMerchPrice(), merch.getMerchStatus(),
                merch.getMerchAmount(), merch.getGroupID());
    }

    public List<MerchView> getAllMerchFromView(){
        return merchRepo.getAllMerchFromView();
    }

    public Merch getMerchByName(String merchName){
        return merchRepo.getMerchByName(merchName);
    }

    public Merch getMerchByID(String merchID){
        return merchRepo.getMerchByID(merchID);
    }

    public String getManagerID(String merchID){
        return merchRepo.getManagerID(merchID);
    }

    public List<String> getMerchIDsByManagerLogin(String login){
        return merchRepo.getMerchIDsByManagerLogin(login);
    }
}
