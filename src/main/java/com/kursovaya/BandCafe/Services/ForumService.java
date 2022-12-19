package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Repos.ForumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumService {

    @Autowired
    ForumRepo forumRepo;

    @Autowired
    MemberGroupService memberGroupService;

    public List<Forum> getAllForums(){
        return forumRepo.findAll();
    }

    public List<String> getAllForumsID(){
        List<Forum> forums = getAllForums();
        return forums.stream().map(Forum::getForumID).toList();
    }

    public List<String> getAllForumsName(){
        List<Forum> forums = getAllForums();
        return forums.stream().map(Forum::getForumName).toList();
    }

    public List<String> getAllForumsDescription(){
        List<Forum> forums = getAllForums();
        return forums.stream().map(Forum::getForumDesc).toList();
    }

    public List<String> getAllForumsGroupID(){
        List<Forum> forums = getAllForums();
        return forums.stream().map(Forum::getGroupID).toList();
    }

    public List<String> getAllForumsGroupName(){
        List <String> groupsID = getAllForumsGroupID();
        List<String> groupsName = new ArrayList<>();
        for (String groupID : groupsID) {
            groupsName.add(memberGroupService.getGroupNameByID(groupID));
        }
        return groupsName;
    }
}
