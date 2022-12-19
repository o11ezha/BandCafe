package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.GroupLabel;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Repos.LabelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    LabelRepo labelRepo;

    public void addLabel(GroupLabel labelgroup) {
        labelRepo.addLabel(labelgroup.getLabelName(), labelgroup.getLabelDirector(),
                labelgroup.getLabelCountry(), labelgroup.getLabelCity(),
                labelgroup.getLabelMainAddress(), labelgroup.getLabelDate(),
                labelgroup.getLabelDescSource());
    }

    public void editLabel(GroupLabel labelgroup) {
        labelRepo.editLabel(labelgroup.getLabelID(), labelgroup.getLabelName(), labelgroup.getLabelDirector(),
                labelgroup.getLabelCountry(), labelgroup.getLabelCity(),
                labelgroup.getLabelMainAddress());
    }

    public List<GroupLabel> findAll() {
       return labelRepo.findAll();
    }

    public List<String> getAllLabelsName() {
        List<GroupLabel> allLabels =  findAll();
        return allLabels.stream().map(GroupLabel::getLabelName).toList();
    }

    public GroupLabel getLabelByLabelID(String labelID) {
        return labelRepo.findLabelByLabelID(labelID);
    }

    public GroupLabel getLabelByLabelName(String labelName) {
        return labelRepo.findLabelByLabelName(labelName);
    }

}
