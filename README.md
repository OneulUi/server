# 오늘의
<p align="center">
  <img width="297" alt="스크린샷 2024-05-18 오전 12 33 52" src="https://github.com/OneulUi/server/assets/96982575/4c4903e4-7e31-4d47-803f-fec3813118c0">
</p>
<h3 align="center"> 오늘 날씨를 기반으로 한 의상 추천 서비스, 오늘의</h3>

**배포링크**: null

## 팀 소개
- 팀명: 오늘의 팀
- 프로젝트 기간: 2024.04 ~ 2024.05

## 서비스 소개
**오늘의**는 사용자에게 현재 위치의 날씨를 기반으로 의상을 추천해주는 서비스입니다. 3일 뒤의 날씨를 시간 단위로 분석하여 외출 전에 날씨에 적합한 옷차림을 고민하는 번거로움을 덜어주고, 편리하고 실용적인 의상 선택을 도와드립니다. 

### 주요 기능

- **실시간 날씨 정보 제공**: 사용자의 현재 위치를 기반으로 실시간 날씨 정보를 제공합니다.
- **맞춤형 의상 추천**: 날씨 정보를 바탕으로 적합한 의상을 추천합니다.
- **시간 단위 날씨 예보**: 3일 뒤 까지의 날씨를 시간 단위로 제공하여 더욱 정확한 의상 선택을 도와줍니다.
- **사용자 맞춤 설정**: 설문을 기반으로 가중치를 정해 구체적인 의상 추천 알고리즘을 개발하였습니다. 이를 통해 개인의 스타일, 선호도, 체질 등을 반영한 의상 추천 기능을 제공합니다.
- **다양한 날씨 조건 지원**: 맑음, 흐림, 비, 눈 등 다양한 날씨 상황에 맞춘 추천을 제공합니다.

### 기술 스택 

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![H2](https://img.shields.io/badge/H2-0078D4?style=for-the-badge&logo=h2&logoColor=white)

![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### ERD
![스크린샷 2024-05-18 오전 12 27 13](https://github.com/OneulUi/server/assets/96982575/b4923ffe-e221-4e1a-8d86-d8b9341a74fc)

### 아키텍처 개요

- 프로젝트 구조
  - src/main/java/com/oneului
  - common - 공통 유틸리티 및 상수
  - config - 설정 클래스
  - controller - REST 컨트롤러
  - dto - 데이터 전송 객체
  - exception - 예외 처리
  - model - 엔티티 및 데이터 모델
  - repository - 데이터 액세스 계층
  - security - 보안 설정 및 유틸리티
  - service - 비즈니스 로직
  - util - 유틸리티 클래스

 ## API 명세서
 
<img width="1054" alt="스크린샷 2024-05-18 오전 1 04 40" src="https://github.com/OneulUi/server/assets/96982575/f5487ed3-0214-4c07-b112-10892693ca85">

<p align="center">
  Made with by 오늘의 팀
</p>
