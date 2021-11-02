package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.opt.Coordinates;

import java.util.List;

public interface MapService {
    ServiceCenter searchCenterPos(Long centerNum);

    List<ServiceCenter> findAllCenter();

    ServiceCenter searchCenterByName(String centerName);


    Coordinates findCoordinates(String customerAddress);

    List<EngineerInfo> searchEngineerAtCenter(Long centerNum);

    List<Object> findScheduleAtTime(List<EngineerInfo> list, String dateTime);
}
