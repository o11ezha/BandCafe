package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.rowMappers.ForumRowMapper;
import com.kursovaya.BandCafe.rowMappers.MemberGroupRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class ForumRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Forum> findAll() {
        String sql = "SELECT * FROM forum";
        return template.query(sql, new ForumRowMapper() );
    }

    public List<String> getAllForumsID() {
        String sql = "SELECT forum_id FROM forum";
        return template.query(sql, (rs, rowNum) -> rs.getString("forum_id"));
    }
    public List<String> getAllForumsName() {
        String sql = "SELECT forum_name FROM forum";
        return template.query(sql, (rs, rowNum) -> rs.getString("forum_name"));
    }

    public List<String> getAllForumsDescription() {
        String sql = "SELECT forum_description FROM forum";
        return template.query(sql, (rs, rowNum) -> rs.getString("forum_description"));
    }

    public List<String> getAllForumsGroupID() {
        String sql = "SELECT group_id FROM forum";
        return template.query(sql, (rs, rowNum) -> rs.getString("group_id"));
    }
    public List<String> getAllForumsGroupName() {
        String sql = "SELECT group_name FROM member_group m JOIN  forum f ON m.group_id = f.group_id;";
        return template.query(sql, (rs, rowNum) -> rs.getString("group_name"));
    }

    public Forum getForumByForumName(String forumName) {
        String sql = "SELECT * FROM forum WHERE forum_name = :forumName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("forumName", forumName);
        return template.queryForObject(sql, namedParameters, new ForumRowMapper() );
    }

    public void editForum(String forumID, String forumName, String forumDesc){
        String sql = "CALL update_forum(?,?,?)";
        template.getJdbcOperations().update(conntection -> {
            CallableStatement cs = conntection.prepareCall(sql);
            cs.setString(1, forumID);
            cs.setString(2, forumName);
            cs.setString(3, forumDesc);
            return cs;
        });
    }
}
