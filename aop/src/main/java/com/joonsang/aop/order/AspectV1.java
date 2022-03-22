package com.joonsang.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

    /**
     * Advice : doLog()
     * 적용범위 : com.joonsang.aop.order 패키지와 하위 패키지
     * 포인트컷 : execution(* com.joonsang.aop.order..*(..))
     *          ㄴ AspectJ 포인트컷 표현식
     *
     * Pointcut Signature : void com.joonsang.aop.order.OrderService.orderItem(String)
     *                      String com.joonsang.aop.order.OrderRepository.save(String)
     *                      ㄴ 메소드 이름 + 파라미터
     */
    @Around("execution(* com.joonsang.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
