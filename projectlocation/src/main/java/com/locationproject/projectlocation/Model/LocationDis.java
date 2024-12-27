package com.locationproject.projectlocation.Model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class LocationDis {
    private double startLan;
    private double startLon;
    private double endLan;
    private double endLon;
    private double distance;
    private String timeReq;
}
