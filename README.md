# shopping-cart 🛒🛒🛒
- 이 프로젝트는 SpringBoot , Vue.js 을 사용하여 간단한 상품 목록과 장바구니 기능을 구현한 프로젝트입니다.

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
### Backend 실행 방법
- 프로젝트가 설치된 곳으로 이동
- ./mvnw install 명령어 입력
- ./mvnw spring-boot:run 명령어 입력

### Backend 실행 방법 예제
~~~
crystal-mac:shopping-cart crystal$ pwd
/Users/crystal/shopping-cart
crystal-mac:shopping-cart crystal$ ./mvnw install
crystal-mac:shopping-cart crystal$ ./mvnw spring-boot:run
~~~

### Frontend 실행 방법
- 프로젝트가 설치된 곳에서 frontend 폴더로 이동
- npm install 명령어 입력
- npm run dev 명령어 입력

### Frontend 실행 방법 예제
~~~~
crystal-mac:shopping-cart crystal$ cd frontend
crystal-mac:frontend crystal$ npm install
crystal-mac:frontend crystal$ npm run dev
~~~~

## API
| Method | Path | Description | User authenticated |
|--------|-----------------------|-----------------------------------------------------------------|--------------------|
| POST | /auth/login | 가입한 아이디/패스워드를 통해 로그인 서비스를 제공합니다. | X |
| POST | /auth/refresh | 해당 사용자의 토큰을 갱신합니다. | X |
| POST | /auth/change-password | 해당 사용자의 패스워드를 변경합니다. | X |
| POST | /auth/register | 회원 가입 서비스를 제공합니다 | X |
| GET | /cart/{user_id} | 해당 사용자의 장바구니를 조회합니다. | O |
| POST | /cart | 해당 사용자의 장바구니에 특정 상품을 추가합니다. | O |
| DELETE | /cart | 해당 사용자의 장바구니에 특정 상품을 삭제합니다. | O |
| PATCH | /cart | 해당 사용자의 장바구니에 특정 상품 정보를 삭제합니다. | O |
| POST | /cart/purchase-info | 해당 사용자의 장바구니에 체크된 상품들의 가격정보를 제공합니다. | O |
| GET | /goods | 전체 상품 목록을 조회 합니다. | X |
| GET | /goods/{goods_id} | 특정 상품을 조회 합니다. | X |