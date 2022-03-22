package com.joonsang.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6 {

    @Around("com.joonsang.aop.order.OrderPointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());

            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("com.joonsang.aop.order.OrderPointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {     // @Before 는 ProceedingJoinPoint 를 사용할 수 없음
        log.info("[before] {}", joinPoint.getSignature());
    }

    // returning 속성에 사용된 이름은 Adivce 메소드(=orderAndService)의 매개변수 타입과 이름이 일치해야 한다.
    @AfterReturning(value = "com.joonsang.aop.order.OrderPointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {  // result 값은 조작 불가능. 조작은 @Around 에서만 가능
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "com.joonsang.aop.order.OrderPointcuts.allOrder()", returning = "result")
    public void doReturn(JoinPoint joinPoint, String result) {  // result 값은 조작 불가능. 조작은 @Around 에서만 가능
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    // throwing 절에 지정된 타입과 맞은 예외를 대상으로 실행한다. (부모 타입을 지정하면 모든 자식 타입은 인정된다.)
    @AfterThrowing(value = "com.joonsang.aop.order.OrderPointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    // 일반적으로 리소스를 해제하는 데 사용한다.
    @After(value = "com.joonsang.aop.order.OrderPointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
