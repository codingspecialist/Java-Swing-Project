package structure;

import javax.swing.JFrame;
import SpriteSheet.SpriteSheet;
import lombok.Data;

@Data

//문 클래스

public class Door {
   // 그려넣을 Frame
   private JFrame app;

   // 강사님이 적으라 했던 TAG
   private final static String TAG = "Door : ";

   // 이미지를 넣을 Frame
   private SpriteSheet ssDoor;

   // 그려지는 객체를 구분할 구분자
   private String gubun;

   // 도어가 그려질 위치 x, y 좌표
   private int xDoor, yDoor;

   // 도어 생성자
   public Door(JFrame app, SpriteSheet ssDoor, String gubun, int xDoor, int yDoor) {
      // this들은 Isaac 앱에서 생성할 때 받아올 값들.
      this.app = app;
      this.gubun = gubun;
      this.ssDoor = ssDoor;
      this.xDoor = xDoor;
      this.yDoor = yDoor;
      
      // 생성 잘되었는지 체크하기위한 프린트문
      System.out.println(TAG + gubun + " 문 생성"); 
      drawStructure(); // 생성하면서 그려주기
   }

   // 문 그리기 메서드
   public void drawStructure() {
      getSsDoor().drawObject(getXDoor(), getYDoor()); // Sprite함수가 가진 drawObject함수를 호출 (인자는 x좌표,y좌표)
      getApp().add(getSsDoor()); // App에다가 에드해서 화면에 최종 출력.
   }
}