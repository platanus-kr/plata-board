# Plata Board

- Spring Boot와 JdbcTemplate로 구현한 회원 관리 기능을 가진 게시판 입니다.

* * *

## 💻 개발 환경

```
Java 11
MariaDB 10.x / H2 Database
Gradle 7.1.1
Spring Boot 2.6.6
Spring JDBC (JdbcTemplate)
Spring Security 5.6.2
JWT 4.0.0
Thymeleaf 3.0.15 - deprecated
Hibernate Validator
commonmark-java (Markdown Parser)
React V6
```

## 🛠️ 기능 구현

**회원 기능** : 회원 가입

**게시판 가능** : 게시물 작성/수정/삭제, 댓글 작성/수정/삭제

**관리자 기능** : 게시판 생성/삭제

(v2 추가) **REST API** : JWT 인증/인가, 회원 기능, 게시판 기능

## 🪧 버전 히스토리

**2021.08.26** version 1 : Spring Boot + Thymeleaf 기본 게시판

**2022.08.27** version 2 : REST API, Spring Security, JWT, React

<!--
## 💡 서비스 구조

작성중

## 🗃 DB 스키마 구조

작성중

## 🔥 개발 과정

- 프로젝트에 대한 제작 과정과 소개는 [이 문서](https://platanus.me/post/1592) 를 참고해 주세요.
-->

* * *

## ⚙️ 실행과 배포

```
현재 프론트 분리 작업중이기 때문에 제대로 실행되지 않을 수 있습니다. (2022.09 완료 예정)
```

서비스를 실행하기 위해 3가지 옵션이 준비되어 있습니다.

1. 로컬 실행
2. 서버 실행

이 프로젝트는 별도의 릴리즈 없이 `master` 브랜치를 그대로 clone받아 실행할 수 있습니다.

### 1. 로컬 실행

<details>
<summary> 구) Thymeleaf 프론트 실행</summary>


Java 11만 준비되어 있다면, 내장된 gradle을 통해 아래와 같이 쉽게 로컬에서 실행할 수 있습니다.

- Linux/OS X

```bash
sh gradlew bootJar
java -jar build/libs/plata-board-0.0.2.jar
```

이후 다른 작업 없이 `http://localhost:8080`로 바로 접속 가능합니다. 기록한 데이터는 서버가 종료되면 모두 사라집니다. (H2 메모리 데이터베이스)

</details>

### 2. 서버 실행 (서버 및 인스턴스)

로컬실행은 메모리DB로 서버가 종료되면 데이터가 모두 사라지는 대신 release로 구분된 이 환경은 MariaDB를 통해 데이터를 영속할 수 있습니다.

준비된 서버나 인스턴스가 없다면 [이 문서](https://platanus.me/post/1586) 를 통해서 참고할 수 있습니다. (CentOS 기준) 만약 준비가 가능하다면 Java 11버전과 MariaDB
10버전을 설치해 주시고 적절한 데이터베이스 하나만 만들어주시면 됩니다.

#### DB 준비

스미카 정보는 src/main/resources/db/schema.sql 파일에 위치합니다. 이 스키마를 이용하여 테이블을 생성할 수 있습니다.

```bash
mysql -u USER -p PASSWORD < src/main/resources/db/schema.sql
```

#### DB 연결을 위한 환경변수 설정

MariaDB연결을 위해 아래의 환경변수 설정이 필요합니다.

```bash
WEBBOARD_MARIADB_JDBC
WEBBOARD_MARIADB_ID
WEBBOARD_MARIADB_PASSWORD
```

다음은 예시 입니다. (Bash를 사용하는 Linux 환경)

```bash
cat << "EOF" >> ~/.bash_profile
export WEBBOARD_MARIADB_JDBC=jdbc:mariadb://localhost:3306/webboard
export WEBBOARD_MARIADB_ID=webboard
export WEBBOARD_MARIADB_PASSWORD=webboardPassword
EOF
source ~/.bash_profile
```

#### 서버 실행하기

모든 환경이 준비되었다면 아래의 명령어로 쉽게 실행할 수 있습니다.

```bash
sh gradlew bootJar
java -jar build/libs/plata-baord-0.0.2.jar --spring.config.location=classpath:/application.properties --spring.profiles.active=release
```

#### 웹에 개시하기

<details>
<summary>구) Thymeleaf 프론트</summary>

웹 게시판 서비스는 8080포트로 서버 로컬 실행 됩니다.

만약 도메인이나 80포트 또는 SSL 통신이 필요하다면 [이 문서](https://platanus.me/post/1590) 를 통해서 Reverse proxy 설정을 할 수 있습니다.
</details>


* * *

## 🪄 Continuous Deploy

GitHub Actions을 활용하여 지속적인 배포를 할 수 있습니다. master 브랜치에 merge가 되면 자동 배포를 합니다.

GitHub Secrets에 등록할 변수는 다음과 같습니다.

|Secret| 설명           |
| --- |--------------|
|WEBBOARD_HOST| 호스트의 주소      |
|WEBBOARD_PORT| 호스트의 SSH 포트  |
|WEBBOARD_ID| 호스트의 계정      |
|WEBBOARD_KEY| 호스트의 SSH KEY |
|DIST_PATH| 호스트에서 실행할 경로 |
