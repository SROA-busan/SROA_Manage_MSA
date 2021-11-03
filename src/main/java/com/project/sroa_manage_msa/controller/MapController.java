package com.project.sroa_manage_msa.controller;

import com.project.sroa_manage_msa.dto.CenterView;
import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {
    MapService mapService;


    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/map/selectCenter")
    public String selectCenter(Model model) {
        List<ServiceCenter> centers = mapService.findAllCenter();
        List<CenterView> list = new ArrayList<>();
        for (ServiceCenter center : centers) {
            Integer cnt = mapService.searchEngineerAtCenter(center.getCenterNum()).size();
            list.add(new CenterView(center.getCenterName(), cnt));
        }
        model.addAttribute("list", list);
        return "Map/selectCenter";
    }

    @GetMapping("/map/{centerName}")
    public String map(@PathVariable("centerName") String centerName, Model model) {
        System.out.println(centerName);
        ServiceCenter center = mapService.searchCenterByName(centerName);
        model.addAttribute("lat", center.getLatitude());
        model.addAttribute("lon", center.getLongitude());
        model.addAttribute("centerName", center.getCenterName());
        model.addAttribute("centerNum", center.getCenterNum());
        return "Map/map";
    }


    @PostMapping("/map")
    @ResponseBody
    public List<Object> drawMap(@RequestParam("dateTime") String dateTime, @RequestParam("serviceCenter") Long centerNum) {
        System.out.println("asd");
        List<EngineerInfo> list = mapService.searchEngineerAtCenter(centerNum);
        System.out.println("센터내 엔지니어 수 : " + list.size());
        List<Object> engineerMap = mapService.findScheduleAtTime(list, dateTime);

        return engineerMap;
    }
}
