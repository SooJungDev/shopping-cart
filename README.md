# shopping-cart 🛒🛒🛒
- 이 프로젝트는 SpringBoot 를 사용하여 간단한 상품 목록과 장바구니 기능을 구현한 프로젝트입니다.

## 서비스
웹 페이지 서비스는 로그인, 상품 목록, 상품 상세 조회, 장바구니 목록,추가,삭제 로 구성되어 있습니다.

## 구축환경
- 구축 환경은 아래와 같습니다.
    - Java 13
    - Spring Boot 2.2.5.RELEASE
    - Spring Security
    - Lombok
    - H2 DB
    - JUnit5
    - Vue.js
    - Vuex


## Quick Start
Backend 실행 방법
- 프로젝트가 설치된 곳으로 이동
- ./mvnw install
- ./mvnw spring-boot:run 명령어 입력

ex)
~~~
crystal-mac:shopping-cart crystal$ pwd
/Users/crystal/shopping-cart
crystal-mac:shopping-cart crystal$ ./mvnw install
crystal-mac:shopping-cart crystal$ ./mvnw spring-boot:run
~~~

Front 실행 방법
- 프로젝트가 설치된 곳에서 frontend 폴더로 이동
- npm install 명령어 입력
- npm run dev 명령어 입력

ex)
~~~~
cd frontend
npm install
npm run dev
~~~~

