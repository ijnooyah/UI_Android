# UI_Android
학생정보관리프로그램
요구사항들
  1. 데이터베이스 관련
    - 사용자계정 EXAM01/1234
    - 학생테이블 
      -테이블명: TBL_STUDENT
      - 학번(SNO, 기본키, 문자타입 8자)
      - 학생명(SNAME, not null, 문자타입 10자)
      - 학년 (SYEAR, not null, 숫자타입 1자리)
      - 성별 (GENDER, not null, 남/여)
      - 전공 (MAJOR, not null, 문자타입 10자리)
      - 점수 (SCORE, default 0, not null, 숫자타입 3자리 0~100)
   - 학생정보에 대해 CRUD작업 진행할 수 있어야함
 2. UI 관련 
    - 성별 UI 라디오버튼, 검색옵션 학생명 또는 전공으로 검색 가능하게 하기
    - 입력 및 수정부분은 새로운화면이나 대화상자를 이용하기
    - 입력값 체크에대한 처리결과 각 UI에 맞는 알림 내용, 알림창, 대화상자 등을 사용
    - 목록 출력 화면 Swing - TextArea, Android-리스트뷰, JSP-테이블 이용
