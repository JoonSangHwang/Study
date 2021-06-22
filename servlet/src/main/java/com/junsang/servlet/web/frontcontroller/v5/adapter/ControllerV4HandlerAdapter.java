package com.junsang.servlet.web.frontcontroller.v5.adapter;

import com.junsang.servlet.web.frontcontroller.ModelView;
import com.junsang.servlet.web.frontcontroller.v4.V4Controller;
import com.junsang.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof V4Controller);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        V4Controller controller = (V4Controller) handler;

        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();

        // 컨트롤러로부터 viewName 받기
        String viewName = controller.process(paramMap, model);

        /**
         * 이 부분에서 바로 return viewName 하면, 타입이 안맞는다.
         * -> 어댑터 사용 !
         */

        // 어댑터
        ModelView mv = new ModelView(viewName); // 뷰 설정
        mv.setModel(model);                     // 모델 설정
        return mv;
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