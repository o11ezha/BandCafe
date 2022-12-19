package com.kursovaya.BandCafe.Entities;

import java.util.Date;

public class Post {
    private String postID;
    private String postText;
    private Date postDate;
    private String postImageSource;
    private String forumID;
    private String authorLogin;
    private String replyPostID;
    private Integer categoryID;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPostImageSource() {
        return postImageSource;
    }

    public void setPostImageSource(String postImageSource) {
        this.postImageSource = postImageSource;
    }

    public String getForumID() {
        return forumID;
    }

    public void setForumID(String forumID) {
        this.forumID = forumID;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    public String getReplyPostID() {
        return replyPostID;
    }

    public void setReplyPostID(String replyPostID) {
        this.replyPostID = replyPostID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
}
