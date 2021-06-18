package com.junsang.servlet.web.frontcontroller.v4;

import com.junsang.servlet.web.frontcontroller.MyView;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberFormController;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberListController;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberSaveController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class V4FrontControllerServlet extends HttpServlet {

    private Map<String, V4Controller> controllerMap = new HashMap<>();

    public V4FrontControllerServlet() {
        controllerMap.put("/front-controller/v4/members/new-form", new V4MemberFormController());
        controllerMap.put("/front-controller/v4/members/save", new V4MemberSaveController());
        controllerMap.put("/front-controller/v4/members", new V4MemberListController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        V4Controller controller = controllerMap.get(requestURI);

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); //추가

        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);

        view.render(model,request,response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
//        request.getParameterNames()
//                .asIterator()
//                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            parameterNames.nextElement();
        }
        return paramMap;
    }
}
