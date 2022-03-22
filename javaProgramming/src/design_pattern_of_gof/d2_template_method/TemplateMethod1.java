package design_pattern_of_gof.d2_template_method;

public abstract class TemplateMethod1 {

    public void excute() {
        long startTime = System.currentTimeMillis();

        call();  // 핵심 비즈니스 로직

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("Result Time = " + resultTime);
    }

    protected abstract void call();
}
