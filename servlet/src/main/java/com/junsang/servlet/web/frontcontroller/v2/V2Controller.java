package com.junsang.servlet.web.frontcontroller.v2;

import com.junsang.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface V2Controller {

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}