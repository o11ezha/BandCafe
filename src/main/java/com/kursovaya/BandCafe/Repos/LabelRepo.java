package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.AccountRole;
import com.kursovaya.BandCafe.Entities.GroupLabel;
import com.kursovaya.BandCafe.rowMappers.AccountRoleRowMapper;
import com.kursovaya.BandCafe.rowMappers.LabelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

@Repository
public class LabelRepo {
    @Autowired
    NamedParameterJdbcTemplate template;

    public List<GroupLabel> findAll() {
        String sql = "SELECT * FROM group_label";
        return template.query(sql, new LabelRowMapper());
    }

    public GroupLabel findLabelByLabelID(String labelID) {
        String sql = "SELECT * FROM group_label WHERE label_id = :labelID";
        SqlParameterSource parameterSource = new MapSqlParameterSource("labelID", labelID);
        return template.queryForObject(sql, parameterSource, new LabelRowMapper());
    }
    public GroupLabel findLabelByLabelName(String labelName) {
        String sql = "SELECT * FROM group_label WHERE label_name = :labelName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("labelName", labelName);
        return template.queryForObject(sql, parameterSource, new LabelRowMapper());
    }

    public void addLabel(String labelname, String labelDirector,
                         String labelcountry, String labelcity,
                         String labeladdress, String labelDate, String labelDescription) {
        String sql = "CALL add_label(?,?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, labelname);
            cs.setString(2, labelDirector);
            cs.setString(3, labelcountry);
            cs.setString(4, labelcity);
            cs.setString(5, labeladdress);
            cs.setString(6, labelDate);
            cs.setString(7, labelDescription);
            return cs;
        });
    }

    public void editLabel(String labelID, String labelname, String labelDirector,
                          String labelcountry, String labelcity,
                          String labeladdress) {
        String sql = "CALL update_label(?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, labelID);
            cs.setString(2, labelname);
            cs.setString(3, labelDirector);
            cs.setString(4, labelcountry);
            cs.setString(5, labelcity);
            cs.setString(6, labeladdress);
            return cs;
        });

    }

}
