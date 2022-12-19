package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.rowMappers.MemberGroupRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberGroupRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<MemberGroup> findAll() {
        String sql = "SELECT * FROM member_group";
        return template.query(sql, new MemberGroupRowMapper() );
    }
}
