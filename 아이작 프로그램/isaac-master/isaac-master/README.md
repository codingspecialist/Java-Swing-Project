# 간단한 아이작 게임 만들기

![isaac](https://github.com/LEEJAECHEOL/isaac/blob/master/document/isaac.png)
> 반갑습니다. 2주동안 아이작 게임을 한 맵을 통해 게임을 제작했습니다.  
> 제작자 : 이재철, 동태완
> PPT Link : [PPT DOWNLOAD](https://github.com/LEEJAECHEOL/isaac/LEEJAECHEOL/isaac/raw/master/document/%EC%95%84%EC%9D%B4%EC%9E%91%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8.pptx)

## 시연 영상
[![Video Label](https://github.com/LEEJAECHEOL/isaac/blob/master/document/playImg.png)](https://www.youtube.com/watch?v=qoJSMnpbHLQ)


## 기능
#### 1. 플레이어   
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/isaac/isaac.png) 
- 체력 20   
- 이동   
  - 상, 하, 좌, 우 이동
- 공격   
  - space키를 이용하여 공격(불릿을 날림)
- 아이템 줍기   
  - 아이템 근처로 이동하면 아이템 획득 모션동시에 아이템 획득
- 아이템 사용   
  - 사용 가능한 아이템은 폭탄이다. 폭탄은 b키를 누르면 사용이 가능하다.

#### 2. 몬스터   
* 웜   
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/monster/worm.png) 
  - 체력 20
  - 플레이어를 따라다님 (상, 하, 좌, 우 이동)
  - 플레이어가 근처에 있으면 공격(플레이어 생명력 -1)
* 스톤   
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/monster/stone.png) 
  - 체력 20
  - 이동X
  - 3초마다 불릿을 날림 (플레이어가 맞으면 생명력 -1)
#### 3. 구조물 
* 바위
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/structure/rock.png) 
  - 플레이어 이동 방해 구조물
  - 폭탄을 통해 부서짐.
* 스파이크
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/structure/spike.png)  
  - 플레이어가 바위와 다르게 밟으면서 이동가능
  - 플레이어가 밟으면 생명력 -0.5
* 문
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/structure/door.png)
  - 맵에 몬스터가 없으면 문이 열림
#### 4. 아이템
* 폭탄
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/item/bomb.png)
  - 바위를 부술 때 사용
  - b키를 눌려서 사용
* 열쇠
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/item/key.png)
  - 상자를 열 때 필요. 구현X 줍기만 가능
* 하트
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/item/recoveryLife.png)
  - 플레이어 생명력 1 회복
* 아이템 줍기   
#### 5. 불릿
* 아이작 불릿
![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/bullet/isaacBullet.png)
* 적 불릿
![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/isaac/images/bullet/enemyBullet.png)

## UML
> ![rock](https://github.com/LEEJAECHEOL/isaac/blob/master/document/uml.png)
