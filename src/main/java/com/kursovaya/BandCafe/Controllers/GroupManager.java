package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bands")
public class GroupManager {

    @Autowired
    MemberGroupService memberGroupService;

    @GetMapping("")
    public String getGroups(Model model) {
        List<String> groupsID = memberGroupService.getAllGroupsID();
        List<String> groupDesc = memberGroupService.getAllGroupsDescriptionSource();
        List<String> groupNames = memberGroupService.getAllGroupsName();
        List<String> groupFandoms = memberGroupService.getAllGroupsFandomName();


        model.addAttribute("groupsID", groupsID);
        model.addAttribute("groupDesc", groupDesc);
        model.addAttribute("groupNames", groupNames);
        model.addAttribute("groupFandoms", groupFandoms);
        return "groups";
    }
}
