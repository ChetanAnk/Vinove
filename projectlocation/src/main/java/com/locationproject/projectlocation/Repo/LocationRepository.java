package com.locationproject.projectlocation.Repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locationproject.projectlocation.Model.Location;
import com.locationproject.projectlocation.Model.Member;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByMemberAndDateStamp(Member member, LocalDateTime dateStamp);
} 