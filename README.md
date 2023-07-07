## 🎯프로젝트 소개

‘**CardTracker**’ 는 미니 카드사 시스템이다. 유저는 보유한 카드로 결제를 진행할 수 있고 계좌에 잔액을 충전할 수 있다. 시스템 관리자는 여러 데이터를 바탕으로 결제 내역을 조회하고 관리한다. 평소 국민 대부분이 사용하는 카드 시스템을 프로토타입으로 만들어보며 DB 설계와 시스템 구현 등에 대해 고민해보고자 개발하게 되었다.

## 📚기술스택

개발환경 : <img src="https://cdn.devinflearn.com/public/files/posts/45a25c80-b53e-4631-b9b9-210ff1daeeec/Java-Logo.png" height="50px">
<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2hdDmVLdRKwnPkPLPTLR88XgFPTz3CQkNeA&usqp=CAU" height="50px">


사용한 기술스택 :<img src="https://miro.medium.com/v2/resize:fit:512/0*X9ZtftD7LUqjD6PW" height="50px"> 
<img src="https://images.velog.io/images/co_der/post/6e06267d-ef83-448e-a7db-191c9a3ea981/Apache-maven.jpg" height="50px">
<img src="https://velog.velcdn.com/images/suyyeon/post/b5f4087d-ef66-43e7-82f6-94ec53674453/image.png" height="50px">
<img src="https://upload.wikimedia.org/wikipedia/commons/d/d5/CSS3_logo_and_wordmark.svg" height="50px">
<img src="https://images.velog.io/images/reveloper-1311/post/0b1dbd85-4a78-4d50-9923-e1113eb279c1/JS.png" height="50px">
<img src="https://www.earlysoft.co.kr/wp-content/uploads/2019/05/bootstrap-tutorial.png" height="50px">


배포환경 : 
<img src="https://a0.awsstatic.com/libra-css/images/logos/aws_logo_smile_1200x630.png" height="50px">
<img src="https://i0.wp.com/bespin-wordpress-bucket.s3.ap-northeast-2.amazonaws.com/wp-content/uploads/2022/01/image.png?resize=410%2C449&ssl=1" height="50px">
<img src="https://lh3.googleusercontent.com/4Quj4qB7UEu3FQu6fsRXzY5M1Kr8vXqUj1JUQIh5ZyCE3B70lClHHvNXbDyog-39Ce8ib8AGi26PITD9Kk8SO1fKQ_1Dal1Sqd-swunWGjzYz2vaR81X48XdqExCiw0r8mTOO-Hv" height="50px">
<img src="https://www.yamamanx.com/wp-content/uploads/2020/06/acm-icon.png" height="50px">
<img src="https://inceptivetechnologies.com/wp-content/uploads/2022/09/amazon-rds.png" height="50px">


DB :<img src="https://i0.wp.com/thinkground.studio/wp-content/uploads/2019/04/190419_MySQL-Logo.png?resize=363%2C224" height="50px">


협업 : <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" height="50px">
<img src="https://play-lh.googleusercontent.com/ht8BdWPiUAsAeVx9SKVtFOX774DgRNYov896FM-fgYVPBvzze6_KgM2nRhO1OUQbVY8=w600-h300-pc0xffffff-pd" height="50px">

## ☁️시스템 아키텍쳐


![KakaoTalk_20230703_005550521](https://github.com/CardSystem/Server/assets/86733856/fc913133-0ee9-48e5-9d00-86fd04a55660)



## ⚒️기술적 고려 사항

### 🔨동시성 이슈 처리

1. 문제점
    
    한 계좌에 3개의 카드가 연결될 수 있어 각각의 카드로 동시에 결제하는 일이 생길 때 계좌의 잔액을 읽는 과정에서 커밋되지 않은 값을 읽는 Dirty Read가 발생해 데이터 정합성이 깨지는 문제 발생
    
2. 해결 방법 후보
    - DB 쿼리에 for update 문을 추가하여 비관적락 구현하기
    - Redis를 이용해 분산락 구현하기 → **채택 ✅**
3. 자세한 설명
    
    Redisson을 이용해 스핀락 구현. 데이터 정합성 유지
    

    
5. 결과
    
    동시성 테스트를 위해 CompletableFuture 라이브러리를 이용해 비동기 이벤트를 발생시킴. 락을 걸기 전에는 스레드가 접근하는 순서에 따라 잔액 차감의 순서가 달라지고 커밋 전 데이터를 읽음으로써 Dirty Read가 일어나고 실제 남아야하는 잔액과 달라지는 케이스가 발생했다.
    
    **적용 전**
   
    ![Untitled (5)](https://github.com/CardSystem/Server/assets/86733856/6c15b832-323d-4554-a5a0-543e460d1042)
![Untitled (1)](https://github.com/CardSystem/Server/assets/86733856/5d519c73-adb1-4ac8-b79b-e5f3f313f3bc)

    **적용 후**

   ![Untitled (6)](https://github.com/CardSystem/Server/assets/86733856/e795eb69-a8ab-415d-8201-05897198e6ad)

    분산락 적용 시에는 데이터의 정합성이 유지되며 계좌 잔액이 올바르게 유지되는 것을 알 수 있다.
    

### 🔨DB 설계

1. 외래키 지양 이유
    - 카드와 계좌를 이용한 결제 시스템은 데이터를 보존하는 것이 중요한 시스템이기 때문에 데이터가 실수로 변경되거나 오염되는 경우 참조되는 모든 릴레이션이 영향받는 것 방지
    - 카드 시스템은 대규모 시스템으로 확장될 필요가 있는데 fk를 추가 시 데이터를 생성 및 수정시 항상 무결성 검사를 거치기 때문에 insert/update 시 성능 저하 방지
    -  확장성 및 개발 용이성을 고려
2. 계좌 당 카드갯수 제한 방법
    - 계좌-카드갯수를 저장하는 새로운 테이블 생성
    - 카드를 추가할 때마다 전체 카드 테이블을 COUNT 함수로 조회해 그 값을 기준으로 예외 처리 → **채택 ✅**

### 🔨배포 시 SSL 인증

- SSL 인증을 위해 AWS의 Route 53, Certificate Manager를 사용했다. 도메인은 가비아에서 구매했다.

### 🔨BusinessException 처리

- enum으로 ErrorCode를 만들어서 statusCode, statusMessage, message를 관리
- business custom exception을 손쉽게 관리하기 위함

### 🔨유닛테스트 (feat, JUnit5, Mockito)

- Mock 객체를 이용해 레이어 간 결합성을 끊고 DB에 의존하지 않는 순수 Service 로직을 테스트 하고자 했다.
- 실제 운영하는 DB 서버에 문제가 생기더라도 테스트는 성공할 수 있도록 했다.
- 실패 케이스와 성공 케이스를 모두 작성했다. 실패 케이스를 작성한 이유는 위에서 언급했듯이 FK를 연결하지 않았기 때문에 어플리케이션 단에서 꼼꼼하게 예외처리를 해서 무결성을 유지하도록 했는데 실패 시 서버단에서 예외를 잘 처리하는지도 테스트해야하기 때문이다.
