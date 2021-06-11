package com.junsang.servlet.web.frontcontroller.v1;

import com.junsang.servlet.web.frontcontroller.v1.controller.V1MemberFormController;
import com.junsang.servlet.web.frontcontroller.v1.controller.V1MemberListController;
import com.junsang.servlet.web.frontcontroller.v1.controller.V1MemberSaveController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class V1FrontControllerServlet extends HttpServlet {

    private Map<String, V1Controller> controllerMap = new HashMap<>();

    public V1FrontControllerServlet() {
        controllerMap.put("/front-controller/v1/members/new-form", new V1MemberFormController());
        controllerMap.put("/front-controller/v1/members/save", new V1MemberSaveController());
        controllerMap.put("/front-controller/v1/members", new V1MemberListController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();

        V1Controller controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);       // 404
            return;
        }

        controller.process(request, response);
    }
}