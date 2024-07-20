# 레포 목적

1. Java + Spring 학습
2. 어느 머신에서든 코드를 pull해서 실행하면 실행되는 코드
    * 민감정보(resources 하위 파일, dockerfile, docker-compose.yml) 숨기는 작업은 안 할 것
3. 백엔드 중심(Spring, JPA, Database) 학습한 내용 구현
    * 중요한 내용은 주석으로 기록
4. 최소한의 CI/CD
5. 프론트엔드는 거들기만

---

# 계속 추가 되는 기술 스택 별 To Do List

## DevOps (CI/CD) 🧱

### Docker & Docker compose

- [ ] 로컬 환경, 테스트 환경, 개발 환경 구분
- 무중단 배포
    - [ ] blue/green 배포

### Github Actions

- [x] workflow, job, step 이해하기
- [x] 단위 테스트 환경 구축해보기
- [x] 테스트 결과 리포트 생성 및 시각화
- [x] Docker 이미지 생성 및 Docker 저장소(Docker Container Registry)에 저장

## Backend 🔙

### Java

- [ ] Virtual Thread

### Spring Boot

- [x] Spring MVC
- [x] Spring Security
- [x] Interceptor
- [ ] AOP
- [ ] Task Scheduler

### JPA/Hibernate

- [x] QueryDSL
- [ ] Spring Data JPA
- [ ] JDBC
- 다양한 최적화 기법들
    - [ ] 지연 로딩과 조회 성능 최적화
    - [ ] 컬렉션 조회 최적화

### Auth

- [x] jsonwebtoken

### Monitoring

- [ ] Grafana

### Test

- [x] Testcontainers 프레임워크를 이용한 Spring + Mysql 통합 테스트
- [ ] embedded MongoDB 이용한 Spring + MongoDB 통합 테스트

- [ ] Postman Collections

## Frontend 🖼️

### Thymeleaf

- 빠른 프론트 구현을 위해 사용
- Spring Controller만 고치면 언제든지 REST API로 변화 가능하도록 설계하기

---

# 패키지 별 역할

## test

* 실제 앱과 아무 관련없는 테스트용 다목적 코드 작성 공간 (없는 코드 취급해도 되는 공간)
* 보통 다음과 같은 용도로 사용
    * CI/CD가 제대로 동작하는지 확인
    * 프레임워크 간 호환성 확인

## api

### api.controller

* Rest Controller Layer
* Json을 기반으로 응답하는 `@RestController`와 관련된 코드만 포함
* ex) Request, Response를 위한 DTO, @RestController 어노테이션이 붙은 컨트롤러

### api.service

* Component Service Layer
* Rest Controller에서 수행해야하는 비즈니스 로직을 모두 Component Service에서 수행
* Rest Controller는 validation, http status code 반환 등 다른 역할에 집중하게 하려고 분리

## config

* 스프링 글로벌 설정, 빈 등록 등을 위한 공간

## domain

* 하나의 Aggregate 단위로 하위 패키지 분리

## util

* 자주 쓰는 연산을 편하게 사용하려고 만들어 놓은 코드 모음
* 어느 프로젝트에서나 동일하게 동작해야 하는 코드
* **무조건 정적 팩토리 메소드로 작성 가능해야 함**
* ex) 날짜 계산 함수, 랜덤 함수

## view

* Server Side Rendering layer
* ViewResolver를 거치는 `@Controller`와 관련된 코드만 포함
* ex) 사용자 입력 폼 관련 DTO, Thymeleaf를 이용해서 html 템플릿을 반환하는 컨트롤러

---

# 네이밍 규칙

* ~Request
    * `@RestController`에서 파라미터로 받은 DTO
* ~Response
    * `@RestController`에서 반환하는 DTO