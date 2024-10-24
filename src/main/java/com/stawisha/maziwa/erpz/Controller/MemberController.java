/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stawisha.maziwa.erpz.Controller;

import com.stawisha.maziwa.erpz.model.Member;
import com.stawisha.maziwa.erpz.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/{tenant}")
    public ResponseEntity<?> save(@RequestBody Member member, @PathVariable String tenant) {
        memberService.save(member, tenant);
        System.out.println(tenant);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenant}/{memberId}")
    public ResponseEntity<?> findmemberByIdAndTenantId(@PathVariable String memberId, @PathVariable String tenant) {
        return new ResponseEntity<>(memberService.findmemberByIdAndTenantId(memberId, tenant), HttpStatus.OK);
    }

    @GetMapping("/{tenant}")
    public ResponseEntity<?> findMembersByTenantId(@PathVariable String tenant) {
        return new ResponseEntity<>(memberService.findMembersByTenantId(tenant), HttpStatus.OK);

    }
}
