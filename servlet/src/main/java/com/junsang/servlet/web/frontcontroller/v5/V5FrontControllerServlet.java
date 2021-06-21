package com.junsang.servlet.web.frontcontroller.v5;

import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.MyView;
import com.junsang.servlet.web.frontcontroller.v3.controller.V3MemberFormController;
import com.junsang.servlet.web.frontcontroller.v3.controller.V3MemberListController;
import com.junsang.servlet.web.frontcontroller.v3.controller.V3MemberSaveController;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberFormController;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberListController;
import com.junsang.servlet.web.frontcontroller.v4.controller.V4MemberSaveController;
import com.junsang.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.junsang.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class V5FrontControllerServlet extends HttpServlet {
    //매핑정보 목록
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    //어탭터 목록
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public V5FrontControllerServlet() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new V3MemberFormController());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new V3MemberSaveController());
        handlerMappingMap.put("/front-controller/v5/v3/members", new V3MemberListController());

        // v4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new V4MemberFormController());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new V4MemberSaveController());
        handlerMappingMap.put("/front-controller/v5/v4/members", new V4MemberListController());
    }


    private void initHandlerAdapters() {

        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //매핑정보에서 핸들러를 조회
        Object handler = getHandler(request);

        //핸들러가 없을 때
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //어댑터 조회
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //어댑터->핸들러 -> mv 반환
        ModelView mv = adapter.handle(request, response, handler);

        //뷰 리졸버
        MyView view = viewResolver(mv.getViewName());

        //렌더
        view.render(mv.getModel(), request, response);
    }


    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }


    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        } throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }


    private MyView viewResolver(String viewName) {

        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}