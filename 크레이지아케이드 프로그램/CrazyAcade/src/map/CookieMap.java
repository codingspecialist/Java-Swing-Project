package map;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class CookieMap extends JFrame {
   private JLabel contentPane;
   private Vector<JLabel> item = new Vector<JLabel>();
   private ArrayList<JLabel> itemlist = new ArrayList<JLabel>();
   private String username;

   ImageIcon item3;
   JLabel itemLabel;
   int myX = 500;
   int myY = 500;

   int enemyX = 100;
   int enemyY = 100;
   String move;
   String shape;
   int x;
   int y;
   int enemyBx,enemyBy;
   int bx, by;
   boolean keyU = false;
   boolean keyD = false;
   boolean keyL = false;
   boolean keyR = false;
   boolean die = false;
   boolean check=true;
   private int speed = 80;
   private ImageIcon[] item2 = { new ImageIcon("images/speed.png"),null,null, null };
   Random random = new Random();

   JLabel bazzi = new JLabel(new ImageIcon("images/bazzi_front.png"));
   JLabel woonie = new JLabel(new ImageIcon("images/woonie_front.png"));
   GameThread gt;

   public void DropBomb() {
      this.x = myX;
      this.y = myY;

      Runnable runnable = new Runnable() {

         @Override
         public void run() {
            ImageIcon bubble = new ImageIcon("images/bomb.png");
            JLabel bu = new JLabel(bubble);
            x /= 40;
            y /= 40;
            x *= 40;
            y *= 40;

            bu.setSize(40, 40);
            bu.setLocation(x + 16, y + 5);
            bu.setVisible(true);
            contentPane.add(bu);

            bx = x + 16;
            by = y + 5;
            try {
               Thread.sleep(2000);
//               bu.setVisible(false);

               ImageIcon bup = new ImageIcon("images/bup.png");
               JLabel bupp = new JLabel(bup);
               ImageIcon bright = new ImageIcon("images/bright.png");
               JLabel br = new JLabel(bright);
               ImageIcon bdown = new ImageIcon("images/bdown.png");
               JLabel bd = new JLabel(bdown);
               ImageIcon bleft = new ImageIcon("images/bleft.png");
               JLabel bl = new JLabel(bleft);

               bupp.setSize(40, 40);
               bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 40);
               bupp.setVisible(true);
               contentPane.add(bupp);
               br.setSize(40, 40);
               br.setLocation(bu.getLocation().x + 40, bu.getLocation().y);
               br.setVisible(true);
               contentPane.add(br);
               bd.setSize(40, 40);
               bd.setLocation(bu.getLocation().x, bu.getLocation().y + 40);
               bd.setVisible(true);
               contentPane.add(bd);
               bl.setSize(40, 40);
               bl.setLocation(bu.getLocation().x - 40, bu.getLocation().y);
               bl.setVisible(true);
               contentPane.add(bl);
               bu.setIcon(new ImageIcon("images/bcenter.png"));
               Thread.sleep(1000);
               bupp.setVisible(false);
               br.setVisible(false);
               bd.setVisible(false);
               bl.setVisible(false);
               bu.setVisible(false);

               checkLocation();
               bx = bu.getLocation().x;
               by = bu.getLocation().y;
            } catch (InterruptedException e) {
               e.printStackTrace();
            }

         }
      };
      new Thread(runnable).start();
   }
   
   public void Die() {
      if(die==true) {
         
         JOptionPane.showMessageDialog(null, "게임 끝!");
         die=false;
         dispose();
         
      }
   }
   
   Runnable gameover = new Runnable() {

      @Override
      public void run() {
         while (true) {
            Die();
            try {
               Thread.sleep(20);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   };
   public void DropBomb(int imgX, int imgY) {
      this.x = imgX;
      this.y = imgY;

      Runnable runnable = new Runnable() {

         @Override
         public void run() {
            ImageIcon bubble = new ImageIcon("images/bomb.png");
            JLabel bu = new JLabel(bubble);
            x /= 40;
            y /= 40;
            x *= 40;
            y *= 40;

            bu.setSize(40, 40);
            bu.setLocation(x + 16, y + 5);
            bu.setVisible(true);
            contentPane.add(bu);
            enemyBx = x + 16;
            enemyBy = y + 5;
            try {
               Thread.sleep(2000);
               bu.setVisible(false);

               ImageIcon bcenter = new ImageIcon("images/bcenter.png");
               JLabel bc = new JLabel(bcenter);
               ImageIcon bup = new ImageIcon("images/bup.png");
               JLabel bupp = new JLabel(bup);
               ImageIcon bright = new ImageIcon("images/bright.png");
               JLabel br = new JLabel(bright);
               ImageIcon bdown = new ImageIcon("images/bdown.png");
               JLabel bd = new JLabel(bdown);
               ImageIcon bleft = new ImageIcon("images/bleft.png");
               JLabel bl = new JLabel(bleft);

               bc.setSize(40, 40);
               bc.setLocation(enemyBx, enemyBy);
               bc.setVisible(true);
               contentPane.add(bc);
               bupp.setSize(40, 40);
               bupp.setLocation(enemyBx, enemyBy - 40);
               bupp.setVisible(true);
               contentPane.add(bupp);
               br.setSize(40, 40);
               br.setLocation(enemyBx + 40, enemyBy);
               br.setVisible(true);
               contentPane.add(br);
               bd.setSize(40, 40);
               bd.setLocation(enemyBx, enemyBy + 40);
               bd.setVisible(true);
               contentPane.add(bd);
               bl.setSize(40, 40);
               bl.setLocation(enemyBx - 40, enemyBy);
               bl.setVisible(true);
               contentPane.add(bl);
               Thread.sleep(1000);
               bc.setVisible(false);
               bupp.setVisible(false);
               br.setVisible(false);
               bd.setVisible(false);
               bl.setVisible(false);
               
               checkLocation();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      };
      new Thread(runnable).start();
   }

   public void MyLocation() {

      for (int i = 0; i < item.size(); i++) {
         if ((myX >= item.get(i).getX() - 40 && myX <= item.get(i).getX())
               && (myY < item.get(i).getY()+5 && myY > item.get(i).getY() - 35)) {
            keyR = false;
            continue;

         } else if ((myX >= item.get(i).getX() && myX <= item.get(i).getX() + 40)
               && (myY < item.get(i).getY()+5 && myY > item.get(i).getY() - 35)) {
            keyL = false;
            continue;
         } else if ((myX > item.get(i).getX()-10 && myX < item.get(i).getX() + 30)
               && (myY > item.get(i).getY() && myY < item.get(i).getY() + 40)) {
            keyU = false;
            continue;
         } else if ((myX >= item.get(i).getX()-10 && myX <= item.get(i).getX() + 30)
               && (myY+46 > item.get(i).getY()-10&& myY+46 < item.get(i).getY()+40)) {
            keyD = false;
            continue;
         }

      }
   }

   Runnable runable = new Runnable() {

      @Override
      public void run() {
         while (true) {
            MyLocation();
         }
      }
   };

   public void checkLocation() {
      // 풍선위치

//      System.out.println("풍선 위치 bx : " + bx);
//      System.out.println("풍선 위치 by : " + by);
//      System.out.println("x : " + x + "y:" + y);
//      System.out.println();
      if((myX>bx-65 && myX<bx+60) &&(myY>by-40 &&myY<by+20)) {
         die = true;
      }
      else if((myX>bx-10 && myX<bx+50) &&(myY>by-80 &&myY<by+45)) {
         die = true;
      }
      // 풍선위치를 기준으로 동서남북
      for (int i = 0; i < item.size(); i++) {

         // 동서남북으로 찾아야 함.
         // 496.525
         y -= 40;
         if ((bx + 40 >= item.get(i).getX() && bx + 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by + 40 >= item.get(i).getY() && by + 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx - 40 >= item.get(i).getX() && bx - 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {

            item.get(i).setIcon(null);
            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by - 40 >= item.get(i).getY() && by - 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         }

         // item.get(i).setIcon(null); //23
      }

      
   }

   Runnable enemyBomb = new Runnable() {
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			enemyCheckLocation(enemyBx,enemyBy);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
};
   
   public void enemyCheckLocation(int enemyBx,int enemyBy) {
	      // 풍선위치

//	      System.out.println("풍선 위치 bx : " + bx);
//	      System.out.println("풍선 위치 by : " + by);
//	      System.out.println("x : " + x + "y:" + y);
//	      System.out.println();
	   	  
	      if((myX>enemyBx-65 && myX<enemyBx+60) &&(myY>enemyBy-40 &&myY<enemyBy+20)) {
	         die = true;
	      }
	      else if((myX>enemyBx-10 && myX<enemyBx+50) &&(myY>enemyBy-80 &&myY<enemyBy+45)) {
	         die = true;
	      }
	      // 풍선위치를 기준으로 동서남북
	      for (int i = 0; i < item.size(); i++) {

	         // 동서남북으로 찾아야 함.
	         // 496.525
	         y -= 40;
	         if ((enemyBx + 40 >= item.get(i).getX() && enemyBx + 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy + 40 >= item.get(i).getY() && enemyBy + 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx - 40 >= item.get(i).getX() && enemyBx - 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {

	            item.get(i).setIcon(null);
	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy - 40 >= item.get(i).getY() && enemyBy - 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         }

	         // item.get(i).setIcon(null); //23
	      }

	      
	   }
   
   public void ItemSpeed() {
      for (int i = 0; i < itemlist.size(); i++) {
         if ((myX >= itemlist.get(i).getX() -20 && myX <= itemlist.get(i).getX() + 10)
               && (myY >= itemlist.get(i).getY() - 10 && myY <= itemlist.get(i).getY() + 30)) {
            itemlist.get(i).setIcon(null);
            speed -= 5;
            if (speed < 40) {
               speed = 40;
            }
         }
      }
   }

   public void bazziCurrent(String imageLocation) {
      bazzi.setIcon(new ImageIcon(imageLocation));
   }

   public void woonieCurrent(String imageLocation) {
      woonie.setIcon(new ImageIcon(imageLocation));
   }

   private void firstLocation() {
      // 배찌
      contentPane.add(bazzi);
      bazzi.setSize(44, 56);
      bazzi.setLocation(myX, myY);
      // 우니
      contentPane.add(woonie);
      woonie.setSize(44, 56);
      woonie.setLocation(enemyX, enemyY);
   }

   public void keyProcess() {
      if (keyU == true) {
         bazziCurrent("images/bazzi_back.png");
         myY -= 10;
         bazzi.setLocation(myX, myY);
         move = "U";
         gt.send(username + ":" + "MOVE:" + move);
         if (myY < 0) {
            myY = 0;
         }
      }
      if (keyD == true) {
         bazziCurrent("images/bazzi_front.png");
         myY += 10;
         bazzi.setLocation(myX, myY);
         move = "D";
         gt.send(username + ":" + "MOVE:" + move);
         if (myY > 550) {
            myY = 550;
         }
      }
      if (keyL == true) {
         bazziCurrent("images/bazzi_left.png");
         myX -= 10;
         bazzi.setLocation(myX, myY);
         move = "L";
         gt.send(username + ":" + "MOVE:" + move);
         if (myX < 16) {
            myX = 16;
         }
      }
      if (keyR == true) {
         bazziCurrent("images/bazzi_right.png");
         myX += 10;
         bazzi.setLocation(myX, myY);
         move = "R";
         gt.send(username + ":" + "MOVE:" + move);
         if (myX > 580) {
            myX = 580;
         }
      }
   }

   Runnable runnable = new Runnable() {

      @Override
      public void run() {
         while (true) {
            keyProcess();
            ItemSpeed();
            
            repaint();
            try {
               Thread.sleep(speed);// 최고 이동속도는 40으로 설정
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   };

   public CookieMap(String username) {

      this.username = username;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 650, 650);
      setLocationRelativeTo(null);
      contentPane = new JLabel(new ImageIcon("Images/mapbg1.png"));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(null);
      setContentPane(contentPane);

      firstLocation();

      gt = new GameThread();
      new Thread(runnable).start();
      new Thread(runable).start();
      new Thread(gameover).start();
      
      gt.start();

      gt.send(username + ":" + "LOCATIONX:" + myX);
      gt.send(username + ":" + "LOCATIONY:" + myY);
      addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            // System.out.println(e.getKeyCode());
            switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
               keyR = true;
               break;
            case KeyEvent.VK_LEFT:
               keyL = true;
               break;
            case KeyEvent.VK_UP:
               keyU = true;
               break;
            case KeyEvent.VK_DOWN:
               keyD = true;
               break;
            case KeyEvent.VK_SPACE:
            	new Thread(one).start();
            }

         }
         
         Runnable one = new Runnable() {
             
             @Override
             public void run() {
                if(check) {
                   DropBomb();
                         gt.send(username + ":DROP:o");
                         check=false;
                         try {
                      Thread.sleep(3000);
                      check=true;
                   } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                   }
                }
                
             }
          };

         @Override
         public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
               keyR = false;
               break;
            case KeyEvent.VK_LEFT:
               keyL = false;
               break;
            case KeyEvent.VK_UP:
               keyU = false;
               break;
            case KeyEvent.VK_DOWN:
               keyD = false;
               break;
            }
         }
      });

      
      Cookie cookie = new Cookie(15);
      for (int i = 0; i < cookie.size; i++) {
         for (int j = 0; j < cookie.size; j++) {
            cookie.map[i][j] = "1";
            String block = cookie.map[i][j];
            if (i == 0 || j == 14 || i == 14 || j == 0) {
               JLabel cookie2 = new JLabel(new ImageIcon("images/cookie.png"));
               item.add(cookie2);
               this.add(cookie2);
               cookie2.setBounds(i * 40 + 15, j * 40, 45, 45);
            } else if (i + j == 8 || i + j == 20 || i == j + 6 || i == j - 6) {
               JLabel cookie3 = new JLabel(new ImageIcon("images/cookie2.png"));
               item.add(cookie3);
               this.add(cookie3);
               cookie3.setBounds(i * 40 + 15, j * 40, 45, 45);
            }

         }
      }
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });

   }

   class GameThread extends Thread {

      private Socket socket;
      private BufferedReader reader;
      private PrintWriter writer;

      public GameThread() {
         // 서버 연결
         try {
            socket = new Socket("localhost", 6000);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      @Override
      public void run() {
         try {
            String line;
            String firstline, secondline, thirdline;

            while ((line = reader.readLine()) != null) {

               // System.out.println(line);

               String[] firstindex = line.split(":");

               firstline = firstindex[0];
               secondline = firstindex[1];
               thirdline = firstindex[2];

               // System.out.println(firstline);
               // System.out.println(secondline);
               // System.out.println(thirdline);
               if (!(firstline.equals(username))) {
                  if (secondline.equals("LOCATIONX")) {
                     enemyX = Integer.parseInt(thirdline);
                     // System.out.println(Integer.parseInt(thirdline));

                  }
                  if (secondline.equals("LOCATIONY")) {
                     enemyY = Integer.parseInt(thirdline);
                     // System.out.println(Integer.parseInt(thirdline));

                  }

                  if (secondline.equals("MOVE")) {
                     if (thirdline.equals("R")) {
                        enemyX += 10;
                        woonieCurrent("images/woonie_right.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyX > 580) {
                           enemyX = 580;
                        }
                     } else if (thirdline.equals("L")) {
                        enemyX -= 10;
                        woonieCurrent("images/woonie_left.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyX < 16) {
                           enemyX = 16;
                        }
                     } else if (thirdline.equals("U")) {
                        enemyY -= 10;
                        woonieCurrent("images/woonie_back.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyY < 0) {
                           enemyY = 0;
                        }
                     } else if (thirdline.equals("D")) {
                        enemyY += 10;
                        woonieCurrent("images/woonie_front.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyY > 550) {
                           enemyY = 550;
                        }
                     }

                  }
                  if (secondline.equals("DROP")) {
                     DropBomb(enemyX, enemyY);
                     new Thread(enemyBomb).start();
					
                  }

               }

               repaint();
               // System.out.println(line);
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      // UI쓰레드 사용
      public void send(String msg) {
         writer.println(msg);
      }
   }
}