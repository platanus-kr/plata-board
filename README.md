# Spring Web board project

- 회원 관리 기능을 가진 간단한 웹 게시판 서비스 입니다.
- 프로젝트에 대한 제작 과정과 소개는 [이 문서](https://platanus.me/post/1592)를 참고해 주세요.
- 현재 이 문서는 작성중 입니다.

## 실행과 배포

서비스를 실행하기 위해 3가지 옵션이 준비되어 있습니다.

- 로컬 실행
- 서버 실행
- Continuous Deploy

이 프로젝트는 별도의 릴리즈 없이 `master` 브랜치를 그대로 clone받아 실행할 수 있습니다.

### 로컬 실행

Java 11만 준비되어 있다면, 내장된 gradle을 통해 아래와 같이 쉽게 로컬에서 실행할 수 있습니다.

- Linux/OS X

```bash
sh gradlew build
java -jar build/libs/webboard-0.0.1-SNAPSHOT.jar
```

- Windows

```batch
gradlew.bat build
java -jar build/libs/webboard-0.0.1-SNAPSHOT.jar
```

이후 다른 작업 없이 `http://localhost:8080`로 바로 접속 가능합니다. 기록한 데이터는 서버가 종료되면 모두 사라집니다. (H2 메모리 데이터베이스)

### 서버 실행 (실제 서버 및 인스턴스)

로컬실행은 메모리DB로 서버가 종료되면 데이터가 모두 사라지는 대신 release로 구분된 이 환경은 MariaDB를 통해 데이터를 영속할 수 있습니다.

준비된 서버나 인스턴스가 없다면 [이 문서](https://platanus.me/post/1586)를 통해서 참고할 수 있습니다. (CentOS 기준) 만약 준비가 가능하다면 Java 11버전과 MariaDB
10버전을 설치해 주시고 적절한 데이터베이스 하나만 만들어주시면 됩니다.

#### DB 준비

아래 명령어를 통해 준비된 스키마를 입력할 수 있습니다.

```bash
mysql -u {사용자이름} -p {데이터베이스이름} < src/main/resources/db.sql
```

#### 환경변수 설정

아래의 환경변수 설정이 필요합니다.

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
sh gradlew build
java -jar build/libs/webboard-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application.properties --spring.profiles.active=release
```

#### 웹에 개시하기

웹 게시판 서비스는 8080포트로 서버 로컬 실행 됩니다. 만약 도메인이나 80포트 또는 SSL 통신이 필요하다면 [이 문서](https://platanus.me/post/1590)를 통해서 Reverse proxy
설정을 할 수 있습니다.

### Continuous Deploy

자동 배포와 지속적인 개시를 위해 GitHub Actions을 사용한 CD가 가능합니다. 준비된 CI/CD를 구축하시려면 [이 문서](https://platanus.me/post/1588)를 참고할 수 있습니다.

GitHub Secrets에 등록할 변수는 다음과 같습니다.

```
WEBBOARD_HOST : 호스트의 주소
WEBBOARD_PORT : 호스트의 SSH 포트
WEBBOARD_ID : 호스트의 계정
WEBBOARD_KEY : 호스트의 SSH KEY
```

## 사용된 기술셋

### 실행을 위한 최소한의 환경

- Java 11 또는 OpenJDK 11
- MariaDB-server 10.x

### 기술셋 내역

- Spring Boot 2.5.2
- Spring Data
- Spring JDBC (JdbcTemplate)
- MariaDB 10.x
- H2 Database
- Thymeleaf 3.0.12
- Hibernate Validator
- Gradle 7.1.1
