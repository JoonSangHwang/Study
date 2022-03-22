package com.joonsang.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@Import(AspectV1.class)     // 스프링 빈으로 등록
//@Import(AspectV2.class)     // 스프링 빈으로 등록
//@Import(AspectV3.class)     // 스프링 빈으로 등록
//@Import(AspectV4.class)     // 스프링 빈으로 등록
//@Import({AspectV5.LogAspect.class, AspectV5.TxAspect.class})
@Import(AspectV6.class)     // 스프링 빈으로 등록
@Slf4j
@SpringBootTest
public class OrderAopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        // AOP 프록시가 적용 되었는지 확인 - Import 를 안하면 false
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
