package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.Member;
import com.kursovaya.BandCafe.Entities.MemberGroup;
import com.kursovaya.BandCafe.Entities.Merch;
import com.kursovaya.BandCafe.Services.MemberGroupService;
import com.kursovaya.BandCafe.Services.MerchService;
import com.kursovaya.BandCafe.Views.MerchView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class MerchController {

    @Autowired
    MerchService merchService;

    @Autowired
    MemberGroupService memberGroupService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/merch")
    public String returnMerch(Model model){
        List<MerchView> merchAll = merchService.getAllMerchFromView();

        model.addAttribute("merchAll", merchAll);
        return "merch";
    }

    @GetMapping("/manageMerch")
    public String returnAllMerch(Principal principal, Model model){
        List<MerchView> merchAll = merchService.getAllMerchFromView();
        List<String> merchIDs = merchService.getMerchIDsByManagerLogin(principal.getName());
        System.out.println(merchIDs);
        for (String merchID : merchIDs) {
            System.out.println(merchID);
        }

        model.addAttribute("login", principal.getName());
        model.addAttribute("merchIDs", merchIDs);
        model.addAttribute("merchAll", merchAll);
        return "manageMerch";
    }

    @GetMapping("/merch/{merchName}")
    public String returnMerchItem(@PathVariable String merchName, Model model){
        String trueName = merchName.replace("%20", " ")
                .replace("+", " ");
        System.out.println(trueName);
        Merch merch = merchService.getMerchByName(trueName);
        MemberGroup memberGroup = memberGroupService.getGroupByGroupID(merch.getGroupID());
        model.addAttribute("merch", merch);
        model.addAttribute("memberGroup", memberGroup);
        return "merchView";
    }

    @GetMapping("/merch/add")
    public String addMerch(Principal principal, Model model){
        List<MemberGroup> groups = memberGroupService.getAllManagerGroups(principal.getName());
        model.addAttribute("groups", groups);
        model.addAttribute("merch", new Merch());
        return "addMerch";
    }

    @PostMapping("/merch/add")
    public String addMerch(@ModelAttribute("merch") @Validated Merch merch,
                           BindingResult bindingResult,
                           @RequestParam("merchImage") MultipartFile merchImage,
                           String errorName,
                           String errorPrice,
                           String errorAmount,
                           String errorGroup,
                           Principal principal,
                           Model model){
        model.addAttribute("merch", merch);
        List<MemberGroup> groups = memberGroupService.getAllManagerGroups(principal.getName());
        model.addAttribute("groups", groups);

        if (bindingResult.hasErrors()){
            return "addMerch";
        }

        if (merch.getMerchName().equals("") || merch.getMerchName() == null) {
            model.addAttribute("errorName", "Введите название товара");
            return "addMerch";
        }


        if (BigDecimal.ZERO.equals(merch.getMerchPrice()) || merch.getMerchPrice() == null) {
            model.addAttribute("errorPrice", "Введите цену товара");
            return "addMerch";
        }

        if (merch.getMerchPrice().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("errorPrice", "Цена не может быть отрицательной");
            return "addMerch";
        }

        if (merch.getMerchPrice().compareTo(BigDecimal.valueOf(1000000000)) > 0) {
            model.addAttribute("errorPrice", "Цена не может быть больше 1 000 000 000");
            return "addMerch";
        }

        if (merch.getMerchAmount() == 0 || merch.getMerchAmount() == null) {
            model.addAttribute("errorAmount", "Введите количество товара");
            return "addMerch";
        }

        if (merch.getMerchAmount() < 0) {
            model.addAttribute("errorAmount", "Количество не может быть отрицательным");
            return "addMerch";
        }

        if (merch.getMerchAmount() > 1000) {
            model.addAttribute("errorAmount", "Количество не может быть больше 1000");
            return "addMerch";
        }

        if (merch.getGroupID().equals("") || merch.getGroupID() == null) {
            model.addAttribute("errorGroup", "Укажите группу, которой принадлежит товар");
            return "addMerch";
        }

        if (!Objects.requireNonNull(merchImage.getOriginalFilename()).equals("")
                && !merchImage.isEmpty()) {
            File uploadDir = new File(uploadPath + "/MerchImages");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + merchImage.getOriginalFilename();

            try {
                merchImage.transferTo(new File(uploadDir + "/" + resultFilename));
                merch.setMerchDescSource(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            merch.setMerchDescSource("default.png");
        }

        System.out.println(merch.getMerchPrice().getClass().getName());


        System.out.println(merch.toString());

        merchService.addMerch(merch, principal.getName());
        return "redirect:/merch";
    }

    static Merch merch2 = new Merch();

    @GetMapping("/merch/edit/{merchID}")
    public String editMerch(@PathVariable("merchID") String merchID, Principal principal, Model model){
        Merch merch = merchService.getMerchByID(merchID);
        merch2 = merch;
        List<MemberGroup> groups = memberGroupService.getAllManagerGroups(principal.getName());
        model.addAttribute("groups", groups);
        model.addAttribute("login", principal.getName());
        model.addAttribute("merch", merch);
        return "editMerch";
    }

    @PostMapping("/merch/edit/{merchID}")
    public String editMerch(@ModelAttribute("merch") @Validated Merch merch,
                            BindingResult bindingResult,
                            String errorName,
                            String errorPrice,
                            String errorAmount,
                            String errorGroup,
                            Principal principal,
                            Model model) {
        model.addAttribute("merch", merch);
        List<MemberGroup> groups = memberGroupService.getAllManagerGroups(principal.getName());
        model.addAttribute("groups", groups);
        model.addAttribute("login", principal.getName());


        if (bindingResult.hasErrors()) {
            return "editMerch";
        }

        if (merch.getMerchName().equals("") || merch.getMerchName() == null) {
            model.addAttribute("errorName", "Введите название товара");
            return "editMerch";
        }


        if (BigDecimal.ZERO.equals(merch.getMerchPrice()) || merch.getMerchPrice() == null) {
            model.addAttribute("errorPrice", "Введите цену товара");
            return "editMerch";
        }

        if (merch.getMerchPrice().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("errorPrice", "Цена не может быть отрицательной");
            return "editMerch";
        }

        if (merch.getMerchPrice().compareTo(BigDecimal.valueOf(1000000000)) > 0) {
            model.addAttribute("errorPrice", "Цена не может быть больше 1 000000000");
            return "editMerch";
        }

        if (merch.getMerchAmount() == 0 || merch.getMerchAmount() == null) {
            model.addAttribute("errorAmount", "Введите количество товара");
            return "editMerch";
        }

        if (merch.getMerchAmount() < 0) {
            model.addAttribute("errorAmount", "Количество не может быть отрицательным");
            return "editMerch";
        }

        if (merch.getMerchAmount() > 1000) {
            model.addAttribute("errorAmount", "Количество не может быть больше 1 000");
            return "editMerch";
        }

        if (merch.getGroupID().equals("") || merch.getGroupID() == null) {
            model.addAttribute("errorGroup", "Укажите группу, которой принадлежит товар");
            return  "editMerch";
        }

        merch.setMerchDescSource(merch2.getMerchDescSource());
        if(merch.getMerchStatus() == null){
            merch.setMerchStatus(false);
        }

        System.out.println(merch.toString());

        merchService.editMerch(merch);
        return "redirect:/merch/" + URLEncoder.encode(merch.getMerchName(), StandardCharsets.UTF_8);
    }
}