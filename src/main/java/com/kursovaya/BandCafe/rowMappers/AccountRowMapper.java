package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setAccountLogin(rs.getString("account_login"));
        account.setAccountPassword(rs.getString("account_password"));
        account.setRoleID(rs.getInt("role_id"));
        return account;
    }
}
