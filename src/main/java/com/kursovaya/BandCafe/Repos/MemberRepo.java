package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.rowMappers.MemberGroupRowMapper;
import com.kursovaya.BandCafe.rowMappers.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class MemberRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Member> getMembersByGroupID(String groupID) {
        String sql = "SELECT * FROM member WHERE group_id = :groupID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("groupID", groupID);
        return template.query(sql, namedParameters, new MemberRowMapper());
    }

    public List<String> getAllMembersLogins() {
        String sql = "SELECT account_login FROM profile p" +
                " JOIN  member_profile mp ON p.profile_id = mp.profile_id" +
                " JOIN member m ON m.member_id = mp.member_id;";
        return template.query(sql, (rs, rowNum) -> rs.getString("account_login"));
    }

    public String getSpecialStageNameFromProfID(String profileID) {
        String sql = "SELECT member_stage_name FROM profile p" +
                " JOIN  member_profile mp ON p.profile_id = mp.profile_id" +
                " JOIN member m ON m.member_id = mp.member_id WHERE p.profile_id = :profileID;";
        SqlParameterSource namedParameters = new MapSqlParameterSource("profileID", profileID);
        return template.queryForObject(sql, namedParameters, (rs, rowNum) -> rs.getString("member_stage_name"));
    }
    public String getSpecialDateFromProfID(String profileID) {
        String sql = "SELECT member_date_of_birth FROM profile p" +
                " JOIN  member_profile mp ON p.profile_id = mp.profile_id" +
                " JOIN member m ON m.member_id = mp.member_id WHERE p.profile_id = :profileID;";
        SqlParameterSource namedParameters = new MapSqlParameterSource("profileID", profileID);
        return template.queryForObject(sql, namedParameters, (rs, rowNum) -> rs.getString("member_date_of_birth"));
    }

    public List<String> getAllMembersStageNames() {
        String sql = "SELECT member_stage_name FROM member";
        return template.query(sql, (rs, rowNum) -> rs.getString("member_stage_name"));
    }

    public Member getMemberByMemberStageName(String memberStageName) {
        String sql = "SELECT * FROM member WHERE member_stage_name = :memberStageName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberStageName", memberStageName);
        return template.queryForObject(sql, namedParameters, new MemberRowMapper());
    }

    public Member getMemberByMemberID(String memberID) {
        String sql = "SELECT * FROM member WHERE member_id = :memberID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberID", memberID);
        return template.queryForObject(sql, namedParameters, new MemberRowMapper());
    }

    public void addMember(String memberLogin, String labelID,
                          String groupID, String memberName,
                          String memberStageName, String memberBirthday,
                          String memberCountry, String memberCity,
                          String memberDesc, Integer memberHeight){
        String sql = "CALL add_member(?,?,?,?,?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, memberLogin);
            cs.setString(2, labelID );
            cs.setString(3, groupID);
            cs.setString(4, memberName);
            cs.setString(5, memberStageName);
            cs.setString(6, memberBirthday);
            cs.setString(7, memberCountry);
            cs.setString(8, memberCity);
            cs.setString(9, memberDesc);
            cs.setInt(10, memberHeight);
            return cs;
        });
    }

    public void editMember(String memberID, String labelID,
                          String groupID, String memberName,
                          String memberStageName, String memberBirthday,
                          String memberCountry, String memberCity,
                          Integer memberHeight) {

        String sql = "CALL update_member(?,?,?,?,?,?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, memberID);
            cs.setString(2, labelID);
            cs.setString(3, groupID);
            cs.setString(4, memberName);
            cs.setString(5, memberStageName);
            cs.setString(6, memberBirthday);
            cs.setString(7, memberCountry);
            cs.setString(8, memberCity);
            cs.setInt(9, memberHeight);
            return cs;
        });
    }

        public void deleteMember(String memberID){
            String sql = "CALL delete_member(?)";
            template.getJdbcOperations().update(connection -> {
                CallableStatement cs = connection.prepareCall(sql);
                cs.setString(1, memberID);
                return cs;
            });
        }
}
