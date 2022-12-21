package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.GroupLabel;
import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Services.LabelService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    LabelService labelService;

    @Autowired
    MemberGroupService memberGroupService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("/member/{memberStageName}")
    public String returnMember(@PathVariable String memberStageName, Model model) throws FileNotFoundException {
        Member member = memberService.getMemberByMemberStageName(memberStageName);
        GroupLabel label = labelService.getLabelByLabelID(member.getLabelID());
        MemberGroup memberGroup = memberGroupService.getGroupByGroupID(member.getGroupID());
        File file = new File(uploadPath + "/MemberDesc/" + member.getMemberDescSource());
        Scanner sc = new Scanner(file);

        model.addAttribute("member", member);
        model.addAttribute("memberdesc",  sc.nextLine());
        model.addAttribute("label", label.getLabelName());
        model.addAttribute("memberGroup", memberGroup.getGroupName());

        return "memberView";
    }

    @GetMapping("{groupName}/member/add")
    public String addMember(@PathVariable("groupName") String groupName, Model model){
        countryList(model);
        Member member = new Member();
        List<GroupLabel> labels = labelService.findAll();
        member.setGroupID(memberGroupService.getGroupByGroupName(groupName).getGroupID());
        model.addAttribute("member", member);
        model.addAttribute("group", memberGroupService.getGroupByGroupName(groupName).getGroupID());
        model.addAttribute("labels", labels);
        return "addMember";
    }

    @PostMapping("{groupName}/member/add")
    public String addMember(@PathVariable("groupName") String groupName,
                            @Validated Member member,
                            BindingResult bindingResult,
                            String errorName,
                            String errorStageName,
                            String errorDateOfBirth,
                            String errorCountry,
                            String errorCity,
                            String errorLabelID,
                            String errorLogin,
                            Model model,
                            @RequestParam("loginMember") String loginMember,
                            @RequestParam("filemember") MultipartFile filemember) throws UnsupportedEncodingException {
        model.addAttribute("member", member);
        model.addAttribute("group", memberGroupService.getGroupByGroupName(groupName).getGroupID());

        identicalParts(model);

        System.out.println(member.toString());

        if (bindingResult.hasErrors()) {
            System.out.println(
                    //print all errors
                    bindingResult.getAllErrors()
            );
            return "addMember";
        }

        List<String> memberLogins = memberService.getAllMembersLogins();

        if (loginMember.equals("") || loginMember == null) {
            errors(errorLogin, "Введите аккаунт мембера", "addMember", model);
        }

        if (memberLogins.contains(loginMember)){
            model.addAttribute("errorLogin", "Такой мембер уже существует");
            return "addMember";
        }


        if (member.getMemberName().equals("") || member.getMemberName() == null) {
            errors(errorName, "Введите имя мембера", "addMember", model);
        }

        if (member.getMemberStageName().equals("") || member.getMemberStageName() == null) {
            errors(errorStageName, "Введите сценарическое имя мембера", "addMember", model);
        }
        List<String> allMembersStageNames = memberService.getAllMembersStageNames();

        if (allMembersStageNames.contains(member.getMemberStageName()) && !member.getMemberStageName().equals(member2.getMemberStageName())) {
            errorStageName = "Такое сценическое имя уже существует";
            model.addAttribute("errorStageName", errorStageName);
            return "addMember";
        }

        if (member.getMemberDateOfBirth().equals("") || member.getMemberDateOfBirth() == null) {
            errors(errorDateOfBirth, "Введите день рождения мембера", "addMember", model);
        }

        if (member.getMemberCountry().equals("") || member.getMemberCountry() == null) {
            errors(errorCountry, "Введите страну рождения мембера", "addMember", model);
        }

        if (member.getMemberCity().equals("") || member.getMemberCity() == null) {
            errors(errorCity, "Введите город рождения мембера", "addMember", model);
        }

        if (member.getLabelID().equals("") || member.getLabelID() == null) {
            errors(errorLabelID, "Введите агентство мембера", "addMember", model);
        }

        if (!Objects.requireNonNull(filemember.getOriginalFilename()).equals("")
                && filemember.getOriginalFilename() != null) {
            File uploadDir = new File(uploadPath + "/MemberDesc");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filemember.getOriginalFilename();


            try {
                filemember.transferTo(new File(uploadDir + "/" + resultFilename));
                member.setMemberDescSource(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (member.getMemberHeight() == null) {
            member.setMemberHeight(0);
        }

        System.out.println(member.toString());
        System.out.println(loginMember);

        memberService.addMember(member,loginMember);
        return "redirect:/bands/" + URLEncoder.encode(groupName, "UTF-8");
    }

    static Member member2 = new Member();

    @GetMapping("/member/edit/{memberID}")
    public String editMember(@PathVariable String memberID, Model model) {
        Member member = memberService.getMemberByMemberID(memberID);
        member2 = member;
        countryList(model);
        model.addAttribute("member", member);
        identicalParts(model);
        return "editMember";
    }

    @PostMapping("/member/edit/{memberID}")
    public String editMember(@ModelAttribute("member") @Validated Member member,
                             BindingResult bindingResult,
                             @PathVariable ("memberID") String memberID,
                             String errorName,
                             String errorStageName,
                             String errorDateOfBirth,
                             String errorCountry,
                             String errorCity,
                             String errorLabelID,
                             String errorGroupID,
                             Model model) throws UnsupportedEncodingException {
        model.addAttribute("member", member);

        identicalParts(model);

        System.out.println(member2.toString());
        System.out.println(member.toString());

        member.setMemberDescSource(member2.getMemberDescSource());

        if (bindingResult.hasErrors()) {
            System.out.println(
                    //print all errors
                    bindingResult.getAllErrors()
            );
            return "editMember";
        }

        if (member.getMemberName().equals("") || member.getMemberName() == null) {
            errors(errorName, "Введите имя мембера", "editMember", model);
        }

        if (member.getMemberStageName().equals("") || member.getMemberStageName() == null) {
            errors(errorStageName, "Введите сценарическое имя мембера", "editMember", model);
        }
        List<String> allMembersStageNames = memberService.getAllMembersStageNames();
        System.out.println(allMembersStageNames);

        if (allMembersStageNames.contains(member.getMemberStageName()) && !member.getMemberStageName().equals(member2.getMemberStageName())) {
            errorStageName = "Такое сценическое имя уже существует";
            model.addAttribute("errorStageName", errorStageName);
            return "editMember";
        }

        if (member.getMemberDateOfBirth().equals("") || member.getMemberDateOfBirth() == null) {
            errors(errorDateOfBirth, "Введите день рождения мембера", "editMember", model);
        }

        if (member.getMemberCountry().equals("") || member.getMemberCountry() == null) {
            errors(errorCountry, "Введите страну рождения мембера", "editMember", model);
        }

        if (member.getMemberCity().equals("") || member.getMemberCity() == null) {
            errors(errorCity, "Введите город рождения мембера", "editMember", model);
        }

        if (member.getLabelID().equals("") || member.getLabelID() == null) {
            errors(errorLabelID, "Введите агентство мембера", "editMember", model);
        }

        if (member.getGroupID().equals("") || member.getGroupID() == null) {
            errors(errorGroupID, "Введите агентство мембера", "editMember", model);
        }

        memberService.editMember(member);
        return "redirect:/member/" + URLEncoder.encode(member.getMemberStageName(),"UTF-8") ;
    }
    @GetMapping("/member/deletemem/{memberID}")
    public String deleteAccount(@PathVariable("memberID") String memberID){
        memberService.deleteMember(memberID);
        return "redirect:/bands";
    }

    public String errors(String finalerror, String texterror,
                         String returnPage, Model model) {

        finalerror = texterror;
        model.addAttribute( '"' + finalerror + '"', finalerror);
        return '"' +  returnPage + '"';
    }
    public void countryList(Model model){
        List<String> countries = new ArrayList<>();

        for (String country : Locale.getISOCountries())
        {countries.add(new Locale("", country).getDisplayCountry());}
        model.addAttribute("countries", countries);

    }

    public void identicalParts(Model model){
        List<GroupLabel> labels = labelService.findAll();
        List<MemberGroup> groups = memberGroupService.getAllGroups();

        countryList(model);

        model.addAttribute("labels", labels);
        model.addAttribute("groups", groups);
    }

}
