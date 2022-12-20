package com.kursovaya.BandCafe.Entities;

import javax.validation.constraints.NotNull;

public class Forum {
    private String forumID;
    @NotNull(message = "Имя форума не может быть пустым")
    private String forumName;
    @NotNull(message = "Введите описание")
    private String forumDesc;
    private String groupID;

    public String getForumID() {
        return forumID;
    }

    public void setForumID(String forumID) {
        this.forumID = forumID;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumDesc() {
        return forumDesc;
    }

    public void setForumDesc(String forumDesc) {
        this.forumDesc = forumDesc;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
