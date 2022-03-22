package design_pattern_of_gof.d3_strategy;

public class ContextV2 {

    public void execute(Strategy1 strategy) {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행

        strategy.call(); //위임

        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("resultTime=" + resultTime);
    }
}
