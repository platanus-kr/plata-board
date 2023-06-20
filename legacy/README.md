# Plata Board

Spring Boot 로 구현한 회원 관리와 파일 업로드 기능을 가진 기본 게시판.

---

## 어플리케이션 구성

```
Java 11, Gradle 7.1.1
Spring Boot 2.6.6
Spring Data JPA 2.6.6
Spring Security 5.6.2, JWT 4.0.0
MariaDB 10.x / H2 Database
```

## 주요 기능

**회원 기능** : 회원 가입

**게시판 기능** : 게시물 작성 / 수정 / 삭제, 댓글 작성 / 수정 / 삭제

**관리자 기능** : 게시판 생성 / 삭제

✨ v2 추가 - **REST API** : JWT 인증/인가, 회원 기능, 게시판 기능

✨ v2 추가 - **파일 첨부 기능** : 파일 업로드, http 서빙, 삭제

---

## 실행과 서비스 배포

서비스를 실행하기 위해 3가지 옵션이 준비되어 있습니다.

1. 로컬 실행
2. 서버 실행

이 프로젝트는 별도의 릴리즈 없이 `master` 브랜치를 그대로 clone 받아 실행할 수 있습니다.

### 1. 로컬 실행

Java 11만 준비되어 있다면, 내장된 `gradlew`를 통해 아래와 같이 쉽게 로컬에서 실행할 수 있습니다.

```bash
sh gradlew bootJar
java -jar build/libs/plata-board-0.0.2.jar
```

<details>
<summary> 구) V1 Thymeleaf 실행</summary>

이후 다른 작업 없이 `http://localhost:8080`로 바로 접속 가능합니다. 기록한 데이터는 서버가 종료되면 모두 사라집니다. (H2 메모리 데이터베이스)

</details>

### 2. 서버 실행

로컬실행은 메모리DB로 서버가 종료되면 데이터가 모두 사라지는 대신 `production`로 구분된 이 환경은 MariaDB를 통해 데이터를 영속할 수 있습니다.

<!-- 준비된 서버나 인스턴스가 없다면 [이 문서](https://platanus.me/post/1586) 를 통해서 참고할 수 있습니다. (CentOS 기준) -->

Java 11버전과 MariaDB 10버전을 설치해 주시고 적절한 데이터베이스 하나만 만들어주시면 됩니다.

#### DB 준비

스키마 정보는 `src/main/resources/db/schema.sql`에 위치합니다. 이 스키마를 이용하여 테이블을 생성할 수 있습니다.

```bash
mysql -u USER -p PASSWORD < src/main/resources/db/schema.sql
```

#### DB 연결을 위한 환경변수 설정

MariaDB 연결을 위해 아래의 환경변수 설정이 필요합니다.

- `WEBBOARD_MARIADB_JDBC` : Database jdbc 주소
- `WEBBOARD_MARIADB_ID` : DB 접근 아이디
- `WEBBOARD_MARIADB_PASSWORD` : DB 접근 비밀번호
- `WEBBOARD_FRONTEND_ADDRESS` : CORS 허용을 위한 프론트엔드 주소 (http 포함한 주소)
- `WEBBOARD_ATTACH_FILE_STORAGE_PATH` : 첨부파일 저장을 위한 파일시스템 경로 (절대경로)
- `WEBBOARD_LOGGING_STORAGE_PATH` : 로그 저장을 위한 파일시스템 경로 (절대경로)

<details>
<summary>Bash 를 사용하는 Linux 에서 환경변수 설정하기 </summary>

```bash
cat << "EOF" >> ~/.bash_profile
export WEBBOARD_MARIADB_JDBC=jdbc:mariadb://localhost:3306/webboard
export WEBBOARD_MARIADB_ID=webboard
export WEBBOARD_MARIADB_PASSWORD=webboardPassword
export WEBBOARD_FRONTEND_ADDRESS=http://localhost:3000
export WEBBOARD_ATTACH_FILE_STORAGE_PATH=$PWD/attach
export WEBBOARD_LOGGING_STORAGE_PATH=$PWD/log
EOF
source ~/.bash_profile
```

</details>

#### 서버 실행하기

모든 환경이 준비되었다면 아래의 명령어로 쉽게 실행할 수 있습니다.

```bash
sh gradlew bootJar
java -jar build/libs/plata-baord-0.0.2.jar --spring.config.location=classpath:/application.properties --spring.profiles.active=release
```

---

## 지속적 배포

GitHub Actions 을 활용하여 Continuous Deploy 를 할 수 있습니다. master 브랜치에 merge 가 되면 자동 배포를 합니다.

GitHub Secrets에 등록할 변수는 다음과 같습니다.

| Secret        | 설명                   |
| ------------- | ---------------------- |
| WEBBOARD_HOST | 호스트의 주소          |
| WEBBOARD_PORT | 호스트의 SSH 포트      |
| WEBBOARD_ID   | 호스트의 계정          |
| WEBBOARD_KEY  | 호스트의 SSH KEY       |
| DIST_PATH     | 호스트에서 실행할 경로 |

---

## 버전 히스토리

**2021.08.26** version 1 : Spring Boot + Thymeleaf 기본 게시판

**2022.08.27** version 2 : REST API, Spring Security, JWT

**2023.01.19** version 2 (minor update) : Spring Data JPA 도입
