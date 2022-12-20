package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setProfileID(rs.getString("profile_id"));
        profile.setProfileAvatarSource(rs.getString("profile_avatar_source"));
        profile.setProfileDateOfBirth(rs.getString("profile_date_of_birth"));
        profile.setProfileDesc(rs.getString("profile_description"));
        profile.setAccountLogin(rs.getString("account_login"));
        return profile;
    }
}
