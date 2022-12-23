package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.Merch;
import com.kursovaya.BandCafe.Views.MerchView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchViewRowMapper implements RowMapper<MerchView> {

    @Override
    public MerchView mapRow(ResultSet rs, int rowNum) throws SQLException {
        MerchView merch = new MerchView();
        merch.setMerchID(rs.getString("merch_id"));
        merch.setMerchName(rs.getString("merch_name"));
        merch.setMerchPrice(rs.getFloat("merch_price"));
        merch.setMerchStatus(rs.getBoolean("merch_status"));
        merch.setMerchDesc(rs.getString("merch_description_source"));
        merch.setMerchGroupName(rs.getString("group_name"));
        merch.setMerchFandomName(rs.getString("group_fandom_name"));
        return merch;
    }
}
