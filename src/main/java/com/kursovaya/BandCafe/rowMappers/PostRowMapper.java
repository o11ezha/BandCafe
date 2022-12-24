package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostID(rs.getString("post_id"));
        post.setPostText(rs.getString("post_text"));
        post.setPostDate(rs.getString("post_date"));
        post.setPostImageSource(rs.getString("post_image_source"));
        post.setForumID(rs.getString("forum_id"));
        post.setAuthorLogin(rs.getString("author_login"));
        post.setReplyPostID(rs.getString("reply_post_id"));
        post.setCategoryID(rs.getInt("category_id"));
        return post;
    }
}