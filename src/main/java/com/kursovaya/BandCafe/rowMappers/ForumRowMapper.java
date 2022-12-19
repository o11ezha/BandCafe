package com.kursovaya.BandCafe.rowMappers;


import com.kursovaya.BandCafe.Entities.Forum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForumRowMapper implements RowMapper<Forum> {
    @Override
    public Forum mapRow(ResultSet rs, int rowNum) throws SQLException {
        Forum forum = new Forum();
        forum.setForumID(rs.getString("forum_id"));
        forum.setForumName(rs.getString("forum_name"));
        forum.setForumDesc(rs.getString("forum_description"));
        forum.setGroupID(rs.getString("group_id"));
        return forum;
    }
}
