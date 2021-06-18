package com.junsang.servlet.web.frontcontroller.v4.controller;

import com.junsang.servlet.member.Member;
import com.junsang.servlet.member.MemberRepository;
import com.junsang.servlet.web.frontcontroller.v4.V4Controller;

import java.util.Map;

public class V4MemberSaveController implements V4Controller {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";

    }
}