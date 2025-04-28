# AllOfThem

## 🛠️ 프로젝트 소개
이 프로젝트는 **MVI (Model-View-Intent)** 와 **Clean Architecture**를 정리하기위해 만든 프로젝트로,
공공데이터포털의 기상청 초단기예보 데이터를 활용한 앱입니다.


## 📐 프로젝트 구조
- MVI (State, Event, Effect, Reducer)
- Clean Architecture


![Image](https://github.com/user-attachments/assets/ef80a2ca-5e3e-4587-8576-0a5523ddd0d9)

**Event(Intent)** : Event 를 받으면 Reducer 또는 UseCase 로 전달
</br>
**Reducer** : Event 를 수신하여 State 로 변환
</br>
**State** : 상태들을 저장 (주로 상태가 변경되었을 때 화면을 렌더링 하기위함)
</br>
**UseCase, Repository, Data** : Clean Architecture 형태로 domain 영역을 통해 data 영역에서 데이터들을 가져옴
</br>


### 사용한 주요 기술
- Kotlin
- Jetpack Compose
- Hilt (DI)
- Coroutine & Flow
- Retrofit2
- Location Service (Foreground/Background)



## 🧩 환경
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
