package com.joonsang.aop.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ExecutionTest {

    // 포인트컷 표현식을 처리해주는 클래스
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void 포인트컷_표현식_출력() {
        // public java.lang.String com.joonsang.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    /**
     * 매칭 조건
     * - 접근제어자 (생략 가능)  : public
     * - 반환타입             : String
     * - 선언타입 (생략 가능)   : com.joonsang.aop.member.MemberServiceImpl
     * - 메서드 이름          : hello
     * - 파라미터            : String
     * - 예외 (생략 가능)
     */
    @Test
    void 매칭_조건을_정확히_Match() {
        // public java.lang.String com.joonsang.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String com.joonsang.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 접근제어자 + 반환타입 + 선언타입 + 메서드이름 + 파라미터
        pointcut.setExpression("execution(public java.lang.String com.joonsang.aop.member.MemberServiceImpl.hello(java.lang.String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void 매칭_조건을_최대한_생략하여_Match() {
        // 반환타입 + 메서드이름 + 파라미터
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /****************************
     * 메서드명 매칭 관련 포인트컷
     ****************************/
    @Test
    void 메서드명_정확히_Match() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 메서드명_조금_생략_Match() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 메서드명_조금_더_생략_Match() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 존재하지_않는_메서드명_MatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    /****************************
     * 패키지 매칭 관련 포인트컷
     ****************************/
    @Test
    void 패키지명_정확히_Match() {
        pointcut.setExpression("execution(* com.joonsang.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 패키지명_조금_생략_Match() {
        pointcut.setExpression("execution(* com.joonsang.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 패키지명_조금_더_생략_MatchFalse() {
        pointcut.setExpression("execution(* com.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    void 패키지명_member를_포함한_하위에_존재하는_모든것_Match() {
        pointcut.setExpression("execution(* com.joonsang.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /****************************
     * 타입 매칭 관련 포인트컷
     ****************************/
    @Test
    void 타입정보_MemberServiceImpl_정확히_Match() {
        pointcut.setExpression("execution(* com.joonsang.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 타입정보를_부모로_MemberService_선언해도_Match() {
        pointcut.setExpression("execution(* com.joonsang.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 타입정보를_부모로_MemberService_선언했지만_부모에_없는_메소드라서_MatchFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.joonsang.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    /****************************
     * 파라미터 매칭 관련 포인트컷
     ****************************/
    @Test
    void String_타입의_파라미터_Match() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 파라미터_없음_Match() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    void 정확히_하나의_파라미터_Match() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void 모든_파라미터_Match() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void String_타입의_파라미터로_시작해서_2번째는_파라미터는_랜덤_Match() {
        pointcut.setExpression("execution(* *(String, *))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    void String_타입의_파라미터로_시작해서_다수의_모든_파라미터_Match() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
