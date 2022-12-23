package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Repos.MemberGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberGroupService {

    @Autowired
    MemberGroupRepo memberGroupRepo;

    public void addGroup(MemberGroup memberGroup) {
        memberGroupRepo.addGroup(memberGroup.getGroupName(), memberGroup.getGroupCountry(),
                memberGroup.getGroupDebutDate(), memberGroup.getGroupFandom(),
                memberGroup.getGroupDescSource(), memberGroup.getGroupManager(),
                memberGroup.getGroupDisbandDate());
    }

    public void editGroup(MemberGroup memberGroup) {
        memberGroupRepo.editGroup(memberGroup.getGroupID(), memberGroup.getGroupName(),
                memberGroup.getGroupFandom(), memberGroup.getGroupManager(),
                memberGroup.getGroupDisbandDate());
    }

    public List<MemberGroup> getAllGroups() {
        return memberGroupRepo.findAll();
    }

    public List<String> getAllGroupsID() {
        List<MemberGroup> memberGroups = getAllGroups();
        return memberGroups.stream().map(MemberGroup::getGroupID).toList();
    }

    public List<String> getAllGroupsName() {
        List<MemberGroup> memberGroups = getAllGroups();
        return memberGroups.stream().map(MemberGroup::getGroupName).toList();
    }

    public List<String> getAllGroupsDescriptionSource() {
        List<MemberGroup> memberGroups = getAllGroups();
        return memberGroups.stream().map(MemberGroup::getGroupDescSource).toList();
    }

    public List<String> getAllGroupsFandomName() {
        List<MemberGroup> memberGroups = getAllGroups();
        return memberGroups.stream().map(MemberGroup::getGroupFandom).toList();
    }

    public MemberGroup getGroupByGroupName(String groupName) {
        return memberGroupRepo.findGroupByGroupName(groupName);
    }

    public MemberGroup getGroupByGroupID(String groupID) {
        return memberGroupRepo.findGroupByGroupID(groupID);
    }
    public List<MemberGroup> getAllManagerGroups(String mangerLogin) {
        return memberGroupRepo.getAllManagerGroups(mangerLogin);
    }

    public String getGroupNameByID(String groupID){
        return memberGroupRepo.getGroupNameByID(groupID);
    }
}
