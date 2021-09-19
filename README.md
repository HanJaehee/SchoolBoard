# 학교 게시판

## 목적
Java, Spring, JPA 등 습득한 여러 개념들을 활용해 보기 위해 학교와 게시판이라는 가상의 모델을 만들어 구현하기 위함입니다.

## 기능
1차
1. 학교 게시판에서는 자유, 과목별 게시판이 존재한다.
2. 학생은 자유게시판과 자신이 수강하는 과목 게시판만 볼 수 있고, 쓸 수 있다. 수정, 삭제는 자신이 작성한 게시물만 가능하다.
3. 교수는 소속되어 있는 과목이 있지만 모든 게시판을 조회할 수 있다.   
단, 쓰기는 자유, 자신이 속한 과목만 가능하며 수정, 삭제는 자신이 작성한 게시물만 가능하다.
4. 게시판 담당자는 모든 게시판의 조회, 쓰기, 수정, 삭제가 가능하다.

## 기능 명세

1. 전공 등록
   * 기능 : 전공을 등록합니다.
   * 요청 : 전공 이름
   * 응답 : 전공 ID, 전공 이름

2. 유저 등록
    * 기능 : 유저를 학생, 교수 등록합니다.
    * 요청 : 이름, 주소, 전화번호, 생년월일, 타입(0(학생) or 1(교수))
    * 응답 : 학생 ID, 이름, 주소, 전화번호, 생년월일

3. 교수 과목 등록
    * 기능 : 교수님이 과목을 등록합니다.
    * 요청 : 과목 이름
    * 응답 : 과목 ID, 과목 이름

4. 학생 과목 신청
    * 기능 : 학생이 과목에 수강 신청 합니다.
    * 요청 : 학생 ID, 과목 ID
    * 응답 : 학생의 수강 과목 리스트

5. 게시글 작성
    * 기능 : 유저(학생, 교수)가 게시판에 글을 작성합니다.
    * 요청 : 유저 ID, 과목 ID, 제목, 내용
    * 응답 : 글 ID, 제목, 내용, 작성 일자, 수정 일자, 조회수

6. 자유 게시판 조회
    * 기능 : 유저(학생, 교수)가 자유 게시판을 조회합니다.
    * 요청 :
    * 응답 : [ 글 ID, 제목, 작성 일자, 조회수 ] 리스트

7. 게시판 조회
    * 기능 : 유저(학생, 교수)가 과목 게시판을 조회합니다.
    * 요청 : 유저 ID, 글 ID
    * 응답 : [ 글 ID, 제목, 작성 일자, 조회수 ] 리스트

8. 본인 게시글 목록 조회
    * 기능 : 유저(학생, 교수)가 본인이 작성한 글들을 조회합니다.
    * 요청 : 유저 ID
    * 응답 : [ 글 ID, 제목, 작성 일자, 조회수 ] 리스트

9. 게시글 상세 조회
    * 기능 : 유저(학생, 교수)가 과목 게시글 상세를 조회합니다.
    * 요청 : 유저 ID, 글 ID
    * 응답 : 글 ID, 제목, 내용, 작성 일자, 수정 일자, 조회수

10. 게시글 수정
    * 기능 : 유저(학생, 교수)가 자신이 작성한 글을 수정합니다.
    * 요청 : 학생 ID, 글 ID ID, 제목, 내용
    * 응답 : 글 ID, 제목, 내용, 작성 일자, 수정 일자

11. 게시글 삭제
    * 기능 : 유저(학생, 교수)가 자신이 작성한 글을 삭제합니다.
    * 요청 : 학생 ID, 글 ID
    * 응답 : 없음
    

## ERD
[https://www.erdcloud.com/d/8PyH4Y73bqyapMu3m](https://www.erdcloud.com/d/8PyH4Y73bqyapMu3m)

## Environment
* Java 8
* Spring Boot 2.5.4
* H2 1.4.99

## 구현방식

### 계획

TDD(Test Driven Development)로 개발을 진행하려 시도합니다.

1. 실패하는 단위 테스트를 작성하고,
2. 프로덕션 코드를 작성해 테스트를 통과 시킵니다.
3. 위 과정을 반복하고, 리팩토링을 진행합니다.

Entity를 기본적으로 생성해둔 상태에서 기능 명세 번호를 순서로 Repository -> Service -> Controller 흐름으로 구현할 것입니다. Repository는 통합테스트를, Service에서는
Mockito를 활용한 Mock 테스트를, Controller에서는 MockMvc를 활용할 것입니다.

### 실제

완성 후 회고를 하며 기록할 예정입니다.

### 의문점

1. 테스트를 Mock으로 작성하면서 내 손으로 직접 Mock을 구현하다보니 문득 이런 생각이 들었다. Mock의 뜻인 가짜를 만들어놓고 내가 예상하는 요청, 응답 값을 기준으로 테스트가 진행되다보니 이 Mock을
   만드는 단계에서 실수가 일어 난다면..? 차라리 속도 차이가 나더라도 실제 구현 인스턴스를 불러와 구현하는게 정확도 측면에서 낫지 않을까.. 라는 생각을 하면서 좀 더 테스트에 신중하고 다양한 케이스를 생각하는
   것이 TDD의 이점인가..? 