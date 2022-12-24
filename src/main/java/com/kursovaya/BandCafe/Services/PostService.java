package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Entities.Post;
import com.kursovaya.BandCafe.Repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    public void addOriginalPost(String forumID, String login, String postText, String fileImage, String replyPostID, Integer categoryPost){
        postRepo.addOriginalPost(forumID, login, postText, fileImage, replyPostID, categoryPost);
    }

    public void deletePost(String postID, String login){
        postRepo.deletePost(postID, login);
    }

    public List<Post> findAllOriginalByForumID(String forumID){
        return postRepo.findAllOriginalByForumID(forumID);
    }

    public List<Post> findAllAnswersByForumID(String forumID){
        return postRepo.findAllAnswersByForumID(forumID);
    }

    public List<String> getAllCategories(){
        return postRepo.getAllCategories();
    }
}
