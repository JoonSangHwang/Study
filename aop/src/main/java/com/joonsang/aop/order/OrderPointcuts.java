package com.joonsang.aop.order;

import org.aspectj.lang.annotation.Pointcut;

public class OrderPointcuts {

    // hello.springaop.app 패키지와 하위 패키지
    @Pointcut("execution(* com.joonsang.aop.order..*(..))")
    public void allOrder(){}

    // 타입 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
