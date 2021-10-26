package com.project.sroa_manage_msa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccountController {
    // employee 입력 -> 저장 -> engineer_info 초기화
    @GetMapping("/Account/creatNewEmployee")
    public String creatNewEmployee(){
        return "creatNewEmployee";
    }
}
