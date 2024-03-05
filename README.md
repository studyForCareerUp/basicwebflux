## 1. Spring Webflux

- Reactive StreamAPI
- non-blocking
- asynchronous

### 특징

- 대량의 웹트래픽
    - 특히 IO가 많은 서비스에서 높은 성능을 보여줌
- 마이크로서비스 GW
- WebSocket
- 실시간 채팅 서비스

## 2. CPU bound VS IO Bound

- Spring Webflux는 IO Bound 에 적합함
- CPU Bound(연산 관점)
    - 암호화, 압출, 알고리즘, 다수의 집계 쿼리
    - 잦은 컨텍스트 스위치는 성능 저하를 일으킴
        - 이러한 문제로 멀티 코어가 나옴
        - 싱글 코어로 속도를 높이면 발열 문제가 발생
- IO Bound(입출력 관점)
    - 네트워크 주고 받기
    - REST API IO, db IO, DISK IO
    - 많은 IO가 발생한다면?
        - 스레드를 늘리기(Thread per Request)
        - 스레드를 만드려면 메모리가 필요함
            - 너무 많은 메모리를 사용하면 OOM 발생
        - 스레드 생성 후 삭제는 성능 관점에서 볼때 손해
            - 그래서 쓰레드 풀을 활용
        - spring boot는 톰캣에서 스레드 풀을 만들어서 관리


## 3. sync/async & block/non-block

### Sync

- 순차적으로 명령을 처리
- 요청의 결과를 기다리고 다음 작업 진행
- 완료 순서 보장

### Async

- 동시에 병렬적으로 작업 처리
- 요청의 결과를 기다리지 않고 다음 작업을 진행
- 완료 순서 보장 X

### Block

- 대기를 하면

### non-Block

- 대기를 하지 않는다면

## 4. Spring MVC vs Webflux

### Netty

- 이벤트루프를 사용하여 non-blocking 프레임워크

## 5. Reactor

- Reactive Stream
    - Asynchronous Stream processing
    - Nonblocking backpressure
- stream
    - Publisher, Subsriber, Subscrption, Processor
- back pressure
    - Subsriber가 Publisher에게 자신이 얼만큼 처리할 수 있는지를 역으로 제시

- Flux
    - 0-N 개 아이템을 가질 수 있는 데이터 스트림
    - onNext(~N), onComplete, onError
- Mono
    - 0개 or 1개의 아이템을 가질 수 있는 데이터 스트림
    - onNext(0-1), onComplete, onError