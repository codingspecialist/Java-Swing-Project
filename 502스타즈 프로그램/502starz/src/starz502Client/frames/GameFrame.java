package starz502Client.frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

import starz502Client.data.DataTypeParser;
import starz502Client.data.DataTypes;
import starz502Client.models.Bullet;
import starz502Client.models.GameModel;
import starz502Client.models.GameModelList;
import starz502Client.models.Player;

public class GameFrame extends JFrame implements Runnable{

   private BufferedReader reader;
   private PrintWriter writer;
   private Gson gson;
   
   //����̹���, ����̹��� ����-���� ���� [ init() ���� �ʱ�ȭ ]
   private BufferedImage mapImg;
   private int mapW;
   private int mapH;
   //ĳ����, ȸ�� �� ĳ���� �̹����� ������ BufferedImage ��ü [ init() ���� �ʱ�ȭ ] 
   private BufferedImage character;
//   private BufferedImage rotatedCharacter;
   private int rotatedCharacterW;
   private int rotatedCharacterH;
   
   //ĳ���� ȸ���� ���� �ʿ��� ��ü��
   private GraphicsConfiguration gc = getDefaultConfiguration();
   private BufferedImage result;
   private Graphics2D g;
   //ĳ���� ȸ�� �� (���� ĳ���� �̹��� ������ �������� �����ؾ� ��)
   private final int rotateAxisX = 123; // ���ι��� �̹��� : 120 , ���ι��� �̹��� = 123
   private final int rotateAxisY = 120; // ���ι��� �̹��� : 230 , ���ι��� �̹��� = 120
   //ȸ�� �� ĳ���͸� �׸� ���ο� BufferedImage ����, ���� (���� ĳ���� �̹��� ������ ����,���� ���� �ξ� Ŀ����)
   //�� ���� ratio�� ���� ���� rotatedCharacter�� ����, ���� ���̰� �� ����
   private final int newBuffImageW = 600;
   private final int newBuffImageH = 600;
   
   //�׸� �׸� �г��� ����, ���� ���� [ ������ â(?) ũ�⿡ ���� �ٸ� ]
   private final int panelW = 1094;
   private final int panelH = 772;
   //ĳ���� �׸��� ���� �ʿ��� ����
   private String username;
   
   private int charX=1390, charY=50;
   private int mouseX, mouseY;
   double angle = 0;
   
   //�Ѿ� ����
   private BufferedImage bulletImg;
//   private BufferedImage rotatedBullet;
   private int rotatedBulletW;
   private int rotatedBulletH;
   
   private final int bulletRotateAxisX = 50; 
   private final int bulletRotateAxisY = 33; 

   private final int bulletNewBuffImageW = 200;
   private final int bulletNewBuffImageH = 200;
   
   private final int bulletSpeed = 30;
   
   private int bulletOffsetX;
   private int bulletOffsetY;
   private final int bulletRatio = 2;
   
   //������ ���� GameModel
   private Player player;
   private Vector<Bullet> sendBulletList;
   private Vector<Bullet> receiveBulletList;
   private int receiveCurHp;
   private GameModel sendGameModel;
   
   private GameModelList receiveGameModelList;
   private StringBuilder jsonData;
   
   //ĳ���� �׸� ��ġ ������ ���� ���� [ init() ���� �ʱ�ȭ ]
   private int offsetX;
   private int offsetY;
   //ĳ���� ũ�� ������ ���� ����
   private final int ratio = 4;
      
   //ĳ���͸� �����̱� ���� �ʿ��� ����
   private final int characterSpeed = 3;
   boolean rightPressed=false, leftPressed=false, upPressed=false, downPressed=false;
   
   //��-ĳ���� �浹������ ���� (������ �ʴ�) ����̹��� [ init() ���� �ʱ�ȭ ]
   private BufferedImage mapCharacterCollision;
   private BufferedImage mapBulletCollision;
   
   
   public GameFrame(BufferedReader r, PrintWriter w, String username) {
      this.username = username;
      this.reader = r;
      this.writer = w;
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 1100, 800);
      setLocationRelativeTo(null);
      setResizable(false);   
      
      MyPanel myPanel = new MyPanel();
      myPanel.setFocusable(true);
      myPanel.setLayout(null);
      setContentPane(myPanel);
      
      init();

      //�� ĳ���� ��ǥ �����ϴ� �޼��� 17ms ���� ȣ��
      ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
      service.scheduleAtFixedRate(this, 0, 17, TimeUnit.MILLISECONDS);

      setVisible(true);
      
      //�����κ��� GameModelList �ް� repaintȣ�� �ϴ� ������ ����
      Thread t = new Thread(myPanel);
      t.start();
   }
   
   //�̹��� ���� �ҷ��ͼ� ������ �ֱ� + �ʱⰪ ����
   private void init() {
      try {
         mapImg = ImageIO.read(new File("images/game/map.png"));
         mapW = mapImg.getWidth();
         mapH = mapImg.getHeight();
         
         character = ImageIO.read(new File("images/game/character.png"));
//         rotatedCharacter = rotate(character, angle);
         
         rotatedCharacterW = newBuffImageW/ratio;
         rotatedCharacterH = newBuffImageH/ratio;
         
         offsetX = ( newBuffImageW/2 - ( character.getWidth()/2 - rotateAxisX ) )/ratio;
         offsetY = ( newBuffImageH/2 - ( character.getHeight()/2 - rotateAxisY ) )/ratio;
         
         
         bulletImg = ImageIO.read(new File("images/game/bullet.png"));
//         rotatedBullet = rotateBullet(bulletImg, angle);
         
         rotatedBulletW = bulletNewBuffImageW/bulletRatio;
         rotatedBulletH = bulletNewBuffImageH/bulletRatio;
         
         bulletOffsetX = ( bulletNewBuffImageW/2 - ( bulletImg.getWidth()/2 - bulletRotateAxisX ) )/bulletRatio;
         bulletOffsetY = ( bulletNewBuffImageH/2 - ( bulletImg.getHeight()/2 - bulletRotateAxisY ) )/bulletRatio;
         
         mapCharacterCollision = ImageIO.read(new File("images/game/mapCharacterCollision.png"));
         mapBulletCollision = ImageIO.read(new File("images/game/mapBulletCollision2.png"));
         
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      gson = new Gson();
      
      player = new Player(username, charX, charY, angle, 100);
      sendBulletList = new Vector<Bullet>();
      receiveBulletList = new Vector<Bullet>();
      
      sendGameModel = new GameModel();
      sendGameModel.setPlayer(player);
   }
   
   //�� ĳ���� ��ǥ �����ϴ� �޼���
   @Override
   public void run() {
      if (rightPressed) {
         if( mapCharacterCollision.getRGB(charX+characterSpeed, charY) >= -1 ) {
            charX += characterSpeed;               
         }
      }
      if (leftPressed) {
         if( mapCharacterCollision.getRGB(charX-characterSpeed, charY) >= -1 ) {
            charX -= characterSpeed;
         }
      }
      if (upPressed) {
         if( mapCharacterCollision.getRGB(charX, charY-characterSpeed) >= -1 ) {
            charY -= characterSpeed;
         }
      }
      if (downPressed) {
         if( mapCharacterCollision.getRGB(charX, charY+characterSpeed) >= -1 ) {
            charY += characterSpeed;
         }
      }
      setAngle(charX, charY, mouseX, mouseY);
      
      player.setX(charX);
      player.setY(charY);
      player.setAngle(angle);
   }
      
   class MyPanel extends JPanel implements Runnable{
      
      public MyPanel() {
         addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
               if (charX<panelW/2) {
                  mouseX = e.getX();
               }else if(charX>=panelW/2 && charX <= (mapW-panelW/2)){
                  mouseX = e.getX() + charX - panelW/2;
               }else {
                  mouseX = e.getX() + mapW - panelW;
               }
               
               if (charY < panelH/2) {
                  mouseY = e.getY();
               }else if(charY>=panelH/2 && charY <= (mapH-panelH/2)){
                  mouseY = e.getY() + charY - panelH/2;
               }else {
                  mouseY = e.getY() + mapH - panelH;
               }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
               if (charX<panelW/2) {
                  mouseX = e.getX();
               }else if(charX>=panelW/2 && charX <= (mapW-panelW/2)){
                  mouseX = e.getX() + charX - panelW/2;
               }else {
                  mouseX = e.getX() + mapW - panelW;
               }
               
               if (charY < panelH/2) {
                  mouseY = e.getY();
               }else if(charY>=panelH/2 && charY <= (mapH-panelH/2)){
                  mouseY = e.getY() + charY - panelH/2;
               }else {
                  mouseY = e.getY() + mapH - panelH;
               }
            }
            
         });
         
         addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               if(e.getButton() == MouseEvent.BUTTON1   ) {
                  if( receiveBulletList.size() + sendBulletList.size() < 5 ) {
                     Bullet bullet = new Bullet(username, charX + 50*Math.cos(angle), charY + 50*Math.sin(angle), angle, 20);
                     sendBulletList.add(bullet);
                  }
               }
            }
         });
         
         addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               switch(e.getKeyCode()){
                  case KeyEvent.VK_D:
                     rightPressed = true;
                     break;
                  case KeyEvent.VK_A:
                     leftPressed = true;
                     break;
                  case KeyEvent.VK_W:
                     upPressed = true;
                     break;
                  case KeyEvent.VK_S:
                     downPressed = true;
                     break;
               }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
               switch(e.getKeyCode()){
                  case KeyEvent.VK_D:
                     rightPressed = false;
                     break;
                  case KeyEvent.VK_A:
                     leftPressed = false;
                     break;
                  case KeyEvent.VK_W:
                     upPressed = false;
                     break;
                  case KeyEvent.VK_S:
                     downPressed = false;
                     break;
               }
            }
            
         });
         
      }
      
      //�����κ��� GameModelList �ް� repaint()ȣ�� �ϴ� ������
      @Override
      public void run() {

         jsonData = new StringBuilder();
         char dataType;
         try {
            while( jsonData.append(reader.readLine()) != null) {
//               System.out.println("From Server >> " + jsonData.toString());
               
               dataType = jsonData.toString().charAt(12);
               if(dataType=='i') {
                  receiveGameModelList = gson.fromJson(jsonData.toString(), GameModelList.class);
                  
                  for (GameModel gm : receiveGameModelList.getGameModelList()) {
                     if( gm.getPlayer().getStz_username().equals(username) ) {
                        receiveCurHp = gm.getPlayer().getCurHp();
                        receiveBulletList = gm.getBulletList();
                        break;
                     }
                  }
                  
                  // �� ĳ���� ü�� 0 ���ϸ� GameResultFrame���� �Ѿ��
                  if(receiveCurHp <= 0) {
                     new GameResultFrame(reader, writer, username);
                     dispose();
                     break;
                  }
                  
                  repaint(); // paintComponent()�޼��带 ������� ȣ����.

                  // ������ ���� �ӽ� BulletList �����ϰ� �������� ���� �� �״�� ����(��������)
                  Vector<Bullet> tempBulletList = new Vector<Bullet>();
                  for (int i = 0; i < receiveBulletList.size(); i++) {
                     Bullet bullet = new Bullet();
                     bullet.setStz_username( receiveBulletList.get(i).getStz_username() );
                     bullet.setX( receiveBulletList.get(i).getX() );
                     bullet.setY( receiveBulletList.get(i).getY() );
                     bullet.setAngle( receiveBulletList.get(i).getAngle() );
                     bullet.setDmg( receiveBulletList.get(i).getDmg() );
                     
                     tempBulletList.add(bullet);
                  }
                  
                  // �������� ���� receiveBulletList + ���� ������ �ִ� sendBulletList
                  for (Bullet bullet : sendBulletList) {
                     tempBulletList.add(bullet);
                  }
                  sendBulletList.clear();
                  
                  // �ΰ� ��ģ bulletList �߿��� ���� �������� �����ϰ� ������ ����
                  Vector<Integer> removeBullet = new Vector<Integer>();
                  int i = 0;
                  for (Bullet b : tempBulletList) {
                     //�Ѿ� �������� 0�̻��ΰ�?
                     if(b.getDmg() > 0) {
                        //�Ѿ��� ���� ��ġ(dx, dy)
                        double dx = b.getX() + bulletSpeed * Math.cos(b.getAngle());
                        double dy = b.getY() + bulletSpeed * Math.sin(b.getAngle());
                        
                        // �Ѿ� ���� ��ġ�� �� �����ȿ� �����ϴ°�?
                        if( dx>0 && dx<mapBulletCollision.getWidth() && dy>0 && dy<mapBulletCollision.getHeight()) {
                           if( mapBulletCollision.getRGB( (int)dx, (int)dy ) >= -1 ) {
                              b.setX(dx);
                              b.setY(dy);
                           }else {
                              removeBullet.add(i);
                           }
                        }else {
                           removeBullet.add(i);
                        }
                        i++;
                     }else {
                        removeBullet.add(i);
                     }
                  }
                  
                  int m = 0;
                  for (int k = 0; k < removeBullet.size(); k++) {
                     int temp = removeBullet.get(k) - m;
                     tempBulletList.remove(temp);
                     m++;
                  }
                  
                  sendGameModel.setBulletList(tempBulletList);
                  
                  writer.println(gson.toJson(sendGameModel));
                  writer.flush();
                  
                  
               }
               
               jsonData.setLength(0);
               
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      
      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         
         if (charX<panelW/2) {
            if (charY < panelH/2) {
               g.drawImage(mapImg, 0, 0, panelW, panelH, 0, 0, 0+panelW, 0+panelH, null);
//               g.drawImage(rotatedCharacter, charX-offsetX, charY-offsetY, rotatedCharacterW,rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage( rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, gm.getPlayer().getY()-offsetY, rotatedCharacterW,rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(gm.getPlayer().getX()-25, gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43);
                  }else {
                     g.drawImage( rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, gm.getPlayer().getY()-offsetY, rotatedCharacterW,rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25, gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43);
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);                        
                     }
                  }
               } 
               
            }else if(charY>=panelH/2 && charY <= (mapH-panelH/2)){
               g.drawImage(mapImg, 0, 0, panelW, panelH, 0, charY-panelH/2, 0+panelW, charY-panelH/2+panelH, null);
//               g.drawImage(rotatedCharacter, charX-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(gm.getPlayer().getX()-25, panelH/2-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, panelH/2-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, gm.getPlayer().getY()-offsetY-(charY-panelH/2), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25, gm.getPlayer().getY()-40-(charY-panelH/2), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43-(charY-panelH/2));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }else {
               g.drawImage(mapImg, 0, 0, panelW, panelH, 0, mapH-panelH, 0+panelW, mapH-panelH+panelH, null);
//               g.drawImage(rotatedCharacter, charX-offsetX, panelH-(mapH-charY)-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, panelH-(mapH-gm.getPlayer().getY())-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(gm.getPlayer().getX()-25, panelH-(mapH-gm.getPlayer().getY())-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, panelH-(mapH-gm.getPlayer().getY())-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX, gm.getPlayer().getY()-offsetY-(mapH-panelH), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25, gm.getPlayer().getY()-40-(mapH-panelH), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43-(mapH-panelH));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX, (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }

            }
         }else if(charX>=panelW/2 && charX <= (mapW-panelW/2)){
            if (charY < panelH/2) {
               g.drawImage(mapImg, 0, 0, panelW, panelH, charX-panelW/2, 0, charX-panelW/2+panelW, 0+panelH, null);
//               g.drawImage(rotatedCharacter, panelW/2-offsetX, charY-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW/2-offsetX, gm.getPlayer().getY()-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW/2-25, gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW/2 - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(charX-panelW/2), gm.getPlayer().getY()-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(charX-panelW/2), gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(charX-panelW/2), gm.getPlayer().getY()-43);
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }else if(charY>=panelH/2 && charY <= (mapH-panelH/2)){
               g.drawImage(mapImg, 0, 0, panelW, panelH, charX-panelW/2, charY-panelH/2, charX-panelW/2+panelW, charY-panelH/2+panelH, null);
//               g.drawImage(rotatedCharacter, panelW/2-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW/2-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW/2-25, panelH/2-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW/2 - gm.getPlayer().getStz_username().length()*4, panelH/2-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(charX-panelW/2), gm.getPlayer().getY()-offsetY-(charY-panelH/2), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(charX-panelW/2), gm.getPlayer().getY()-40-(charY-panelH/2), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(charX-panelW/2), gm.getPlayer().getY()-43-(charY-panelH/2));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }else {
               g.drawImage(mapImg, 0, 0, panelW, panelH, charX-panelW/2, mapH-panelH, charX-panelW/2+panelW, mapH-panelH+panelH, null);
//               g.drawImage(rotatedCharacter, panelW/2-offsetX, panelH-(mapH-charY)-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW/2-offsetX, panelH-(mapH-gm.getPlayer().getY())-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW/2-25, panelH-(mapH-gm.getPlayer().getY())-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW/2 - gm.getPlayer().getStz_username().length()*4, panelH-(mapH-gm.getPlayer().getY())-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(charX-panelW/2), gm.getPlayer().getY()-offsetY-(mapH-panelH), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(charX-panelW/2), gm.getPlayer().getY()-40-(mapH-panelH), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(charX-panelW/2), gm.getPlayer().getY()-43-(mapH-panelH));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(charX-panelW/2), (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }
         }else {
            if (charY < panelH/2) {
               g.drawImage(mapImg, 0, 0, panelW, panelH, mapW-panelW, 0, mapW-panelW+panelW, 0+panelH, null);
//               g.drawImage(rotatedCharacter, panelW-(mapW-charX)-offsetX, charY-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW-(mapW-gm.getPlayer().getX())-offsetX, gm.getPlayer().getY()-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW-(mapW-gm.getPlayer().getX())-25, gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW-(mapW-gm.getPlayer().getX()) - gm.getPlayer().getStz_username().length()*4, gm.getPlayer().getY()-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(mapW-panelW), gm.getPlayer().getY()-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(mapW-panelW), gm.getPlayer().getY()-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(mapW-panelW), gm.getPlayer().getY()-43);
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY, rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }else if(charY>=panelH/2 && charY <= (mapH-panelH/2)){
               g.drawImage(mapImg, 0, 0, panelW, panelH, mapW-panelW, charY-panelH/2, mapW-panelW+panelW, charY-panelH/2+panelH, null);
//               g.drawImage(rotatedCharacter, panelW-(mapW-charX)-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW-(mapW-gm.getPlayer().getX())-offsetX, panelH/2-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW-(mapW-gm.getPlayer().getX())-25, panelH/2-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW-(mapW-gm.getPlayer().getX()) - gm.getPlayer().getStz_username().length()*4, panelH/2-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(mapW-panelW), gm.getPlayer().getY()-offsetY-(charY-panelH/2), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(mapW-panelW), gm.getPlayer().getY()-40-(charY-panelH/2), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(mapW-panelW), gm.getPlayer().getY()-43-(charY-panelH/2));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY-(charY-panelH/2), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
               
            }else {
               g.drawImage(mapImg, 0, 0, panelW, panelH, mapW-panelW, mapH-panelH, mapW-panelW+panelW, mapH-panelH+panelH, null);
//               g.drawImage(rotatedCharacter, panelW-(mapW-charX)-offsetX, panelH-(mapH-charY)-offsetY, rotatedCharacterW, rotatedCharacterH, null);
//               
//               for (Bullet b : bulletList) {
//                  g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);
//               }
               
               for (GameModel gm : receiveGameModelList.getGameModelList()) {
                  if(gm.getPlayer().getStz_username().equals(username)) {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), panelW-(mapW-gm.getPlayer().getX())-offsetX, panelH-(mapH-gm.getPlayer().getY())-offsetY, rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(181,230,29));
                     g.fillRect(panelW-(mapW-gm.getPlayer().getX())-25, panelH-(mapH-gm.getPlayer().getY())-40, 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), panelW-(mapW-gm.getPlayer().getX()) - gm.getPlayer().getStz_username().length()*4, panelH-(mapH-gm.getPlayer().getY())-43);
                  }else {
                     g.drawImage(rotate(character, gm.getPlayer().getAngle()), gm.getPlayer().getX()-offsetX-(mapW-panelW), gm.getPlayer().getY()-offsetY-(mapH-panelH), rotatedCharacterW, rotatedCharacterH, null);
                     g.setColor(new Color(237,28,36));
                     g.fillRect(gm.getPlayer().getX()-25-(mapW-panelW), gm.getPlayer().getY()-40-(mapH-panelH), 50*gm.getPlayer().getCurHp()/100, 10);
                     g.setColor(Color.black);
                     g.drawString(gm.getPlayer().getStz_username(), gm.getPlayer().getX() - gm.getPlayer().getStz_username().length()*4-(mapW-panelW), gm.getPlayer().getY()-43-(mapH-panelH));
                  }
                  for (Bullet b : gm.getBulletList()) {
                     if (b.getDmg() > 0) {
                        g.drawImage(rotateBullet(bulletImg, b.getAngle()), (int)b.getX()-bulletOffsetX-(mapW-panelW), (int)b.getY()-bulletOffsetY-(mapH-panelH), rotatedBulletW, rotatedBulletH, null);   
                     }
                  }
               }
            }
         }

      }

      
   }
   
   
   private void setAngle(int charX, int charY, int mouseX, int mouseY) {
      int triangleBottom = mouseX - charX;
      int triangleHeight = mouseY - charY;
      
      if (triangleBottom > 0) {
         angle = Math.atan( (double)triangleHeight / triangleBottom ); //+ Math.PI/2;                  
      }else if(triangleBottom < 0) {
         angle = Math.atan( (double)triangleHeight / triangleBottom ) + Math.PI;// + Math.PI/2;
      }else {
         if(triangleHeight > 0) {
            angle = Math.PI/2;// + Math.PI/2;
         }else if(triangleHeight < 0 ) {
            angle = -Math.PI/2;// + Math.PI/2;
         }else {
            angle = 0;// + Math.PI/2;
         }
      }
   }
   
   private BufferedImage rotate(BufferedImage image, double angle) {
       result = gc.createCompatibleImage(newBuffImageW, newBuffImageH, Transparency.TRANSLUCENT);
       g = result.createGraphics();       
       
       g.translate( (newBuffImageW - image.getWidth())/2, (newBuffImageH - image.getHeight())/2 );
       g.rotate(angle, rotateAxisX, rotateAxisY);
       
       g.drawRenderedImage(image, null);
       g.dispose();
       return result;
   }
   
   private BufferedImage rotateBullet(BufferedImage image, double angle) {
       result = gc.createCompatibleImage(bulletNewBuffImageW, bulletNewBuffImageH, Transparency.TRANSLUCENT);
       g = result.createGraphics();       
       
       g.translate( (bulletNewBuffImageW - image.getWidth())/2, (bulletNewBuffImageH - image.getHeight())/2 );
       g.rotate(angle, bulletRotateAxisX, bulletRotateAxisY);
       
       g.drawRenderedImage(image, null);
       g.dispose();
       return result;
   }

   private GraphicsConfiguration getDefaultConfiguration() {
       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice gd = ge.getDefaultScreenDevice();
       return gd.getDefaultConfiguration();
   }
   
   
   
//   public static void main(String[] args) {
//      new GameFrame(null, null, null);
//   }
   
}