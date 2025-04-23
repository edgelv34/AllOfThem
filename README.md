# AllOfThem
주로 사용하는 기술들을 사용하여 만든 프로젝트

## 기술 스택
Clean Architecture: Domain, Data, Presentation(ui) 레이어 분리
</br>
MVI 패턴: Event(Intent), State, SideEffect, Reducer
</br>
Jetpack Compose: UI 구현
</br>
Coroutine & Flow: 비동기 처리
</br>
Dagger Hilt: 의존성 주입
</br>
Room: 로컬 데이터베이스
</br>
Retrofit: API 통신
</br>
CameraX: 카메라 기능
</br>
Location: 위치 정보
</br>

(현재는 위 기술을 기반으로 진행 예정)

## 환경 설정
### KAPT 와 KSP (ksp 지원하는 라이브러리는 ksp 로 진행)
https://developer.android.com/build/migrate-to-ksp?hl=ko
</br>
KAPT : 자바의 annotationProcessor 를 Kotlin 에 환경에 맞게 확장한 도구
</br>
KSP : KSP는 Kotlin 코드를 직접 분석하기 때문에 KAPT 에 비해 시간이 최대 2배 빠름. 또한 Kotlin 언어 구성을 더 잘 이해 함.
</br>
현재 지원하는 KSP 로 된 라이브러리 : Dagger(Hilt), Glide, Room, Moshi (계속 추가되고는 있다고 함.)
</br>
https://kotlinlang.org/docs/ksp-overview.html#supported-libraries 에서 확인가능.
</br>

## 프로젝트 구조 (clean architecture)
data, domain, ui 그리고 common, di 를 패키지단위로 구성 (소규모이므로, 모듈단위로 만들지 않음)
</br>
di 는 data/di, domain/di, ui/di 와 같이 하위에 넣어도 되지만, common 과 같은 level 에서 처리하도록 함.
</br>


