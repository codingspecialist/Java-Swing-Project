package musicMaker;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dbconn.FileDB;
import model.MusicFile;

public class NewFileFrame extends JFrame {

   private JPanel contentPane;
   private JTextField textField;
   private int state;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               NewFileFrame frame = new NewFileFrame();
               
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public NewFileFrame() {
	// TODO Auto-generated constructor stub
}
   public NewFileFrame(String username, DefaultTableModel t) {
      setTitle("New File");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 260, 153);
      setResizable(false);//크기고정
      setLocationRelativeTo(null);//정중앙에 실행된다.
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JButton btnNewButton = new JButton("New File");//save버튼
      
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 MusicFile musicfile = new MusicFile();
       	  //New File 버튼저장 버튼 핸들러
            FileDB fileDB = FileDB.getInstance();
           String  filename = textField.getText();
         int result =  fileDB.newFile(filename, username);
         if(result == 1) {
        	 JOptionPane.showMessageDialog(null, "Created File");
        	 state = 1;
        	 
     		Vector<Object> t123 = new Vector<Object>();
    		t123.addElement(filename);
    		t.addRow(t123);
    		dispose();
         }
           
         }
      });
      btnNewButton.setBounds(171, 72, 70, 29);
      contentPane.add(btnNewButton);
      
      textField = new JTextField();
      textField.setBounds(82, 30, 161, 29);
      contentPane.add(textField);
      textField.setColumns(10);
      
      JLabel lblNewLabel = new JLabel("File Name :");
      lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
      lblNewLabel.setBounds(12, 32, 70, 24);
      contentPane.add(lblNewLabel);
   
      setVisible(true);
   }
   
   public int getState() {
	   return state;
   }
}