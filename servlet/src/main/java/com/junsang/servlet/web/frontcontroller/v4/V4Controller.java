package com.junsang.servlet.web.frontcontroller.v4;

import java.util.Map;

/**
 * V4
 *
 * - 구조는 V3 와 동일하지만
 * - 프론트 컨트롤러가 컨트롤러에게 model 을 주고, 컨트롤러에서 ModleView 를 반환하지 않고 ViewName 를 반환한다.
 */
public interface V4Controller {

    /**
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
