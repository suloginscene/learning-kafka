# learning-kafka

카프카 학습을 위한 아주 작은 개인 프로젝트입니다. \
오류가 있는 경우 알려주시면 감사하겠습니다.

## 구성하기

- 이 프로젝트 clone
- 카프카 다운로드 (https://kafka.apache.org/downloads)  
  cli를 사용하기 위한 다운로드입니다. 카프카 자체는 도커로 실행할 예정이므로, 로컬에서 카프카를 실행하시면 포트가 중복됩니다.

``` shell
# 프로젝트 루트 경로에서

# 주키퍼 1 & 카프카 3 등...
docker-compose up -d

# 토픽 생성 (비즈니스 & 로그)
sudo sh {카프카홈}/bin/kafka-topics \
  --zookeeper localhost:2181 \ 
  --replication-factor 3 --partitions 1 \
  --create --topic business
  
sudo sh {카프카홈}/bin/kafka-topics \
  --zookeeper localhost:2181 \ 
  --replication-factor 3 --partitions 1 \
  --create --topic log
```

- `localhost:8000`에서 토픽 상태 확인


## 시나리오

카프카 클러스터와 api-server, internal-system, log-collector를 모두 실행한 뒤,  
http 요청으로 관련 동작을 발생시키고 로그 및 토픽의 상태를 확인합니다.

(이미지)

a. 정상 시나리오
  - 2 X 1의 컨슈머들이 BizProducer와 BizConsumer의 로그를 수집한다.
  - 데이터 생산: api-server의 a_normal.http
  
b. 장애 시나리오 1 : 컨슈머 중단
  - BizConsumer가 중단되었다가 재시작하면 그 사이 쌓여있던 메시지를 빠짐없이 가져온다.
  - 컨슈머 중단: internal-system의 b_consumer_run_control.http
  
c. 장애 시나리오 2 : 커밋 전 실패
  - BizConsumer가 커밋을 하지 않으면, 다음 메시지로 넘어가지 않는다.

d. 장애 시나리오 3
  - 리더 브로커를 종료해도, ISR이 리더를 승계하여 이상없이 동작한다.
  
e. 성능 시나리오



## 참고자료
