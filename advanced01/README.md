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