package design_pattern_of_gof.d1_singleton;

public class Singleton7 {

    private Singleton7() {}

    private static class SingletonHolder {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // 역직렬화 시, 실행되는 메소드로 동일한 객체를 반환할 수 있게 된다.
    protected Object readResolve() {
        return getInstance();
    }
}
