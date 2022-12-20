package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Repos.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepo memberRepo;

    public void addMember(Member member, String accountLogin){
        memberRepo.addMember(accountLogin, member.getLabelID(),
                member.getGroupID(), member.getMemberName(),
                member.getMemberStageName(), member.getMemberDateOfBirth(),
                member.getMemberCountry(), member.getMemberCity(),
                member.getMemberDescSource(), member.getMemberHeight());
    }

    public void editMember(Member member) {
        memberRepo.editMember(member.getMemberID(), member.getLabelID(),
                member.getGroupID(), member.getMemberName(),
                member.getMemberStageName(), member.getMemberDateOfBirth(),
                member.getMemberCountry(), member.getMemberCity(),
                member.getMemberHeight());
    }

    public void deleteMember(String memberID) {
        memberRepo.deleteMember(memberID);
    }

    public List<Member> getMembersByGroupID(String groupID) {
        return memberRepo.getMembersByGroupID(groupID);
    }

    public List<String> getAllMembersStageNames() {
        return memberRepo.getAllMembersStageNames();
    }

    public List<String> getAllMembersLogins() {
        return memberRepo.getAllMembersLogins();
    }
    public String getSpecialStageNameFromProfID(String profileID) {
            return memberRepo.getSpecialStageNameFromProfID(profileID);
    }

    public String getSpecialDateFromProfID(String profileID) {
            return memberRepo.getSpecialDateFromProfID(profileID);
    }

    public Member getMemberByMemberStageName(String memberStageName) {
        return memberRepo.getMemberByMemberStageName(memberStageName);
    }

    public Member getMemberByMemberID(String memberID) {
        return memberRepo.getMemberByMemberID(memberID);
    }
}
