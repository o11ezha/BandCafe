package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Album;
import com.kursovaya.BandCafe.rowMappers.AlbumRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class AlbumRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public Album getAlbumByAlbumName(String albumName) {
        String sql = "SELECT * FROM album WHERE album_name = :albumName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("albumName", albumName);
        return template.queryForObject(sql, namedParameters, new AlbumRowMapper());
    }

    public Album getAlbumByAlbumID(String albumID) {
        String sql = "SELECT * FROM album WHERE album_id = :albumID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("albumID", albumID);
        return template.queryForObject(sql, namedParameters, new AlbumRowMapper());
    }

    public List<Album> getAlbumsByGroupName(String groupName) {
        String sql = "SELECT * FROM album a JOIN member_group m ON a.group_owner_id = m.group_id WHERE group_name = :groupName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("groupName", groupName);
        return template.query(sql, namedParameters, new AlbumRowMapper());
    }

    public void addAlbum(String groupID, String albumName,
                         String albumReleaseDate, String coverImage) {
        String sql = "CALL add_album(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, groupID);
            cs.setString(2, albumName);
            cs.setString(3, albumReleaseDate);
            cs.setString(4, coverImage);
            return cs;
        });
    }

    public void editAlbum(String albumID, String albumName,
                          String albumReleaseDate, String coverImage) {
        String sql = "CALL update_album(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, albumID);
            cs.setString(2, albumName);
            cs.setString(3, albumReleaseDate);
            cs.setString(4, coverImage);
            return cs;
        });
    }

    public void deleteAlbum(String albumID) {
        String sql = "CALL delete_album(?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, albumID);
            return cs;
        });
    }
}
