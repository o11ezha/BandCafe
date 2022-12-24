package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Post;
import com.kursovaya.BandCafe.rowMappers.PostRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class PostRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Post> findAllOriginalByForumID(String forumID){
        String sql = "SELECT * FROM post WHERE forum_id = :forumID AND reply_post_id IS NULL";
        SqlParameterSource source = new MapSqlParameterSource().addValue("forumID", forumID);
        return template.query(sql, source, new PostRowMapper());
    }

    public List<Post> findAllAnswersByForumID(String forumID){
        String sql = "SELECT * FROM post WHERE forum_id = :forumID AND reply_post_id IS NOT NULL";
        SqlParameterSource source = new MapSqlParameterSource().addValue("forumID", forumID);
        return template.query(sql, source, new PostRowMapper());
    }

    public List<String> getAllCategories(){
        String sql = "SELECT category_name FROM post_category";
        return template.query(sql, (rs, rowNum) -> rs.getString("category_name"));
    }

    public void addOriginalPost(String forumID, String login,
                                String postText, String fileImage,
                                String replyPostID, Integer categoryPost){
        String sql = "CALL add_post(?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, forumID);
            cs.setString(2, login);
            cs.setString(3, postText);
            cs.setString(4, fileImage);
            cs.setString(5, replyPostID);
            cs.setInt(6, categoryPost);
            return cs;
        });
    }

    public void deletePost(String postID, String login){
        String sql = "CALL delete_post(?, ?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, postID);
            cs.setString(2, login);
            return cs;
        });
    }
}
