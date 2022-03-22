package com.joonsang.advanced01.app.v5;

import com.joonsang.advanced01.trace.LogTrace;
import com.joonsang.advanced01.trace.callback.TraceTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepoV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepoV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderController.request()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
