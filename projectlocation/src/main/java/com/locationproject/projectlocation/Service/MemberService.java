package com.locationproject.projectlocation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.locationproject.projectlocation.Model.Member;
import com.locationproject.projectlocation.Repo.MemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Get all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Get a member by ID
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    // Add a new member
    public Member addMember(String name, MultipartFile profilePicture) throws IOException {
        Member member = new Member();
        member.setName(name);
        member.setProfilePicture(profilePicture.getBytes());
        return memberRepository.save(member);
    }

    // Update an existing member
    public Member updateMember(Long id, String name, MultipartFile profilePicture) throws IOException {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(name);
            if (profilePicture != null && !profilePicture.isEmpty()) {
                member.setProfilePicture(profilePicture.getBytes());
            }
            return memberRepository.save(member);
        } else {
            throw new RuntimeException("Member not found with id " + id);
        }
    }

    // Delete a member
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}

