package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class SocketServer {

   private ServerSocket serverSocket;
   private Vector<SocketThread> vc; // Ŭ���̾�Ʈ ������ ������ �ִ� ����Ʈ

   public SocketServer() {

      try {
         serverSocket = new ServerSocket(5000);
         vc = new Vector<>();
         while (true) {
            Socket socket = serverSocket.accept(); // ���� ��� ��!!
            System.out.println("���ӿϷ�");
            SocketThread st = new SocketThread(socket);
            st.start();
            vc.add(st); // Vector(����ȭ) = ArrayList(�񵿱�ȭ)
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   class SocketThread extends Thread {
      private Socket socket;
      private BufferedReader reader;
      private PrintWriter writer;
      private String username;
      public SocketThread(Socket socket) {
         this.socket = socket;
      }

      @Override
      public void run() {
         try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String line;
           
            
            username = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
               for (SocketThread st : vc) {
                  st.writer.println(username + ":" +line);
               }
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

   }

   public static void main(String[] args) {
      new SocketServer();
   }

}