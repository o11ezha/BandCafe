package com.kursovaya.BandCafe.Repos;

import com.kursovaya.BandCafe.Entities.Account;
import com.kursovaya.BandCafe.rowMappers.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
public class AccountRepo {

    @Autowired
    NamedParameterJdbcTemplate template;

    public List<Account> getAll() {
        String sql = "SELECT * FROM account";
        return template.query(sql, new AccountRowMapper());
    }

    public List<Account> getAllManager() {
        String sql = "SELECT * FROM account WHERE role_id = 1";
        return template.query(sql, new AccountRowMapper());
    }

    public Account findByLogin(String login) {
        String sql = "SELECT * FROM account WHERE account_login = :login";
        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);
        return template.queryForObject(sql, namedParameters, new AccountRowMapper());
    }

    public void registration(String login, String password) {
        String sql = "CALL add_user(?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, login);
            cs.setString(2, password);
            return cs;
        });
    }

    public void editAccount(String oldlogin, String newlogin, String newpassword, Integer newroleid) {
        String sql = "CALL update_user(?,?,?,?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, oldlogin);
            cs.setString(2, newlogin);
            cs.setString(3, newpassword);
            cs.setInt(4, newroleid);
            return cs;
        });
    }

    public void deleteAccount(String login) {
        String sql = "CALL delete_user(?)";
        template.getJdbcOperations().update(connection -> {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, login);
            return cs;
        });
    }
}
