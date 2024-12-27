package com.locationproject.projectlocation.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locationproject.projectlocation.Model.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
