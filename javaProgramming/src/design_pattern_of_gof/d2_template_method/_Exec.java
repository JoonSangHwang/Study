package design_pattern_of_gof.d2_template_method;

public class _Exec {

    public static void main(String[] args) {
        TemplateMethod1 t1 = new SubLoginClass1();
        t1.excute();

        TemplateMethod1 t2 = new SubLoginClass2();
        t2.excute();

        TemplateMethod1 t3 = new TemplateMethod1() {
            @Override
            protected void call() {
                System.out.println("로직3 실행");
            }
        };
    }
}

class SubLoginClass1 extends TemplateMethod1 {

    @Override
    protected void call() {
        System.out.println("로직1 실행");
    }
}

class SubLoginClass2 extends TemplateMethod1 {

    @Override
    protected void call() {
        System.out.println("로직2 실행");
    }
}