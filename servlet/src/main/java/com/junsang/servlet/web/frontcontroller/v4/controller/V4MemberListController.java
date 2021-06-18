package com.junsang.servlet.web.frontcontroller.v4.controller;

import com.junsang.servlet.member.Member;
import com.junsang.servlet.member.MemberRepository;
import com.junsang.servlet.web.frontcontroller.v4.V4Controller;

import java.util.List;
import java.util.Map;

public class V4MemberListController implements V4Controller {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        List<Member> members = memberRepository.findAll();
        model.put("members", members);

        return "members";

    }
}