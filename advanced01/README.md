# 스프링 핵심 원리 - 고급편

### v1
* 로그 (http://localhost:8010/v1/request?itemId=ex)  
[04e86ada] OrderController.request()  
[8dc1e606] OrderService.orderItem()  
[0dd5d560] OrderRepository.save()  
[0dd5d560] OrderRepository.save() time=0ms ex=java.lang.IllegalStateException: 예외 발생!  
[8dc1e606] OrderService.orderItem() time=0ms ex=java.lang.IllegalStateException: 예외 발생!  
[04e86ada] OrderController.request() time=1ms ex=java.lang.IllegalStateException: 예외 발생!  
<br/><br/>

* 요구사항
> (o) 모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력  
(o) 애플리케이션의 흐름을 변경하면 안됨  
____ (o) 로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨  
(o) 메서드 호출에 걸린 시간  
(o) 정상 흐름과 예외 흐름 구분  
____ (o) 예외 발생시 예외 정보가 남아야 함  
(x) 메서드 호출의 깊이 표현  
(x) HTTP 요청을 구분  
____ (x) HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함  
____ (x) 트랜잭션 ID (DB 트랜잭션X)  

----
### v2
* 로그 (http://localhost:8010/v1/request?itemId=ex)  
  [c80f5dbb] OrderController.request()  
  [c80f5dbb] |-->OrderService.orderItem()  
  [c80f5dbb] |   |-->OrderRepository.save()  
  [c80f5dbb] |   |<--OrderRepository.save() time=1005ms  
  [c80f5dbb] |<--OrderService.orderItem() time=1014ms  
  [c80f5dbb] OrderController.request() time=1017ms  
  <br/><br/>

* 요구사항
> (o) 모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력  
(o) 애플리케이션의 흐름을 변경하면 안됨  
____ (o) 로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨  
(o) 메서드 호출에 걸린 시간  
(o) 정상 흐름과 예외 흐름 구분  
____ (o) 예외 발생시 예외 정보가 남아야 함  
(o) 메서드 호출의 깊이 표현  
(o) HTTP 요청을 구분  
____ (o) HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함  
____ (o) 트랜잭션 ID (DB 트랜잭션X)


* 남은 문제
> HTTP 요청을 구분하고 깊이를 표현하기 위해서 TraceId 동기화가 필요하다.  
> TraceId 의 동기화를 위해서 관련 메서드의 모든 파라미터를 수정해야 한다.  
____ 만약 인터페이스가 있다면 인터페이스까지 모두 고쳐야 하는 상황이다.
로그를 처음 시작할 때는 begin() 을 호출하고, 처음이 아닐때는 beginSync() 를 호출해야 한다.  
____ 만약에 컨트롤러를 통해서 서비스를 호출하는 것이 아니라, 다른 곳에서 서비스를 처음으로 호출하는 상황이라면 파리미터로 넘길 TraceId 가 없다.  
HTTP 요청을 구분하고 깊이를 표현하기 위해서 TraceId 를 파라미터로 넘기는 것 말고 다른 대안은 없을까?  

----


동시성 문제
여러 쓰레드가 동시에 같은 인스턴스의 필드 값을 변경하면서 발생하는 문제
트래픽이 적은 상황에서는 확률상 잘 나타나지 않지만, 트래픽이 점점 많아질 수록 자주 발생한다.
특히 스프링 빈 처럼 싱글톤 객체의 필드를 변경하며 사용할 때 이러한 동시성 문제를 조심해야한다.

참고로 동시성 문제는 지역 변수에서는 발생하지 않는다. 지역 변수는 스레드마다 각각 다른 메모리 영역에 할당되기 때문이다.
동시성 문제는 인스턴스의 필드(주로 싱글톤), 또는 static 같은 공용 필드에 접근할 때 발생한다.
동시성 문제는 값을 읽기만 하면 발생하지 않는다. 어디선가 값을 변경하기 때문에 발생한다.

---
### v5
템플릿 콜백 패턴을 적용하였다.

* 문제  
  - 아무리 최적화를 해도 결국 로그 추적기를 적용하기 위해서 원본 코드를 수정해야 한다는 점이다.  
  - 클래스가 수백개이면 수백개를 더 힘들게 수정하는가 조금 덜 힘들게 수정하는가의 차이가 있을 뿐, 본질적으로 코드를 다 수정해야 하는 것은 마찬가지이다.  
  - 개발자의 게으름에 대한 욕심은 끝이 없다. 수 많은 개발자가 이 문제에 대해서 집요하게 고민해왔고, 여러가지 방향으로 해결책을 만들어왔다.  
  - 지금부터 원본 코드를 손대지 않고 로그 추적기를 적용할 수 있는 방법을 알아보자. 그러기 위해서 프록시 개념을 먼저 이해해야 한다.
