package com.locationproject.projectlocation.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.locationproject.projectlocation.Model.Member;
import com.locationproject.projectlocation.Service.MemberService;

@RestController
@RequestMapping("/attendance")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Member addMember(
            @RequestParam String name,
            @RequestParam MultipartFile profilePicture) throws IOException {
        return memberService.addMember(name, profilePicture);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestParam String name,@RequestParam(required = false) MultipartFile profilePicture) throws IOException {
        return memberService.updateMember(id, name, profilePicture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}

