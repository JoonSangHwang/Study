package design_pattern_of_gof.d1_singleton;

public class Singleton5 {

    private Singleton5() {}

    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    public static Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
