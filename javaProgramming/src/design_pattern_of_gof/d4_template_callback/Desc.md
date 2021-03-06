# 템플릿 콜백 패턴
![img.png](img.png)

* 콜백 정의
  - 프로그래밍에서 콜백(callback) 또는 콜애프터 함수(call-after function)는 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 말한다. 콜백을 넘겨받는 코드는 이 콜백을 필요에 따라 즉시 실행할 수도 있고, 아니면 나중에 실행할 수도 있다. (위키백과 참고)  
  - callback 은 코드가 호출( call )은 되는데 코드를 넘겨준 곳의 뒤( back )에서 실행된다는 뜻이다.  
    - ContextV2 예제에서 콜백은 Strategy  
    - 여기에서는 클라이언트에서 직접 Strategy 를 실행하는 것이 아니라, 클라이언트가 ContextV2.execute(..) 를 실행할 때 Strategy 를 넘겨주고, ContextV2 뒤에서 Strategy 가 실행된다.  

* 자바 언어에서 콜백
  - 자바 언어에서 실행 가능한 코드를 인수로 넘기려면 객체가 필요하다. 자바8부터는 람다를 사용할 수 있다. 
  - 자바 8 이전에는 보통 하나의 메소드를 가진 인터페이스를 구현하고, 주로 익명 내부 클래스를 사용했다. 
  - 최근에는 주로 람다를 사용한다.

* 개념
  - 변하는 부분은 파라미터로 넘어온 Strategy 의 코드를 실행해서 처리한다. 이렇게 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 콜백(callback)이라 한다.  
  - 스프링에서는 ContextV2 와 같은 방식의 전략 패턴을 템플릿 콜백 패턴이라 한다. 
  - 전략 패턴에서 Context 가 템플릿 역할을 하고, Strategy 부분이 콜백으로 넘어온다 생각하면 된다.
  - 참고로 템플릿 콜백 패턴은 GOF 패턴은 아니고, 스프링 내부에서 이런 방식을 자주 사용하기 때문에, 스프링 안에서만 이렇게 부른다. 전략 패턴에서 템플릿과 콜백 부분이 강조된 패턴이라 생각하면 된다.
  - 스프링에서는 JdbcTemplate , RestTemplate , TransactionTemplate , RedisTemplate 처럼 다양한 템플릿 콜백 패턴이 사용된다. 스프링에서 이름에 XxxTemplate 가 있다면 템플릿 콜백 패턴으로 만들어져 있다 생각하면 된다.
  <br/>

---------------------------------------
* 구현 1
  - ContextV2 와 내용이 같고 이름만 다르므로 크게 어려움은 없을 것이다
    - `Context` -> `Template`
    - `Strategy` -> `Callback`
<pre>
<code>public class _Exec {

    public static void main(String[] args) {
        // 익명 클래스
        TimeLogTemplate template1 = new TimeLogTemplate();
        template1.execute(new Callback() {
            @Override
            public void call() {
                System.out.println("비즈니스 로직1 실행");
            }
        });

        // 람다
        TimeLogTemplate template2 = new TimeLogTemplate();
        template2.execute(() -> System.out.println("비즈니스 로직2 실행"));
    }
}

interface Callback {
    void call();
}

class TimeLogTemplate {
    public void execute(Callback callback) {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행

        callback.call(); //위임

        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("resultTime=" + resultTime);
    }
}</code>
</pre>
