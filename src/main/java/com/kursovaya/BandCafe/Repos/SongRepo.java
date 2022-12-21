package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Views.SongView;
import com.kursovaya.BandCafe.rowMappers.SongRowMapper;
import com.kursovaya.BandCafe.rowMappers.SongViewRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class SongRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public Song getSongByName(String songName) {
        String sql = "SELECT * FROM song WHERE song_name = :songName";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("songName", songName);
        return template.queryForObject(sql, sqlParameterSource, new SongRowMapper());
    }

    public List<Song> getSongsByAlbumName(String albumName) {
        String sql = "SELECT * FROM song s JOIN album a ON s.album_id = a.album_id WHERE a.album_name = :albumName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("albumName", albumName);
        return template.query(sql, namedParameters, new SongRowMapper());
    }

    public List<SongView> getSongsView() {
        String sql = "SELECT * FROM song_info;";
        return template.query(sql, new SongViewRowMapper());
    }

    public List<SongView> getSongsViewByAlbumName(String albumName) {
        String sql = "SELECT * FROM song_info WHERE album_name = :albumName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("albumName", albumName);
        return template.query(sql, namedParameters, new SongViewRowMapper());
    }

    public void addSong(String albumID, String songName, Integer songDuration, String songMV) {
        String sql = "CALL add_song(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, albumID);
            cs.setString(2, songName);
            cs.setInt(3, songDuration);
            cs.setString(4, songMV);
            return cs;
        });
    }

    public void editSong(String songID, String songName, Integer songDuration, String songMV) {
        String sql = "CALL update_song(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, songID);
            cs.setString(2, songName);
            cs.setInt(3, songDuration);
            cs.setString(4, songMV);
            return cs;
        });
    }

    public void deleteSong(String songID) {
        String sql = "CALL delete_song(?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, songID);
            return cs;
        });
    }
}
