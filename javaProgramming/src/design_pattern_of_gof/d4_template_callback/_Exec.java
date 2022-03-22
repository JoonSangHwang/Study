package design_pattern_of_gof.d4_template_callback;

public class _Exec {

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
}