package com.junsang.servlet.web.frontcontroller.v3;

import com.junsang.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/**
 * V3
 *
 * - 서블릿 종속성 제거
 * - 뷰 경로 제거
 *
 * = 구조적으로는 잘 설계된 컨트롤러
 * = 하지만 좋은 프레임워크는 아키텍처도 중요하지만, 실제 개발하는 개발자가 단순하고 편리하게 사용할 수 있는 소위 실용성이 필요하다
 * = 좋은 예시로 EJB... 좋은 아키텍처를 가지고 있지만 사용하기 너무 어렵다.
 * = 두 마리의 토끼를 다 잡아보자. (V4 go go)
 *
 */
public interface V3Controller {
    ModelView process(Map<String, String> paramMap);
}
