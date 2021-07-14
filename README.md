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
http 요청으로 동작을 발생시키고 로그 및 토픽의 상태를 확인합니다.

(이미지)

- 정상 시나리오
  
- 장애 시나리오 1
  
- 장애 시나리오 2
  
- 장애 시나리오 3
  
- 성능 시나리오



## 참고자료
