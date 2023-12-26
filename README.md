<div align="center">
<img src="https://file.notion.so/f/f/3bb498cb-60fe-429b-a51f-807fedf6cdc4/bb0578ad-bf3b-4a12-a295-e3ca7966061b/Untitled.png?id=da58b5f4-878d-4338-aad4-5775e3ec804f&table=block&spaceId=3bb498cb-60fe-429b-a51f-807fedf6cdc4&expirationTimestamp=1703700000000&signature=CInnZp6KjqjL-NwZJXSn2L7CaRvEEH5YCNLSpdp1c4g&downloadName=Untitled.png" width="80%" height="80%"/>
<h3 align="center">INBA-Backend</h3>
<p>인하대학교 컴퓨터공학과 학생들을 위한 졸업수강지도 서비스</p>
</div>
</br>
</br>

## 개발 배경
인하대학교 컴퓨터공학과 수강지도 서비스 INBA는 교과 과정의 개편 및 학적 변동에 따른 졸업 요건의 변화를 고려하지 않는 현재의 인하대학교 졸업 요건 확인 시스템을 개편하기 위해 개발된 서비스입니다.

이용자가 인하대학교 포털사이트에서 다운로드 할 수 있는 참고용 성적표 pdf 파일을 업로드 하면, 이를 스캔하여 이수내역을 저장합니다.

이후 사용자는 학번/다중전공을 반영한 졸업 진단, 추후 수강해야 하는 과목 안내, 사용자의 미래 수강계획을 입력 받아 졸업 요건 재확인 받는 서비스를 이용할 수 있습니다.

</br>

## 구현 기능
**회원관리**

- Spring Security + JWT를 이용한 로그인 구현
- 인하대학교 학생 계정(@inha.edu) 이메일 인증

**공지사항 확인**

- JSoup 라이브러리를 이용하여 인하대학교 컴퓨터공학과 졸업관련 공지 크롤링

**졸업요건 검사**

- PDFBox 라이브러리를 이용하여 성적표 pdf 파일을 png 파일로 변환
- CLOVA API를 이용하여 성적표 파일 중 중요부분 추출 후 OCR 기능 구현
- 사용자 이수내역 관리를 통해 졸업 가능 여부, 추후 수강해야 하는 과목 안내 기능 구현
- 사용자의 미래 수강계획을 입력 받아 졸업 요건 재확인 기능 구현

**서버 배포**

- AWS EC2를 이용한 서버 배포 구현
- AWS ROUTE53, ELB, ACM을 이용한 도메인 연결 및 Https 설정

</br>

## Structure
<div align="center">
<img src="https://file.notion.so/f/f/3bb498cb-60fe-429b-a51f-807fedf6cdc4/20242604-0aae-44ed-b9c8-1db4f79222f0/Untitled.png?id=7fd1f8b0-94d2-48c3-8b37-b7e427977209&table=block&spaceId=3bb498cb-60fe-429b-a51f-807fedf6cdc4&expirationTimestamp=1703700000000&signature=mWr0ovV1WIf9o-Oo9eAMmBJL84O5Q23dEBOGEHCkuu0&downloadName=Untitled.png" width="80%" height="80%"/>
</div>
</br>

## Bulit with
- Spring Boot
- Spring Security
- JSON Web Token
- Spring Data JPA
- Springfox Swagger UI
- MySQL
- Amazon Web Services
- pdfbox
- imgscalr
- jsoup
  
