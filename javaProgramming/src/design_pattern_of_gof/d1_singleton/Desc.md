# 싱글톤 패턴
* 개념  
  - 객체의 인스턴스가 오직 1개만 생성되는 패턴  
  <br/>
* 장점  
  - 메모리 절약 (최초 한번의 new 연산자를 통해서 고정된 메모리 영역을 사용하기 때문)  
  - 재활용성 (이미 생성된 인스턴스를 활용하니 속도 측면 이점)  
  - 데이터 공유가 쉽다 (전역 인스턴스)  
  <br/>
* 단점  
  - 전역으로 사용되는 인스턴스이기 때문에 동시에 여러 요청 들어올 경우 동시성 그리고 트래픽 문제가 발생한다.  
  - 테스트하기 어렵다  
    ㄴ 자원을 공유하고 있기 때문에 테스트가 결정적으로 격리된 환경에서 수행되려면 매번 인스턴스의 상태를 초기화시켜주어야 한다  
    ㄴ 안티패턴으로 부르는 가장 큰 이유...
  - 의존 관계상 클라이언트가 구체 클래스에 의존하게 된다  
    ㄴ new 키워드를 직접 사용하여 클래스 안에서 객체를 생성하고 있으므로, 이는 DIP를 위반하게 되고 OCP 원칙 또한 위반할 가능성이 높다  
  - 자식클래스를 만들수 없으며 내부 상태를 변경하기 어려워 유연성이 떨어지는 패턴이다.  
  <br/>
* 구현 요약
  - 구현1 : Normal
  - 구현2 : syncronized
  - 구현3 : eager loding
  - 구현4 : Double Checked Locking
  - 구현5 : static inner 클래스
  - 구현6 : enum
  - 구현7 : 직렬화 & 역직렬화 방지하는 법

---------------------------------------
* 구현 1
<pre>
<code>public class Singleton1 {

    private static Singleton1 instance;

    private Singleton1() {}

    public static Singleton1 getInstance() {
        if (instance == null)
            instance = new Singleton1();

        return instance;
    }
}</code>
</pre>
>> 문제점  
> 멀티 스레드 환경에서 동시성 문제가 발생한다.

<br/><br/>
* 구현 2  
멀티스레딩 환경에서 발생할 수 있는 동시성 문제 해결을 위해 syncronized 키워드를 사용한다. 그러면 동시에 여러 스레드가 메소드로 들어 올 수 없어 동시성 문제가 해결 된다.  

<pre>
<code>public class Singleton2 {

    private static Singleton2 instance;

    private Singleton1() {}

    public static synchronized Singleton2 getInstance() {
        if (instance == null)
            instance = new Singleton1();

        return instance;
    }
}</code>
</pre>

>> 문제점  
getInstance() 함수가 호출 될 때 마다 동기화 처리 작업이 일어나 성능에 불이득이 생긴다.

<br/><br/>
* 구현 3  
  성능을 조금 더 신경쓰고 싶고(synchronized 키워드 삭제), 객체를 만드는 비용이 비싸지 않을 경우 즉시(eager) 로딩을 이용한다.  

<pre>
<code>public class Singleton3 {

    private static final Singleton3 INSTANCE = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return INSTANCE;
    }
}</code>
</pre>

>> 문제점  
즉시(eager) 로딩이 단점이 된다. 클래스가 로딩되는 시점에 인스턴스 필드가 초기화 되어 미리 만들어 두는데, 만약 인스턴스를 만드는 과정이 오래걸리고 메모리를 많이 사용할 경우 만들어 두고 쓰질 않으니 보기에 좋지 않다.  

<br/><br/>
* 구현 4  
  Double Checked Locking 을 사용하여 성능도 잡고, 지연(Lazy) 로딩의 이점도 잡는다.

<pre>
<code>public class Singleton4 {

    private static volatile Singleton4 instance;

    private Singleton4() {}

    public static Singleton4 getInstance() {
        if (instance == null) {
            synchronized (Singleton4.class) {
                if (instance == null) {
                    instance = new Singleton4();
                }
            }
        }

        return instance;
    }
}</code>
</pre>

>> 문제점  
volatile 키워드는 Java 1.5 이상 사용 가능하다. 그리고 코드 복잡도가 높다.

<br/><br/>
* 구현 5  
  static inner 클래스를 사용하여 동시성을 지키고 지연(Lazy) 로딩의 이점도 잡는다.  

<pre>
<code>public class Singleton5 {

    private Singleton5() {}

    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    public static Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}</code>
</pre>

>> 문제점  
권장되는 5번째 싱글톤 패턴 마저도 꺠트리는 방법이 있다.  

<br/><br/>

---------------------------------------
* 싱글톤 패턴 구현 방법을 깨트리는 방법 1 (리플렉션)

<pre>
<code>// 의도한 싱글톤 패턴으로 객체 생성
Singleton5 singleton = Singleton5.getInstance();

// 리플렉션으로 객체 생성
Constructor<Singleton5> constructor = Singleton5.class.getDeclaredConstructor();
constructor.setAccessible(true);
Singleton5 compSingleton = constructor.newInstance();

// 같지 않음
System.out.println(singleton == compSingleton);</code>
</pre>

- 대응방법  
enum 을 활용한다. 그러면 리플렉션은 기본생성자를 못가져오기 때문에 그리고 Cannot reflectively create `enum` objects 하다. 그렇기에 유일한 인스턴스를 보장 받는다. 단점은 enum 은 즉시 로딩이다. 크게 단점이 되지 않는다면 가장 안전한 방법이다. 또 직렬화&역직렬화 시에도 동일한 인스턴스를 반환해주기에 안전한 방법이다.
<pre>
<code>@Getter @Setter
public enum Singleton6 {

    INSTANCE;
    
    private Singleton6() {}

    private Integer number;
}</code>
</pre>

<br/><br/>
* 싱글톤 패턴 구현 방법을 깨트리는 방법 2 (직렬화 & 역직렬화)

<pre>
<code>// 의도한 싱글톤 패턴으로 객체 생성
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
System.out.println(singleton == compSingleton);</code>
</pre>

- 대응방법  
<pre>
<code>public class Singleton7 {

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
}</code>
</pre>

<br/>

---------------------------------------
### 복습

1. 자바에서 enum을 사용하지 않고 싱글톤 패턴을 구현하는 방법은?
2. private 생성자와 static 메소드를 사용하는 방법의 단점은?
3. enum을 사용해 싱글톤 패턴을 구현하는 방법의 장점과 단점은?