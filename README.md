# 톰과 제리 게임
톰은 제리를 잡아가두고, 제리는 집을 탈출해야하는 생존-탈출 장르의 멀티플레이 게임입니다.

# Mini-spec

1. 회원가입 처리 (완료)

    BEGIN
   	
	1.1 아이디, 비밀번호, 비밀번호 확인, 닉네임, 이메일, 생년월일, 전화번호를 입력받아 유저 객체에 담는다.
   	
	1.2 서버에 유저 객체를 전달한다.
   	
	1.3 IF 유저 객체에 대한 유효성 검증 통과 THEN
   	
        1.3.1 DB에 유저 정보를 추가한다.

        1.3.2 클라이언트에게 회원가입을 성공했음을 알린다.
    
    1.4 ELSE
        
        1.4.1 클라이언트에게 회원가입에 실패했음을 알린다.
    
	END



2. 로그인 처리 (완료)

    BEGIN
   
    1.1 아이디와 비밀번호를 입력받아, 로그인 객체에 담는다.
  
    1.2 로그인 객체를 클라이언트에서 서버로 전달한다.
 
    1.3 서버에서 로그인 객체에 대한 유호성 검증을 진행한다.

    1.4 IF 로그인 유효성 검증 통과 THEN
        
        1.4.1 로그인 정보에 해당하는 유저 정보를 DB에서 꺼내온다.
		
        1.4.2 유저 정보를 클라이언트에게 전달함으로써 로그인을 성공하였음을 알린다.
        
        1.4.3 클라이언트는 유저 정보를 가지고 로비 화면으로 이동한다.
   
    1.5 ELSE
       
	   1.5.1 로그인에 실패했음을 유저에게 알린다.
	
	END


3. 클라이언트 로비 접속 구현 (완료)
    
	BEGIN
	
    3.1 로비에 접속한 유저의 정보를 리스트에 담는다.

	3.2 접속 중인 유저 리스트 정보를 클라이언트에게 전달한다.

	3.2 대기방 리스트 정보를 클라이언트에게 전달한다. (대기방 생성 기능 구현할 때 같이 만들거임)

    END
	

4. 로비 채팅 시스템 구현 (완료)

    BEGIN

    4.1 클라이언트에서 서버로 메시지를 전송

    4.2 서버는 클라이언트로부터 받은 메시지를 chatList에 등록되어있는 클라이언트들에게 브로드캐스팅
	
    4.3 클라이언트는 서버로부터 받은 메시지를 채팅창에다가 append 한다.

    END

5. 대기방 생성 (완료)

    5.1 버튼을 누르면 방 생성 화면이 뜬다.
    
	5.2 방제목, (비밀번호)를 입력하고, 생성버튼을 누르면 대기방 정보들이 서버로 전달된다.
    
	5.3 서버는 대기방 정보를 받아서 서버에 대기방 정보를 등록한다.
    
	5.4 서버는 대기방 생성을 클라이언트에게 알린다.
    
	5.5 클라이언트 화면이 로비 화면에서 대기방 화면으로 전환된다.

6. 대기방 조회 (2020-11-10 예정)

7. 대기방 접속

8. 게임 진행

## 리팩토링 필요사항

1. 모든 화면 GUI GridBagLayout으로 변경해야 함.

## 중간발표 들어갈 내용 및 순서

1. 프로젝트 주제 간단 소개

2. 개발 해야하는 기능 목록 소개 (진행완료, 진행중, 진행예정 단위로 소개)

3. 프로젝트 준비 과정 소개 (트렐로, Sourcetree, ERD)

4. 만든 부분까지 발표 시연