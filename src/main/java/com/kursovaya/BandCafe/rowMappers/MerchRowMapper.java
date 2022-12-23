package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Merch;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchRowMapper implements RowMapper<Merch> {
    @Override
    public Merch mapRow(ResultSet rs, int rowNum) throws SQLException {
        Merch merch = new Merch();
        merch.setMerchID(rs.getString("merch_id"));
        merch.setMerchName(rs.getString("merch_name"));
        merch.setMerchPrice(rs.getBigDecimal("merch_price"));
        merch.setMerchStatus(rs.getBoolean("merch_status"));
        merch.setMerchAmount(rs.getInt("merch_amount"));
        merch.setMerchDescSource(rs.getString("merch_description_source"));
        merch.setGroupID(rs.getString("group_id"));
        return merch;
    }
}
