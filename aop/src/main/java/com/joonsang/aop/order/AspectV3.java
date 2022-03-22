package com.joonsang.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // com.joonsang.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* com.joonsang.aop.order..*(..))")
    public void allOrder(){}

    // 타입 이름 패턴이 *Service (타입 이름 패턴이라고 한 이유는 클래스, 인터페이스에 모두 적용되기 때문)
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

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
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    /**
     * Advice : doTransaction()
     * 적용범위 : com.joonsang.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service 인 경우
     * 포인트컷 : execution(* com.joonsang.aop.order..*(..))
     *          ㄴ AspectJ 포인트컷 표현식
     *
     * Pointcut Signature : void com.joonsang.aop.order.OrderService.orderItem(String)
     *                      String com.joonsang.aop.order.OrderRepository.save(String)
     *                      ㄴ 메소드 이름 + 파라미터
     */
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
