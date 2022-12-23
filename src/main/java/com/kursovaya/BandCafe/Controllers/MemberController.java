package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.GroupLabel;
import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Entities.Position;
import com.kursovaya.BandCafe.Services.LabelService;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.MemberService;
import com.kursovaya.BandCafe.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
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

    @Autowired
    PositionService positionService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("/member/{memberStageName}")
    public String returnMember(@PathVariable String memberStageName, Model model) throws FileNotFoundException {
        Member member = memberService.getMemberByMemberStageName(memberStageName);
        GroupLabel label = labelService.getLabelByLabelID(member.getLabelID());
        MemberGroup memberGroup = memberGroupService.getGroupByGroupID(member.getGroupID());
        List<Position> memberPositions = positionService.getAllMemberPositions(member.getMemberID());
        File file = new File(uploadPath + "/MemberDesc/" + member.getMemberDescSource());
        Scanner sc = new Scanner(file);

        model.addAttribute("member", member);
        model.addAttribute("memberdesc",  sc.nextLine());
        model.addAttribute("label", label.getLabelName());
        model.addAttribute("memberGroup", memberGroup.getGroupName());
        model.addAttribute("memberPositions", memberPositions);

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
            model.addAttribute("errorLogin", "Введите логин");
            return "addMember";
        }

        if (memberLogins.contains(loginMember)){
            model.addAttribute("errorLogin", "Такой мембер уже существует");
            return "addMember";
        }


        if (member.getMemberName().equals("") || member.getMemberName() == null) {
            model.addAttribute("errorName", "Введите имя мембера");
            return "addMember";
        }

        if (member.getMemberStageName().equals("") || member.getMemberStageName() == null) {
            model.addAttribute("errorStageName", "Введите ссценарическое имя мембера");
            return "addMember";
        }
        List<String> allMembersStageNames = memberService.getAllMembersStageNames();

        if (allMembersStageNames.contains(member.getMemberStageName()) && !member.getMemberStageName().equals(member2.getMemberStageName())) {
            model.addAttribute("errorStageName", "Такое сценическое имя уже существует");
            return "addMember";
        }

        if (member.getMemberDateOfBirth().equals("") || member.getMemberDateOfBirth() == null) {
            model.addAttribute("errorDateOfBirth", "Введите дату рождения мембера");
            return "addMember";
        }

        if (member.getMemberCountry().equals("") || member.getMemberCountry() == null) {
            model.addAttribute("errorCountry", "Введите страну рождаения мембера");
            return "addMember";
        }

        if (member.getMemberCity().equals("") || member.getMemberCity() == null) {
            model.addAttribute("errorCity", "Введите город рождения мембера");
            return "addMember";
        }

        if (member.getLabelID().equals("") || member.getLabelID() == null) {
            model.addAttribute("errorLabelID", "Введите агентство мембера");
            return "addMember";
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
            model.addAttribute("errorName", "Введите имя мембера");
            return "editMember";
        }

        if (member.getMemberStageName().equals("") || member.getMemberStageName() == null) {
            model.addAttribute("errorStageName", "Введите сценарическое имя мембера");
            return "editMember";
        }
        List<String> allMembersStageNames = memberService.getAllMembersStageNames();
        System.out.println(allMembersStageNames);

        if (allMembersStageNames.contains(member.getMemberStageName()) && !member.getMemberStageName().equals(member2.getMemberStageName())) {
            model.addAttribute("errorStageName", "Такое сценическое имя уже существует");
            return "editMember";
        }

        if (member.getMemberDateOfBirth().equals("") || member.getMemberDateOfBirth() == null) {
            model.addAttribute("errorDateOfBirth", "Введите день рождения мембера");
            return "editMember";
        }

        if (member.getMemberCountry().equals("") || member.getMemberCountry() == null) {
            model.addAttribute("errorCountry", "Введите страну рождения мембера");
            return "editMember";
        }

        if (member.getMemberCity().equals("") || member.getMemberCity() == null) {
            model.addAttribute("errorCity", "Введите город рождения мембера");
            return "editMember";
        }

        if (member.getLabelID().equals("") || member.getLabelID() == null) {
            model.addAttribute("errorLabelID", "Введите агентство мембера");
            return "editMember";
        }

        if (member.getGroupID().equals("") || member.getGroupID() == null) {
            model.addAttribute("errorGroupID", "Введите группу мембера");
            return "editMember";
        }

        memberService.editMember(member);
        return "redirect:/member/" + URLEncoder.encode(member.getMemberStageName(),"UTF-8") ;
    }

    @GetMapping("/member/editPos/{memberID}")
    public String editMemberPos(@PathVariable String memberID, Model model) {
        Member member = memberService.getMemberByMemberID(memberID);
        List<Position> positions = positionService.getAllPositions();
        List<Integer> memberPositions = positionService.getAllMemberPositionsCodes(memberID);

        model.addAttribute("member", member);
        model.addAttribute("positions", positions);
        model.addAttribute("memberPositions", memberPositions);
        return "editMemberPos";
    }

    @PostMapping("/member/editPos/{memberID}")
    public String editMemberPos(@PathVariable("memberID") String memberID,
                                @RequestBody MultiValueMap<String, String> form,
                                Model model) throws UnsupportedEncodingException {
        Member member = memberService.getMemberByMemberID(memberID);
        List<String> newPositions = new ArrayList<>();

        for (String key : form.keySet()) {
            newPositions.addAll(form.get(key));
        }
        newPositions.remove(newPositions.get(0));

        List<Integer> memberPositions = positionService.getAllMemberPositionsCodes(memberID);
        List<Integer>  diffOld  = memberPositions.stream().filter(element -> !newPositions.contains(element.toString())).toList();
        List<String> diffNew = newPositions.stream().filter(element -> !memberPositions.contains(Integer.parseInt(element))).toList();
        List<Integer> diffNewInt = new ArrayList<>();

        for (String pos : diffNew) {
            diffNewInt.add(Integer.parseInt(pos));
        }

        for (Integer pos : diffNewInt) {
            positionService.addMemberPosition(memberID, pos);
        }

        for (Integer pos : diffOld) {
            positionService.deleteMemberPosition(memberID, pos);
        }
        return "redirect:/member/" + URLEncoder.encode(member.getMemberStageName(),"UTF-8") ;
    }

    @GetMapping("/member/deletemem/{memberID}")
    public String deleteAccount(@PathVariable("memberID") String memberID){
        memberService.deleteMember(memberID);
        return "redirect:/bands";
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
