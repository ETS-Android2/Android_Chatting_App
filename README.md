# Firebase 기반 Android 채팅 App

## ■ Preview

<details>
<summary><b>Show</b></summary>
<div markdown="1">

</br>
<figure>
    <div><b><i>■ Launch Application</i></b></div>
    <img src="/README_IMG/Start.gif" alt="앱 시작" width="40%" height="auto" />    
</figure>

</br>
</br>

<figure>
    <div><b><i>■ Enter the Chat Room</i></b></div>
    <img src="/README_IMG/enter_chat_room.gif" alt="채팅방 입장" width="40%" height="auto" />
</figure>

</br>
</br>

<figure>
    <div><b><i>■ Input Images</i></b></div>
    <img src="/README_IMG/input_img.gif" alt="채팅방 입장" width="40%" height="auto" />
</figure>

</br>
</br>

<figure>
    <div><b><i>■ Chagne Application Settings</i></b></div>
    <img src="/README_IMG/app_settings.gif" alt="채팅방 입장" width="40%" height="auto" />
</figure>

</div>
</details>

------------------------------

## ■ About Project ...
- 파이어베이스 서버 및 데이터베이스 관리를 통한 앱 내(內) 채팅 내역 관리.
- 1 대 다(多) 기반 채팅 환경 구축.
- 닉네임(이름) 설정 및 채팅방 생성 가능.
- 기존 개설된 채팅방(List 형태) 목록에서 선택 가능.
- STT(Speach To Text) 기능 구현.
- 파이어베이스 서버 스토리지 업로드 방식 이미지 첨부(Glide) 기능 제공.
- 스마트폰 내부 스토리지에 채팅 내역(로그) *.txt 파일 저장 가능.
- 설정(Setting)페이지 구현 및 테마 선택(Red, Blue, Green) 설정 및 초기화 가능.


------------------------------

## ■ Tech Stack

#### Server
 - Google Firebase
   - Firebase Realtime Database
   - Firebase Storage

#### 사용 언어 
 - JAVA
 - XML

#### 사용 툴
 - Android Studio
 - Adobe XD
 - Adobe Photoshop

#### SDK Version
 - Android Pie (API 28, 9.0)



-------------------------------

## ■ Build Logs
<details>
<summary><b>Show</b></summary>
<div markdown="1">

### [v1.1.5]
 - **시작 화면 하단부에 저작권 관련 문구 추가**
 - **종료 다이얼로그 이미지 추가**
 - **사용자 메뉴얼 다이얼로그 이미지 추가**

### [v1.1.4]
 - **PreferenceFragment 설정 페이지에 '설정 초기화' 버튼 추가**
   - **초기화 버튼 클릭 시에 모든 설정 Default 값으로 변경 (테마 설정 값 복원)**

### [v1.1.3]
 - **채팅방 목록, 채팅방 테마 변경 기능 추가**
    - **SharedPreference 사용**
    - **PreferenceFragment 설정에서 채팅 테마 설정 시 색상 변경 (RED / BLUE / GREEN)**

### [v1.1.2]
 - **채팅방 목록, 채팅방 테마 제작**
    - **Adobe XD 색상별 테마 제작 (레드, 블루, 그린 색상)**
    - **색상 테마 추가로 인한 Drawable 내(內) xml 파일 및 폴더 재정립(리네이밍).**



--------------------------------------------------
### [v1.1.1]
 - **뒤로가기(Back) 버튼 터치 시에 적용 여부 Alert 다이얼로그로 물어보기**
    - **설정 미적용으로 인한 충돌 방지**
    - **Alert 다이얼로그 함수화**
 - **테마 색상 변경 시에 커스텀 다이얼로그 색상도 함께 변경 (사용자 메뉴얼, 종료 다이얼로그)**

### [v1.1.0.1]
 - **앱 테마 색상 변경 기능 추가**
    - **설정모양 버튼 클릭 시에 팝업메뉴 호출로 색상 선택 가능 (RED / BLUE / GREEN)
      (팝업메뉴 대신 PreferenceFragment로도 대체 가능)**
    - **SharedPreference 매소드(Key값에 Value저장) 사용으로 앱 종료 시에도 테마 색상 초기화 없이 설정 유지 가능**

### [v1.1.0.2]
 - **설정 모양 버튼 클릭 시에 PreferenceFragment 페이지로 이동**
    - **테마 색상 선택 시 테마 색상 변경되도록 코드 수정(MainActivity)**

### [v1.0.9]
 - **Adobe XD 색상별 테마 제작 (레드, 블루, 그린 색상)**
 - **색상 테마 추가로 인한 Drawable 내(內) xml 파일 및 폴더 재정립(리네이밍).**



-----------------------------------------------
### [v1.0.8]
 - **Android-Studio 內 엑티비티 폴더별 정리.**
 - **첫 화면(MainStartActivity)에 종료 버튼 추가.**
 - **종료 버튼 또는 기기의 뒤로가기(Back) 버튼 터치시에 "종료하시겠습니까?" 다이얼로그 호출.**
    - **'취소' 시에 다이얼로그 dismiss**
    - **'종료' 시에 앱 종료 (finish)**

### [v1.0.7]
 - **채팅로그를 외부저장소(External Storage)에 저장.**
    - **기존에는 내부저장소(Internal Storage)에 저장되어서 접근에 불편함이 존재.**
    - **기기 기본(Default) 다운로드(Download) 폴더에 "yyyy-MM-dd-HH:mm:ss.txt" 형식으로 저장.**

### [v1.0.6]
 - **앱 실행 시에 권한 설정 팝업 출력**
    - **권한 설정 거부 시에 앱 종료.**

### [v1.0.5]
 - **STT(Speech To Text) 기능 추가**
    - **안드로이드 기본 내장 API 이용. (STT 호출 시 다이얼로그 팝업)**
 - **더 이상 기기 회전에 따른 앱 화면회전이 발생하지 않음.**
    - **매니패스트(Manifest)에서 옵션 추가**
---------------------------------------------
### [v1.0.4]
 - **채팅방 목록 다이얼로그(팝업) 백그라운드 이미지 추가(변경)**
    - **XD로 비율 재작업**

### [v1.0.3]
 - **사용 메뉴얼(설명창) 레이아웃 코드 수정**
    - **외형 변화 X**

### [v1.0.2]
 - **Android-Studio 內 엑티비티 폴더별 정리**
 - **채팅방 목록 다이얼로그(팝업)화**
    - **기존 새로운 레이아웃 호출 방식에서 다이얼로그(팝업) 호출 방식으로.**

### [v1.0.1]
 - **사용 메뉴얼(설명창) 팝업 추가**
    - **물음표(?) 버튼 클릭 시 다이얼로그(팝업) 호출**
    - **메뉴얼 내용 추후 추가 요망**

### [v1.0.0]
 - **프로젝트 이름 변경 (test.myapplication -> lee.woosuk)**
 - **앱 아이콘 제작 (아이콘.PSD)**
 - **레이아웃 배치 오류 수정**

### [v0.9.9]
 - **초기 버전 기반**

</div>
</details>

------------------------------------------
