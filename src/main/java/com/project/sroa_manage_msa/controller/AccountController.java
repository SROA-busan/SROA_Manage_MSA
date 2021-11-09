package com.project.sroa_manage_msa.controller;

import com.project.sroa_manage_msa.dto.CenterForm;
import com.project.sroa_manage_msa.dto.EngineerForm;
import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.model.UserInfo;
import com.project.sroa_manage_msa.opt.Coordinates;
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

    @GetMapping("/info/creatNewCenter")
    public String createNewCenter() {
        return "/ServiceCenter/createNewCenter";
    }


    @PostMapping("info/createNewCenter")
    public String storeCenter(@Valid CenterForm centerForm) {
        Coordinates coor = mapService.findCoordinates(centerForm.getAddress());
        if (coor == null) {
            return "redirect:/info/creatNewCenter";
        }
        accountService.storeCenter(centerForm.getCenterName(), centerForm.getAddress(), coor);
        return "redirect:/";
    }


}
