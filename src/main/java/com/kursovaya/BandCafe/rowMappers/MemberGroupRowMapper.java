package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.MemberGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberGroupRowMapper implements RowMapper<MemberGroup> {

    @Override
    public MemberGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroupID(rs.getString("group_id"));
        memberGroup.setGroupName(rs.getString("group_name"));
        memberGroup.setGroupCountry(rs.getString("group_country"));
        memberGroup.setGroupDebutDate(rs.getDate("group_debut_date"));
        memberGroup.setGroupDisbandDate(rs.getDate("group_disband_date"));
        memberGroup.setGroupFandom(rs.getString("group_fandom_name"));
        memberGroup.setGroupDescSource(rs.getString("group_description_source"));
        memberGroup.setGroupManager(rs.getString("group_manager"));
        return memberGroup;
    }
}
