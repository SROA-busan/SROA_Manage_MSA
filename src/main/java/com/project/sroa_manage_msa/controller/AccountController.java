package com.project.sroa_manage_msa.controller;

import com.project.sroa_manage_msa.dto.EngineerForm;
import com.project.sroa_manage_msa.model.EmployeeInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.model.UserInfo;
import com.project.sroa_manage_msa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }

    // employee 입력 -> 저장 -> engineer_info 초기화
    @GetMapping("/Account/creatNewEmployee")
    public String creatNewEmployee(Model model){
        List<ServiceCenter> centers =accountService.searchAllCenter();
        List<String> list= new ArrayList<>();
        for(ServiceCenter center:centers){
            list.add(center.getCenterName());
        }
        model.addAttribute("list", list);
        return "creatNewEmployee";
    }

    @PostMapping("/Account/creatNewEmployee")
    public String storeEngineer(@Valid EngineerForm engineerForm){
        if(accountService.checkDuplicateEmployeeNum(engineerForm.getEmployeeNum())){
            System.out.println(engineerForm.getEmployeeNum()+"는 이미 등록된 사원 번호입니다.");
            return "redirect:/Account/creatNewEmployee";
        }

        ServiceCenter center= accountService.searchCenter(engineerForm.getCenterName());
        EmployeeInfo employee= accountService.createEmployee(engineerForm.getEmployeeNum(), engineerForm.getName());
        UserInfo user = accountService.createUserInfo(engineerForm.getEmployeeNum(), engineerForm.getName(), engineerForm.getPhoneNum());
        accountService.createEnginnerInfo(center, employee, user);

        return "redirect:/";
    }

}
