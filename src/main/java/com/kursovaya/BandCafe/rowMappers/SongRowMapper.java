package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRowMapper implements RowMapper<Song> {

    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();
        song.setSongID(rs.getString("song_id"));
        song.setSongName(rs.getString("song_name"));
        song.setSongDuration(rs.getInt("song_duration"));
        song.setSongMV(rs.getString("song_MV"));
        song.setAlbumID(rs.getString("album_id"));
        return song;
    }
}
