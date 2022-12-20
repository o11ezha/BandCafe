package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setMemberID(rs.getString("member_id"));
        member.setMemberName(rs.getString("member_name"));
        member.setMemberStageName(rs.getString("member_stage_name"));
        member.setMemberDateOfBirth(rs.getString("member_date_of_birth"));
        member.setMemberCountry(rs.getString("member_country"));
        member.setMemberCity(rs.getString("member_city"));
        member.setMemberHeight(rs.getInt("member_height"));
        member.setMemberDescSource(rs.getString("member_description_source"));
        member.setLabelID(rs.getString("label_id"));
        member.setGroupID(rs.getString("group_id"));
        return member;
    }
}
