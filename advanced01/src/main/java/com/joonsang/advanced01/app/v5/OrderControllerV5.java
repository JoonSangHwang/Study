package com.joonsang.advanced01.app.v5;

import com.joonsang.advanced01.trace.LogTrace;
import com.joonsang.advanced01.trace.callback.TraceCallback;
import com.joonsang.advanced01.trace.callback.TraceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    @Autowired
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        // new TraceTemplate 할 수 있겠지만... 리소스 낭비이다. 컨트롤러 자체가 싱글톤이라 생성자에서 초기화 해두면 재활용에 좋다.
        return template.execute("OrderController.request()", new TraceCallback<>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
