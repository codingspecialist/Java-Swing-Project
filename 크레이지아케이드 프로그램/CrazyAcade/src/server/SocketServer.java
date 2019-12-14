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
   private Vector<SocketThread> vc; // 클라이언트 정보를 가지고 있는 리스트

   public SocketServer() {

      try {
         serverSocket = new ServerSocket(5000);
         vc = new Vector<>();
         while (true) {
            Socket socket = serverSocket.accept(); // 접속 대기 중!!
            System.out.println("접속완료");
            SocketThread st = new SocketThread(socket);
            st.start();
            vc.add(st); // Vector(동기화) = ArrayList(비동기화)
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