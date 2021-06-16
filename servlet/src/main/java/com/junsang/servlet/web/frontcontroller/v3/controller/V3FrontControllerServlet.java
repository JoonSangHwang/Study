package com.junsang.servlet.web.frontcontroller.v3.controller;

import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.MyView;
import com.junsang.servlet.web.frontcontroller.v3.V3Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;

@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class V3FrontControllerServlet extends HttpServlet {

    private Map<String, V3Controller> controllerMap = new HashMap<>();

    public V3FrontControllerServlet() {
        controllerMap.put("/front-controller/v3/members/new-form", new V3MemberFormController());
        controllerMap.put("/front-controller/v3/members/save", new V3MemberSaveController());
        controllerMap.put("/front-controller/v3/members", new V3MemberListController());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        // 프론트 컨트롤러가 가진 URL 맵핑 정보 가져오기
        V3Controller controller = controllerMap.get(requestURI);
        // 사용자가 요청한 URL 이 없을 경우 404
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 모든 파라미터를 가져와 Create Param Map
        Map<String, String> paramMap = createParamMap(request);

        // 뷰 네임 만들어주기 (뷰 리졸브)
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();     // 논리 이름 가져오기
        MyView view = viewResolver(viewName);   // 물리 이름 가져오기

        // 뷰 로딩 (렌더링)
        view.render(mv.getModel(),request,response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {

        // 모든 파라미터 가져오기
        Map<String, String> paramMap = new HashMap<>();

//        request.getParameterNames()
//                .asIterator()
//                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            parameterNames.nextElement();
        }

        paramMap = (Map<String, String>) parameterNames;

        return paramMap;
    }
}