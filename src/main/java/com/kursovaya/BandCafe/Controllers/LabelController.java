package com.kursovaya.BandCafe.Controllers;

import com.kursovaya.BandCafe.Entities.GroupLabel;
import com.kursovaya.BandCafe.Services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Controller
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    LabelService labelService;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping()
    public String getLabels(Model model) throws FileNotFoundException {

        List<GroupLabel> labels = labelService.findAll();
        List<String> labelsDescs = new ArrayList<>();

        for (GroupLabel label : labels) {
            File file = new File(uploadPath + "/LabelDesc/" + label.getLabelDescSource());
            Scanner sc = new Scanner(file);
            labelsDescs.add(sc.nextLine());
        }
        model.addAttribute("labels", labels);
        model.addAttribute("labelsDescs", labelsDescs);
        return "labels";
    }

    @GetMapping("/{labelName}")
    public String returnLabel(@PathVariable String labelName, Model model) throws FileNotFoundException {
        GroupLabel label = labelService.getLabelByLabelName(labelName);
        File file = new File(uploadPath + "/LabelDesc/" + label.getLabelDescSource());
        Scanner sc = new Scanner(file);

        model.addAttribute("labelgroup", label);
        model.addAttribute("labeldesc",  sc.nextLine());
        return "labelView";
    }

    @GetMapping("/add")
    public String addLabel(Model model){
        countryList(model);
        model.addAttribute("labelgroup",new GroupLabel());
        return "addLabel";
    }


    @PostMapping("/add")
    public String addLabelPost(@ModelAttribute @Valid GroupLabel labelgroup,
                               BindingResult bindingResult,
                               @RequestParam ("filelabeldesc") MultipartFile filelabeldesc,
                               String errorLabel,
                               String errorCountry,
                               String errorCity,
                               String errorAddress,
                               Model model){
        model.addAttribute("labelgroup", labelgroup);

        countryList(model);

        if (bindingResult.hasErrors()){
            return "addLabel";
        }

        List<String> allLabelsNames = labelService.getAllLabelsName();
        if (labelgroup.getLabelName().equals("") || labelgroup.getLabelName() == null){
            errorLabel = "Введите название агентсва";
            model.addAttribute("errorLabel", errorLabel);
            return "addLabel";
        }
        if (allLabelsNames.contains(labelgroup.getLabelName())){
            errorLabel = "Такое агентство уже существует";
            model.addAttribute("errorLabel", errorLabel);
            return "addLabel";
        }

        if (labelgroup.getLabelCountry().equals("") || labelgroup.getLabelCountry() == null){
            errorCountry = "Введите страну агентсва";
            model.addAttribute("errorCountry", errorCountry);
            return "addLabel";
        }

        if (labelgroup.getLabelCity().equals("") || labelgroup.getLabelCity() == null){
            errorCity = "Введите город агентсва";
            model.addAttribute("errorCity", errorCity);
            return "addLabel";
        }

        if (labelgroup.getLabelMainAddress().equals("") || labelgroup.getLabelMainAddress() == null){
            errorAddress = "Введите адрес агентсва";
            model.addAttribute("errorAddress", errorAddress);
            return "addLabel";
        }

        if (filelabeldesc != null){
            File uploadDir = new File(uploadPath + "/LabelDesc");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + filelabeldesc.getOriginalFilename();

            try {
                filelabeldesc.transferTo(new File(uploadDir + "/"+ resultFilename));
                labelgroup.setLabelDescSource(resultFilename);
                System.out.println("Файл загружен");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(labelgroup.toString());
        labelService.addLabel(labelgroup);

        return "redirect:/labels";
    }

    static GroupLabel label2 = new GroupLabel();

    @GetMapping("/edit/{labelID}")
    public String editLabel(@PathVariable("labelID") String labelID, Model model){
        GroupLabel label = labelService.getLabelByLabelID(labelID);
        String selectedCountry = label.getLabelCountry();
        label2 = label;
        countryList(model);
        model.addAttribute("labelgroup", label);
        model.addAttribute("selectedCountry", selectedCountry);
        return "editLabel";
    }

    @PostMapping("/edit/{labelID}")
    public String editLabel(@ModelAttribute @Valid GroupLabel labelgroup,
                            BindingResult bindingResult,
                            @PathVariable("labelID") String labelID,
                            String errorLabel,
                            String errorCountry,
                            String errorCity,
                            String errorAddress,
                            Model model){
        countryList(model);
        model.addAttribute("labelgroup", labelgroup);

        if (bindingResult.hasErrors() ){
            return "editLabel";
        }

        List<String> allLabelsNames = labelService.getAllLabelsName();
        if (labelgroup.getLabelName().equals("") || labelgroup.getLabelName() == null){
            errorLabel = "Введите название агентсва";
            model.addAttribute("errorLabel", errorLabel);
            return "editLabel";
        }
        if (allLabelsNames.contains(labelgroup.getLabelName()) && !labelgroup.getLabelName().equals(label2.getLabelName())){
            errorLabel = "Такое агентство уже существует";
            model.addAttribute("errorLabel", errorLabel);
            return "editLabel";
        }

        if (labelgroup.getLabelCountry().equals("") || labelgroup.getLabelCountry() == null){
            errorCountry = "Введите страну агентсва";
            model.addAttribute("errorCountry", errorCountry);
            return "editLabel";
        }

        if (labelgroup.getLabelCity().equals("") || labelgroup.getLabelCity() == null){
            errorCity = "Введите город агентсва";
            model.addAttribute("errorCity", errorCity);
            return "editLabel";
        }

        if (labelgroup.getLabelMainAddress().equals("") || labelgroup.getLabelMainAddress() == null){
            errorAddress = "Введите адрес агентсва";
            model.addAttribute("errorAddress", errorAddress);
            return "editLabel";
        }

        labelService.editLabel(labelgroup);
        return "redirect:/labels";
    }


    public void countryList(Model model){
        List<String> countries = new ArrayList<>();

        for (String country : Locale.getISOCountries())
        {countries.add(new Locale("", country).getDisplayCountry());}
        model.addAttribute("countries", countries);

    }
}
