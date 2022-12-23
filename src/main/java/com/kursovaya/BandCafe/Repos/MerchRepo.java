package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Merch;
import com.kursovaya.BandCafe.Views.MerchView;
import com.kursovaya.BandCafe.rowMappers.MerchRowMapper;
import com.kursovaya.BandCafe.rowMappers.MerchViewRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.List;

@Repository
public class MerchRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<MerchView> getAllMerchFromView(){
        String sql = "SELECT * FROM merch_info;";
        return template.query(sql, new MerchViewRowMapper());
    }

    public Merch getMerchByName(String merchName){
        String sql = "SELECT * FROM merch WHERE merch_name = :merchName;";
        SqlParameterSource source = new MapSqlParameterSource("merchName", merchName);
        return template.queryForObject(sql, source, new MerchRowMapper());
    }

    public Merch getMerchByID(String merchID){
        String sql = "SELECT * FROM merch WHERE merch_id = :merchID;";
        SqlParameterSource source = new MapSqlParameterSource("merchID", merchID);
        return template.queryForObject(sql, source, new MerchRowMapper());
    }

    public String getManagerID(String merchID){
        String sql ="SELECT group_manager FROM member_group JOIN merch ON member_group.group_id = merch.group_id WHERE merch_id = :merch_id;";
        SqlParameterSource source = new MapSqlParameterSource("merchID", merchID);
        return template.queryForObject(sql, source, String.class);
    }

    public List<String> getMerchIDsByManagerLogin(String login){
        String sql = "SELECT merch_id FROM merch m " +
                "JOIN member_group mg ON m.group_id = mg.group_id " +
                "WHERE mg.group_manager = :login;";
        SqlParameterSource source = new MapSqlParameterSource("login", login);
        return template.query(sql, source,  (rs, rowNum) -> rs.getString("merch_id"));
    }

    public void addMerch(String login, String merchName, BigDecimal merchPrice,
                         Integer merchAmount, String merchDesc,
                         String groupID){
        String sql = "CALL add_merch(?, ?, ?, ?, ?, ?);";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, login);
            cs.setString(2, merchName);
            cs.setBigDecimal(3, merchPrice);
            cs.setInt(4, merchAmount);
            cs.setString(5, merchDesc);
            cs.setString(6, groupID);
            return cs;
        });
    }

    public void editMerch(String merchID, String merchName, BigDecimal merchPrice,
                          Boolean merchStatus, Integer merchAmount, String groupID){
        String sql = "CALL update_merch(?, ?, ?, ?, ?, ?);";
        template.getJdbcOperations().update(connection ->{
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, merchID);
            cs.setString(2, merchName);
            cs.setBigDecimal(3, merchPrice);
            cs.setBoolean(4, merchStatus);
            cs.setInt(5, merchAmount);
            cs.setString(6, groupID);
            return cs;
        });
    }

}
