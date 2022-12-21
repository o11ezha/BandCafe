package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Song;
import com.kursovaya.BandCafe.Views.SongView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongViewRowMapper implements RowMapper<SongView> {

    @Override
    public SongView mapRow(ResultSet rs, int rowNum) throws SQLException {
        SongView songView = new SongView();
        songView.setGroupName(rs.getString("group_name"));
        songView.setAlbumName(rs.getString("album_name"));
        songView.setAlbumReleaseDate(rs.getString("album_release_date"));
        songView.setSongName(rs.getString("song_name"));
        songView.setSongDuration(rs.getString("song_duration_format"));
        songView.setSongMV(rs.getString("song_mv"));
        return songView;
    }
}
