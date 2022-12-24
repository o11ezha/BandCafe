package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Profile;
import com.kursovaya.BandCafe.Repos.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    ProfileRepo profileRepo;

    public void editProfile(Profile profile){
        profileRepo.editProfile(profile.getAccountLogin(), profile.getProfileAvatarSource(),
                profile.getProfileDateOfBirth(), profile.getProfileDesc());
    }

    public List<Profile> getAllProfiles(){
        return profileRepo.getAllProfiles();
    }
    public Profile findByProfileID(String profileID) {
        return profileRepo.findProfileByProfileID(profileID);
    }
    public Profile findProfileByAccountLogin(String login) {
        return profileRepo.findProfileByAccountLogin(login);
    }

    public String findByLogin(String login) {
        return profileRepo.findProfileByLogin(login);
    }

    public List<String> getAllMembersProfiles(String forumID){
        return profileRepo.getAllMembersProfiles(forumID);
    }
}
