package design_pattern_of_gof.d1_singleton;

public class Singleton1 {

    private static Singleton1 instance;

    // 기본 생성자의 private 선언으로 new 생성을 막음
    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (instance == null)
            instance = new Singleton1();

        return instance;
    }
}
