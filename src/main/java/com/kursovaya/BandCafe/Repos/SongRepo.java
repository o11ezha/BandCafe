package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.rowMappers.SongRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Song> getSongsByAlbumName(String albumName) {
        String sql = "SELECT * FROM song s JOIN album a ON s.album_id = a.album_id WHERE a.album_name = :albumName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("albumName", albumName);
        return template.query(sql, namedParameters, new SongRowMapper());
    }
}
