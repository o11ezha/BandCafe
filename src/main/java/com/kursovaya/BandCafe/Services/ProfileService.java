package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Profile;
import com.kursovaya.BandCafe.Repos.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileRepo profileRepo;

    public void editProfile(Profile profile){
        profileRepo.editProfile(profile.getAccountLogin(), profile.getProfileAvatarSource(),
                profile.getProfileDateOfBirth(), profile.getProfileDesc());
    }

    public Profile findByProfileID(String profileID) {
        return profileRepo.findProfileByProfileID(profileID);
    }

    public String findByLogin(String login) {
        return profileRepo.findProfileByLogin(login);
    }
}
