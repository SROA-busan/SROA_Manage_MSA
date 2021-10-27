package com.project.sroa_manage_msa.controller;

import com.project.sroa_manage_msa.dto.CenterView;
import com.project.sroa_manage_msa.dto.EngineerForm;
import com.project.sroa_manage_msa.dto.EngineerView;
import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.model.UserInfo;
import com.project.sroa_manage_msa.service.AccountService;
import com.project.sroa_manage_msa.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {
    AccountService accountService;
    MapService mapService;


    @Autowired
    public AccountController(AccountService accountService,
                             MapService mapService) {
        this.accountService = accountService;
        this.mapService = mapService;
    }

    @GetMapping("/info/selectEngineer/{centerName}")
    public String selectEngineer(@PathVariable("centerName") String centerName, Model model) {
        model.addAttribute("centerName", centerName);
        System.out.println(centerName);
        ServiceCenter center = mapService.searchCenterByName(centerName);
        List<EngineerInfo> engineers = accountService.searchEngineerAtCenter(center);
        List<EngineerView> list = new ArrayList<>();
        for (EngineerInfo engineer : engineers) {
            String name = accountService.findEngineerName(engineer.getEngineerNum());
            list.add(new EngineerView(name, engineer.getAvgScore(), engineer.getAmountOfWork()));
        }
        model.addAttribute("list", list);
        return "EngineerInfo/engineersAtCenter";
    }

    @GetMapping("/info/selectCenter")
    public String selectCenterInfo(Model model) {
        List<ServiceCenter> centers = mapService.findAllCenter();
        List<CenterView> list = new ArrayList<>();
        for (ServiceCenter center : centers) {
            Integer cnt = mapService.searchEngineerAtCenter(center.getCenterNum()).size();
            list.add(new CenterView(center.getCenterName(), cnt));
        }
        model.addAttribute("list", list);
        return "EngineerInfo/selectCenterForInfo";
    }


    // employee 입력 -> 저장 -> engineer_info 초기화
    @GetMapping("/Account/creatNewEmployee/{centerName}")
    public String creatNewEmployee(Model model, @PathVariable("centerName") String centerName) {
        List<ServiceCenter> centers = accountService.searchAllCenter();

        model.addAttribute("centerName", centerName);
        return "EngineerInfo/creatNewEmployee";
    }

    @PostMapping("/Account/creatNewEmployee")
    public String storeEngineer(@Valid EngineerForm engineerForm) throws UnsupportedEncodingException {
        ServiceCenter center = accountService.searchCenter(engineerForm.getCenterName());
        if (accountService.checkDuplicateEmployeeNum(engineerForm.getEmployeeNum())) {
            System.out.println(engineerForm.getEmployeeNum() + "는 이미 등록된 사원 번호입니다.");
            return "redirect:/info/selectCenter";
        }


        EmployeeInfo employee = accountService.createEmployee(engineerForm.getEmployeeNum(), engineerForm.getName());
        UserInfo user = accountService.createUserInfo(engineerForm.getEmployeeNum(), engineerForm.getName(), engineerForm.getPhoneNum());
        accountService.createEnginnerInfo(center, employee, user);

        return "redirect:/info/selectCenter";
    }

}
