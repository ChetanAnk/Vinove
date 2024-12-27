package com.locationproject.projectlocation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.locationproject.projectlocation.GoogleMap.GoogleMapDto.GoogleDistanceMatrixElement;
import com.locationproject.projectlocation.GoogleMap.GoogleMapDto.GoogleDistanceMatrixResponse;
import com.locationproject.projectlocation.GoogleMap.GoogleMapDto.GoogleMapsResponse;
import com.locationproject.projectlocation.Model.Location;
import com.locationproject.projectlocation.Model.LocationDis;
import com.locationproject.projectlocation.Model.Member;
import com.locationproject.projectlocation.Repo.LocationRepository;
import com.locationproject.projectlocation.Repo.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s";
    private static final String GOOGLE_DISTANCE_MATRIX_API_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s,%s&destinations=%s,%s&key=%s";

    private static final String GOOGLE_MAPS_API_KEY="AIzaSyD_VuprkbJQsrXhzdEMMywuwu8IcuLMZTo";

    /**
     * Save a location from coordinates by reverse geocoding.
     *
     * @param memberId  the ID of the member
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param dateStamp the date and time of the location
     * @return the saved location
     */
    public Location saveLocation(long memberId, double latitude, double longitude, LocalDateTime dateStamp) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        String address = reverseGeocode(latitude, longitude);

        Location location = new Location();
        location.setMember(member);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAddress(address);
        location.setDateStamp(dateStamp);

        return locationRepository.save(location);
    }
    private String reverseGeocode(double latitude, double longitude) {
        String url = String.format(GOOGLE_MAPS_API_URL, latitude, longitude, GOOGLE_MAPS_API_KEY);
        try {
            GoogleMapsResponse response = restTemplate.getForObject(url, GoogleMapsResponse.class);
            if (response != null && !response.getResults().isEmpty()) {
                return response.getResults().get(0).getFormattedAddress();
            } else {
                throw new IllegalArgumentException("Unable to retrieve address for the provided coordinates.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during reverse geocoding: " + e.getMessage(), e);
        }
    }
    public LocationDis calculateDistanceAndTime(double startLat, double startLon, double endLat, double endLon) {
        String url = String.format(GOOGLE_DISTANCE_MATRIX_API_URL, startLat, startLon, endLat, endLon, GOOGLE_MAPS_API_KEY);
        try {
            GoogleDistanceMatrixResponse response = restTemplate.getForObject(url, GoogleDistanceMatrixResponse.class);
            if (response != null && !response.getRows().isEmpty()) {
                GoogleDistanceMatrixElement element = response.getRows().get(0).getElements().get(0);

                LocationDis locationDis = new LocationDis();
                locationDis.setStartLan(startLat);
                locationDis.setStartLon(startLon);
                locationDis.setEndLan(endLat);
                locationDis.setEndLon(endLon);

                if (element.getDistance() != null) {
                    locationDis.setDistance(element.getDistance().getValue()/1000);
                }
                if (element.getDuration() != null) {
                    locationDis.setTimeReq(element.getDuration().getText());
                }

                return locationDis;
            } else {
                throw new IllegalArgumentException("Unable to calculate distance and time for the provided coordinates.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during distance and time calculation: " + e.getMessage(), e);
        }
    }
    public List<Location> findLocationsByMemberIdAndDateStamp(long memberId, LocalDateTime dateStamp) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        return locationRepository.findByMemberAndDateStamp(member, dateStamp);
    }

}
