package com.junsang.servlet.web.frontcontroller.v4.controller;

import com.junsang.servlet.web.frontcontroller.v4.V4Controller;

import java.util.Map;

public class V4MemberFormController implements V4Controller {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}