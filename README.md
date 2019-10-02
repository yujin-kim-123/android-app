# 어플 간략 설명
1. 좌우로 슬라이딩하여 웹 페이지 2개를 이용할 수 있도록 하였습니다.
2. 메뉴에서 사용자가 직접 어플 접속 시 사용할 2개의 웹 주소를 설정할 수 있도록 하였습니다.
3. 아직 부족한 것이 많아 계속 수정중인 프로젝트로 이 점 감안해주시면 감사하겠습니다.


# 어플 시작시
![1번](https://user-images.githubusercontent.com/51709213/66035709-9e6e2200-e546-11e9-93c4-39963ad67328.png)

SplashActivity를 사용하여 어플 이름을 넣어 3초간 화면이 나오게 했습니다.
(SplashActivity.java)


# 첫 화면
![2번](https://user-images.githubusercontent.com/51709213/66036080-5f8c9c00-e547-11e9-8f21-d5f3417df2a4.png)

첫번째 기본 페이지는 구글로 설정하였고 위, 아래의 주소 설정과 메뉴바는 아래로 드래그를 하였을 때 나오도록 하였습니다.
(MainActivity.java)


# 슬라이딩 후 두번째 화면
![3번](https://user-images.githubusercontent.com/51709213/66036088-63b8b980-e547-11e9-8579-e0e0f12a93c8.png)

두번째 기본 페이지는 다음으로 설정하습니다.
(슬라이딩-translate_left.xml, translate_right.xml 화면-MainActivity.java)

# 메뉴에 들어갔을때
![4번](https://user-images.githubusercontent.com/51709213/66036089-63b8b980-e547-11e9-8674-f0b6e687e781.png)

사용자가 처음에 나올 주소를 설정할 수 있도록 하였고, 첫번재 주소와 두번재 주소를 변경할 수 있습니다.
(메뉴 화면-Menu.java,activity_menu.xml 메뉴 설정-SettingPreferenceFragment.java)
