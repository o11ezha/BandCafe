package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Repos.ForumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    ForumRepo forumRepo;

    @Autowired
    MemberGroupService memberGroupService;


    public void editForum(Forum forum){
        forumRepo.editForum(forum.getForumID(), forum.getForumName(), forum.getForumDesc());
    }

    public List<Forum> getAllForums(){
        return forumRepo.findAll();
    }
    public Forum getForumByForumName(String forumName){
        return forumRepo.getForumByForumName(forumName);
    }

    public List<String> getAllForumsID(){
        return forumRepo.getAllForumsID();
    }

    public List<String> getAllForumsName(){
        return forumRepo.getAllForumsName();
    }

    public List<String> getAllForumsDescription(){
        return forumRepo.getAllForumsDescription();
    }

    public List<String> getAllForumsGroupID(){
        return forumRepo.getAllForumsGroupID();
    }

    public List<String> getAllForumsGroupName(){
        return forumRepo.getAllForumsGroupName();
    }
}
