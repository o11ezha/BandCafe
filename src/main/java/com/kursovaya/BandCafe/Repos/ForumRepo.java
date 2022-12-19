package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Forum;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.rowMappers.ForumRowMapper;
import com.kursovaya.BandCafe.rowMappers.MemberGroupRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForumRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Forum> findAll() {
        String sql = "SELECT * FROM forum";
        return template.query(sql, new ForumRowMapper() );
    }
}
