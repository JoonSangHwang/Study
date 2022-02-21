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