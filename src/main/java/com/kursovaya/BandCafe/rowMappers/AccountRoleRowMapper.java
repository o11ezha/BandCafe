package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.AccountRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRoleRowMapper implements RowMapper<AccountRole> {
    @Override
    public AccountRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountRole accountRole = new AccountRole();
        accountRole.setRoleID(rs.getInt("role_id"));
        accountRole.setRoleName(rs.getString("role_name"));
        return accountRole;
    }
}
