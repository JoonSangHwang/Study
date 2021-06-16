package com.junsang.servlet.web.frontcontroller.v3.controller;

import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.v3.V3Controller;

import java.util.Map;

public class V3MemberFormController implements V3Controller {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}