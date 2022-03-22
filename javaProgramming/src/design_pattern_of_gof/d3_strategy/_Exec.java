package design_pattern_of_gof.d3_strategy;

public class _Exec {

    public static void main(String[] args) {
        Strategy1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy1 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();

        // 익명 클래스 사용
        Strategy1 strategyLogic3 = new StrategyLogic1() {
            @Override
            public void call() {
                System.out.println("비즈니스 로직3 실행");
            }
        };
        ContextV1 context3 = new ContextV1(strategyLogic3);
        context3.execute();

        // 인라인
        ContextV1 context4 = new ContextV1(new StrategyLogic1() {
            @Override
            public void call() {
                System.out.println("비즈니스 로직4 실행");
            }
        });
        context4.execute();

        // 람다
        ContextV1 context5 = new ContextV1(() -> System.out.println("비즈니스 로직5 실행"));
        context5.execute();

        // -----------------------------------------------
        ContextV2 context6 = new ContextV2();
        context6.execute(new StrategyLogic1());

        // 익명 클래스 사용
        ContextV2 context7 = new ContextV2();
        context7.execute(new StrategyLogic1() {
            @Override
            public void call() {
                System.out.println("비즈니스 로직7 실행");
            }
        });

        // 람다 사용
        ContextV2 context8 = new ContextV2();
        context8.execute(() -> System.out.println("비즈니스 로직8 실행"));

        // context 와 Strategy 를 '선 조립 후 실행'하는 방식이 아니라 Context 를 실행할 때 마다 전략을 인수로 전달한다.
        //클라이언트는 Context 를 실행하는 시점에 원하는 Strategy 를 전달할 수 있다. 따라서 이전 방식과 비교해서 원하는 전략을 더욱 유연하게 변경할 수 있다.
        //테스트 코드를 보면 하나의 Context 만 생성한다. 그리고 하나의 Context 에 실행 시점에 여러 전략을 인수로 전달해서 유연하게 실행하는 것을 확인할 수 있다.
    }
}

class StrategyLogic1 implements Strategy1 {
    @Override
    public void call() {
        System.out.println("비즈니스 로직1 실행");
    }
}

class StrategyLogic2 implements Strategy1 {
    @Override
    public void call() {
        System.out.println("비즈니스 로직1 실행");
    }
}