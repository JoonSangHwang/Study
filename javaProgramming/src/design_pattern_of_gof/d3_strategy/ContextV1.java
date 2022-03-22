package design_pattern_of_gof.d3_strategy;

public class ContextV1 {

    private Strategy1 strategy;

    public ContextV1(Strategy1 strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행

        strategy.call(); //위임

        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("resultTime=" + resultTime);
    }
}
