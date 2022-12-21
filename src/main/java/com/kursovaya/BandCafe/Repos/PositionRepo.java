package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class PositionRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Position> getAllPositions() {
        String sql = "SELECT * FROM position";
        return template.query(sql, (rs, rowNum) -> {
            Position position = new Position();
            position.setPositionCode(rs.getInt("position_code"));
            position.setPositionName(rs.getString("position_name"));
            return position;
        });
    }

    public List<Position> getAllMemberPositions(String memberID) {
        String sql = "SELECT * FROM position p JOIN member_position mp ON p.position_code = mp.position_code " +
                "WHERE mp.member_id = :memberID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberID", memberID);
        return template.query(sql, namedParameters, (rs, rowNum) -> {
            Position position = new Position();
            position.setPositionCode(rs.getInt("position_code"));
            position.setPositionName(rs.getString("position_name"));
            return position;
        });
    }
    public List<Integer> getAllMemberPositionsCodes(String memberID) {
        String sql = "SELECT p.position_code FROM position p JOIN member_position mp ON p.position_code = mp.position_code " +
                "WHERE mp.member_id = :memberID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberID", memberID);
        return template.query(sql, namedParameters, (rs, rowNum) -> (rs.getInt("position_code")));
    }

    public void addMemberPosition(String memberID, Integer positionCode) {
        String sql = "CALL add_member_position(?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, memberID);
            cs.setInt(2, positionCode);
            return cs;
        });
    }

    public void deleteMemberPosition(String memberID, Integer positionCode) {
        String sql = "CALL delete_member_position(?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, memberID);
            cs.setInt(2, positionCode);
            return cs;
        });
    }
}
