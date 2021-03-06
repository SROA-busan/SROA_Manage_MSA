package com.project.sroa_manage_msa.service;

import com.project.sroa_manage_msa.model.EngineerInfo;
import com.project.sroa_manage_msa.model.Schedule;
import com.project.sroa_manage_msa.model.ServiceCenter;
import com.project.sroa_manage_msa.opt.Coordinates;
import com.project.sroa_manage_msa.repository.EngineerInfoRepository;
import com.project.sroa_manage_msa.repository.ScheduleRepository;
import com.project.sroa_manage_msa.repository.ServiceCenterRepository;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {
    String apiKey = "553DD31F-7E58-3853-8B42-951509B85AAF";
    ScheduleRepository scheduleRepository;
    EngineerInfoRepository engineerInfoRepository;
    ServiceCenterRepository serviceCenterRepository;

    public MapServiceImpl(EngineerInfoRepository engineerInfoRepository,
                          ScheduleRepository scheduleRepository,
                          ServiceCenterRepository serviceCenterRepository) {
        this.engineerInfoRepository = engineerInfoRepository;
        this.scheduleRepository = scheduleRepository;
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Override
    public List<ServiceCenter> findAllCenter() {
        return serviceCenterRepository.findAll();
    }

    @Override
    public List<EngineerInfo> searchEngineerAtCenter(Long centerNum) {
        return engineerInfoRepository.findAllByServiceCenter(serviceCenterRepository.findByCenterNum(centerNum));
    }

    @Override
    public ServiceCenter searchCenterPos(Long centerNum) {
        ServiceCenter center = serviceCenterRepository.findByCenterNum(centerNum);
        return center;
    }

    @Override
    public ServiceCenter searchCenterByName(String centerName) {
        ServiceCenter center = serviceCenterRepository.findByCenterName(centerName);
        if (center.getLatitude() == null || center.getLongitude() == null) {
            Coordinates coor = findCoordinates(center.getAddress());
            center.setLatitude(coor.getLat());
            center.setLongitude(coor.getLon());
            serviceCenterRepository.updateCoor(center.getCenterNum(), coor.getLat(), coor.getLon());
        }
        return center;
    }

    // ???????????? ??????, ??????, ?????????
    @Override
    public List<Object> findScheduleAtTime(List<EngineerInfo> list, String dateTime) {
        Map<String, Object> map = null;
        List<Object> resList = new ArrayList<>();

        for (EngineerInfo engineer : list) {

            System.out.println(engineer.getEngineerNum() + "??? ????????????");
            List<Schedule> schedules = scheduleRepository.findAllByEngineerInfoAndStartDateTime(engineer.getEngineerNum(), dateTime);
            System.out.println("?????? ?????? ??? : " + schedules.size());
            if (schedules.size() != 0) {
                for (Schedule schedule : schedules) {
                    map = new HashMap<>();
                    Coordinates coordinates = findCoordinates(schedule.getAddress());
                    map.put("y", coordinates.getLat());
                    map.put("x", coordinates.getLon());
                    map.put("text", engineer.getEngineerNum().toString() + "??? ???????????? " + schedule.getStartDate().toString().split("T")[1]);
                    resList.add(map);
                }
            }

            schedules = scheduleRepository.findAllByEngineerInfoAndEndDateTime(engineer.getEngineerNum(), dateTime);
            System.out.println("?????? ?????? ??? : " + schedules.size());
            if (schedules.size() == 0) continue;
            for (Schedule schedule : schedules) {
                map = new HashMap<>();
                Coordinates coordinates = findCoordinates(schedule.getAddress());
                map.put("y", coordinates.getLat());
                map.put("x", coordinates.getLon());
                map.put("text", engineer.getEngineerNum().toString() + "??? ???????????? " + schedule.getEndDate().toString().split("T")[1]);
                resList.add(map);
            }
        }
        return resList;
    }

    @Override
    public Coordinates findCoordinates(String customerAddress) {
        String apiURL = "http://api.vworld.kr/req/address";
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            String text_content = URLEncoder.encode(customerAddress, StandardCharsets.UTF_8);

            String postParams = "service=address";
            postParams += "&request=getcoord";
            postParams += "&version=2.0";
            postParams += "&crs=epsg:4326";
            postParams += "&address=" + text_content;
            postParams += "&refine=true";
            postParams += "&simple=false";
            postParams += "&format=json";
            postParams += "&type=ROAD";
            postParams += "&key=" + apiKey;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {// ????????????
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {// ????????????
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            con.disconnect();
            Map<String, Object> map = jsonParser.parseMap(response.toString());

            Map<String, Object> point = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) map.get("response")).get("result")).get("point");
            return new Coordinates(Double.parseDouble((String) point.get("x")), Double.parseDouble((String) point.get("y")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
