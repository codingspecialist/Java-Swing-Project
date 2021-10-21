package gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CountDown extends JFrame {
 private JLabel label;

 class MyThread extends Thread {
  public void run() {
   for (int i = 3; i >= 1; i--) {
    try {
     Thread.sleep(1000);
    } catch (InterruptedException e) {
     e.printStackTrace();
    }
    label.setText(i + "");
   }
  }
 }

 public CountDown() {
  setTitle("카운트다운");
  setSize(250, 175);
  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  JPanel panel = new JPanel();
  label = new JLabel("Start");
  label.setFont(new Font("serif", Font.BOLD, 100));
  panel.add(label);
  add(panel);
  (new MyThread()).start();
  setVisible(true);
 }

}