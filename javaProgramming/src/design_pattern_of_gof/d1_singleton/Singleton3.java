package design_pattern_of_gof.d1_singleton;

public class Singleton3 {

    private static final Singleton3 INSTANCE = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return INSTANCE;
    }
}
