package com.kursovaya.BandCafe.rowMappers;

import com.kursovaya.BandCafe.Entities.GroupLabel;
import org.springframework.jdbc.core.RowMapper;

import java.util.Date;

public class LabelRowMapper implements RowMapper<GroupLabel> {
    @Override
    public GroupLabel mapRow(java.sql.ResultSet resultSet, int i) throws java.sql.SQLException {
        GroupLabel label = new GroupLabel();
        label.setLabelID(resultSet.getString("label_id"));
        label.setLabelName(resultSet.getString("label_name"));
        label.setLabelDirector(resultSet.getString("label_director"));
        label.setLabelCountry(resultSet.getString("label_country"));
        label.setLabelCity(resultSet.getString("label_city"));
        label.setLabelMainAddress(resultSet.getString("label_main_address"));
        label.setLabelDate(resultSet.getString("label_date"));
        label.setLabelDescSource(resultSet.getString("label_description_source"));
        return label;
    }
}
