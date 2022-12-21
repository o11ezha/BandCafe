package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Album;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();
        album.setAlbumID(rs.getString("album_id"));
        album.setAlbumName(rs.getString("album_name"));
        album.setAlbumReleaseDate(rs.getString("album_release_date"));
        album.setAlbumCover(rs.getString("album_cover"));
        album.setGroupOwnerID(rs.getString("group_owner_id"));
        return album;
    }
}
