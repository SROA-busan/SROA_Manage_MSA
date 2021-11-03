package com.project.sroa_manage_msa.controller;

import com.project.sroa_manage_msa.dto.CenterView;
import com.project.sroa_manage_msa.dto.EngineerView;
import com.project.sroa_manage_msa.dto.ProductView;
import com.project.sroa_manage_msa.model.*;
import com.project.sroa_manage_msa.service.InfoService;
import com.project.sroa_manage_msa.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class InfoController {
    InfoService infoService;
    MapService mapService;

    @Autowired
    public InfoController(InfoService infoService,
                          MapService mapService) {
        this.infoService = infoService;
        this.mapService = mapService;
    }

    @GetMapping("/info/selectEngineer/{centerName}")
    public String selectEngineer(@PathVariable("centerName") String centerName, Model model) {
        model.addAttribute("centerName", centerName);
        System.out.println(centerName);
        ServiceCenter center = mapService.searchCenterByName(centerName);
        List<EngineerInfo> engineers = infoService.searchEngineerAtCenter(center.getCenterNum());
        List<EngineerView> list = new ArrayList<>();
        for (EngineerInfo engineer : engineers) {
            String name = infoService.findEngineerName(engineer.getEngineerNum());

            list.add(new EngineerView(engineer.getEmployeeInfo().getEmpNum(), engineer.getUserInfo().getPw(), engineer.getUserInfo().getName(), engineer.getEngineerNum(), name, engineer.getAvgScore(), engineer.getAmountOfWork()));
        }
        model.addAttribute("list", list);
        return "EngineerInfo/engineersAtCenter";
    }

    @GetMapping("/info/selectCenter")
    public String selectCenterInfo(Model model) {
        List<ServiceCenter> centers = infoService.findAllCenter();
        List<CenterView> list = new ArrayList<>();
        for (ServiceCenter center : centers) {
            Integer cnt = infoService.searchEngineerAtCenter(center.getCenterNum()).size();
            list.add(new CenterView(center.getCenterName(), cnt));
        }
        model.addAttribute("list", list);
        return "ServiceCenter/selectCenterForInfo";
    }


    @GetMapping("/info/selectCustomer")
    public String selectCustomer(Model model) {
        List<UserInfo> userInfos = infoService.findAllUserByStatus(0);
        model.addAttribute("list", userInfos);
        // 모델에 넣어서 리턴
        return "Customer/selectCustomer";
    }

    @GetMapping("/info/selectProduct")
    public String selectProduct(Model model) {
        List<Schedule> schedules = infoService.findAllSchedule();
        List<ProductView> list = new ArrayList<>();
        for (Schedule schedule : schedules) {
            Product product = schedule.getProduct();
            String endDate = new String();
            if (schedule.getEndDate() == null)
                endDate = "-";
            else {
                endDate = schedule.getEndDate().toString().substring(0, 16);
            }

            String status = infoService.convertStatusToString(schedule.getStatus());


            list.add(new ProductView(
                    schedule.getScheduleNum(),
                    product.getClassifyName(),
                    product.getProblem(),
                    schedule.getStartDate().toString().substring(0, 16).replace("T", "\n\t"),
                    endDate,
                    status,
                    schedule.getUserInfo().getId(),
                    schedule.getEngineerInfo().getEmployeeInfo().getEmpNum()
            ));
        }
        model.addAttribute("list", list);
        return "Product/selectProduct";
    }

    @GetMapping("/info/Evaluation/{engineerNum}")
    public String EvaluationOfEngineer(@PathVariable("engineerNum") Long engineerNum, Model model) {
        List<Evaluation> list = infoService.findEvaluationByEngineer(engineerNum);
        model.addAttribute("list", list);
        model.addAttribute("engineerNum", engineerNum);
        return "EngineerInfo/evaluationOfEngineer";
    }


}
