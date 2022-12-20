package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Profile;
import com.kursovaya.BandCafe.rowMappers.MemberRowMapper;
import com.kursovaya.BandCafe.rowMappers.ProfileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;

@Repository
public class ProfileRepo {


    @Autowired
    NamedParameterJdbcTemplate template;

    public Profile findProfileByProfileID(String profileID) {
        String sql = "SELECT * FROM profile WHERE profile_id = :profileID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("profileID", profileID);
        return template.queryForObject(sql, namedParameters, new ProfileRowMapper());
    }

    public String findProfileByLogin(String login) {
        String sql = "SELECT profile_id FROM profile WHERE account_login = :login";
        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);
        return template.queryForObject(sql, namedParameters, String.class);
    }

    public void editProfile(String accountLogin, String avatarImg,
                            String birthdayDate, String profileDesc){
        String sql = "CALL update_profile(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, accountLogin);
            cs.setString(2, avatarImg);
            cs.setString(3, birthdayDate);
            cs.setString(4, profileDesc);
            return cs;
        });
    }
}
