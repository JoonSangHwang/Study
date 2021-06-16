package com.junsang.servlet.web.frontcontroller.v3.controller;

import com.junsang.servlet.member.Member;
import com.junsang.servlet.member.MemberRepository;
import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.v3.V3Controller;

import java.util.List;
import java.util.Map;

public class V3MemberListController implements V3Controller {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
    }
}