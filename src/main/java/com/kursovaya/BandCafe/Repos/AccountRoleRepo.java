package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.AccountRole;
import com.kursovaya.BandCafe.rowMappers.AccountRoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class AccountRoleRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public AccountRole findRoleName(Integer roleid) {
        String sql = "SELECT * FROM account_role WHERE role_id = :roleid";
        SqlParameterSource namedParameters = new MapSqlParameterSource("roleid", roleid);
        return template.queryForObject(sql, namedParameters, new AccountRoleRowMapper());
    }

    public List<AccountRole> findAll() {
        String sql = "SELECT * FROM account_role";
        return template.query(sql, new AccountRoleRowMapper());
    }

    public void editAccountRole(String adminLogin, String userLogin, Integer roleid) {
        String sql = "CALL update_role(?, ?, ?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, adminLogin);
            cs.setString(2, userLogin);
            cs.setInt(3, roleid);
            return cs;
        });
    }
}