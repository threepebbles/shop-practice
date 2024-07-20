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
- [ ] AOP
- [ ] Spring Security
- [ ] Task Scheduler

### JPA/Hibernate
- [ ] QueryDSL
- [ ] Spring Data JPA
- [ ] JDBC
- 다양한 최적화 기법들
  - [ ] 지연 로딩과 조회 성능 최적화
  - [ ] 컬렉션 조회 최적화

### Monitoring
- [ ] Grafana

### Test
- [x] testcontainers 프레임워크를 이용한 Spring + Mysql 통합 테스트
- [ ] embedded MongoDB 이용한 Spring + MongoDB 통합 테스트

## Frontend 🖼️
### Thymeleaf
- 빠른 프론트 구현을 위해 사용
- Spring Controller만 고치면 언제든지 REST API로 변화 가능하도록 설계하기
