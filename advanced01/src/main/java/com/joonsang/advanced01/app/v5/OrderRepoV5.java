package com.joonsang.advanced01.app.v5;

import com.joonsang.advanced01.trace.LogTrace;
import com.joonsang.advanced01.trace.callback.TraceTemplate;

public class OrderRepoV5 {

    private final TraceTemplate template;

    public OrderRepoV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {
        template.execute("OrderRepository.save()", () -> {

            //저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
