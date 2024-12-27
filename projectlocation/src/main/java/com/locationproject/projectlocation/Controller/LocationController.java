package com.locationproject.projectlocation.Controller;

import com.locationproject.projectlocation.Model.Location;
import com.locationproject.projectlocation.Model.LocationDis;
import com.locationproject.projectlocation.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/save")
    public ResponseEntity<Location> saveLocation(
            @RequestParam long memberId,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam String dateStamp) {
        try {
            LocalDateTime localDateStamp = LocalDateTime.parse(dateStamp);
            Location savedLocation = locationService.saveLocation(memberId, latitude, longitude, localDateStamp);
            return ResponseEntity.ok(savedLocation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/distance")
    public ResponseEntity<LocationDis> calculateDistanceAndTime(@RequestParam double startLat,@RequestParam double startLon,@RequestParam double endLat,@RequestParam double endLon) {
        try {
            LocationDis locationDis = locationService.calculateDistanceAndTime(startLat, startLon, endLat, endLon);
            return ResponseEntity.ok(locationDis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/find")
    public ResponseEntity<List<Location>> findLocationsByMemberIdAndDate(@RequestParam long memberId,@RequestParam String dateStamp) {
        try {
            LocalDateTime localDateStamp = LocalDateTime.parse(dateStamp);
            List<Location> locations = locationService.findLocationsByMemberIdAndDateStamp(memberId, localDateStamp);
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

