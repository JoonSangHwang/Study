package com.junsang.servlet.web.frontcontroller.v3.controller;

import com.junsang.servlet.member.Member;
import com.junsang.servlet.member.MemberRepository;
import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.v3.V3Controller;

import java.util.Map;

public class V3MemberSaveController implements V3Controller {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;
    }
}