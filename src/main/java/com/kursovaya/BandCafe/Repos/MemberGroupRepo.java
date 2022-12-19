package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.rowMappers.MemberGroupRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class MemberGroupRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<MemberGroup> findAll() {
        String sql = "SELECT * FROM member_group";
        return template.query(sql, new MemberGroupRowMapper() );
    }

    public String getGroupNameByID(String groupID){
        String sql = "SELECT group_name FROM member_group WHERE group_id = :groupID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("groupID", groupID);
        return template.queryForObject(sql, namedParameters, String.class);
    }

    public MemberGroup findGroupByGroupName(String groupName){
        String sql = "SELECT * FROM member_group WHERE group_name = :groupName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("groupName", groupName);
        return template.queryForObject(sql, namedParameters, new MemberGroupRowMapper());
    }

    public MemberGroup findGroupByGroupID(String groupID){
        String sql = "SELECT * FROM member_group WHERE group_id = :groupID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("groupID", groupID);
        return template.queryForObject(sql, namedParameters, new MemberGroupRowMapper());
    }
    public void addGroup(String groupName, String groupCountry,
                         String groupDebut, String groupFandom,
                         String groupDescription, String groupLogin,
                         String groupDisband){

        String sql = "CALL add_group(?,?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, groupName);
            cs.setString(2, groupCountry);
            cs.setString(3, groupDebut);
            cs.setString(4, groupFandom);
            cs.setString(5, groupDescription);
            cs.setString(6, groupLogin);
            cs.setString(7, groupDisband);
            return cs;
        });

    }

    public void editGroup(String groupID, String groupName,
                         String groupFandom, String groupManager,
                         String groupDispand){

        String sql = "CALL update_group(?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, groupID);
            cs.setString(2, groupName);
            cs.setString(3, groupFandom);
            cs.setString(4, groupManager);
            cs.setString(5, groupDispand);
            return cs;
        });

    }
}
