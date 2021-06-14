package com.junsang.servlet.web.frontcontroller.v2;

import com.junsang.servlet.web.frontcontroller.MyView;
import com.junsang.servlet.web.frontcontroller.v2.controller.V2MemberFormController;
import com.junsang.servlet.web.frontcontroller.v2.controller.V2MemberListController;
import com.junsang.servlet.web.frontcontroller.v2.controller.V2MemberSaveController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class V2FrontControllerServlet extends HttpServlet {

    private Map<String, V2Controller> controllerMap = new HashMap<>();

    public V2FrontControllerServlet() {
        controllerMap.put("/front-controller/v2/members/new-form", new V2MemberFormController());
        controllerMap.put("/front-controller/v2/members/save", new V2MemberSaveController());
        controllerMap.put("/front-controller/v2/members", new V2MemberListController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        V2Controller controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}