package design_pattern_of_gof.singleton;

import java.io.*;

public class _Exec {
    public static void main(String[] args) throws IOException {

        // 의도한 싱글톤 패턴으로 객체 생성
        Singleton5 singleton = Singleton5.getInstance();

        // 직렬화로 파일 저장
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("joonsang.obj"))) {
            out.writeObject(singleton);
        }

        // 역직렬화로 객체 생성
        Singleton5 compSingleton = null;
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("joonsang.obj"))) {
            compSingleton = (Singleton5) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 같지 않음
        System.out.println(singleton == compSingleton);
    }
}
