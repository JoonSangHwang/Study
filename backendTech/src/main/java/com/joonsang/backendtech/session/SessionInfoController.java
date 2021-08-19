package com.joonsang.backendtech.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
//        session.getAttributeNames()
//                .asIterator()
//                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        Enumeration<String> parameterNames = session.getAttributeNames();
        while (parameterNames.hasMoreElements()) {
            log.info("session name={}", parameterNames.nextElement());
//            log.info("session name={}, value={}", parameterNames.nextElement(), session.getAttribute(parameterNames.nextElement()));
        }

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());        // 비활성화까지 시간(초)
        log.info("creationTime={}", new Date(session.getCreationTime()));               // 세션 생성 일자
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));       // 마지막에 접근한 시간
        log.info("isNew={}", session.isNew());                                          // 지금 생성한 것인가

        return "세션 출력";

    }
}