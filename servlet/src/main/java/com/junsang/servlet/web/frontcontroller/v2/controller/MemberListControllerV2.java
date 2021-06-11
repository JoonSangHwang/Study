package com.junsang.servlet.web.frontcontroller.v2.controller;


import com.junsang.servlet.member.Member;
import com.junsang.servlet.member.MemberRepository;
import com.junsang.servlet.web.frontcontroller.MyView;
import com.junsang.servlet.web.frontcontroller.v2.V2Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements V2Controller {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);
        return new MyView("/WEB-INF/views/members.jsp");
    }
}