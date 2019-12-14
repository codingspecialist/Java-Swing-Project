package models;

import java.sql.Timestamp;

public class Customer {
   private int no;
   private String phone;
   private int people;
   private Timestamp time;
   private String state;
   
   public Customer() {
      
   }

   public Customer(int no, String phone, int people, Timestamp time, String state) {
      this.no = no;
      this.phone = phone;
      this.people = people;
      this.time = time;
      this.state = state;
   }
   
   public Customer(String phone, int people) {
      this.phone = phone;
      this.people = people;
   }

   public int getNo() {
      return no;
   }

   public void setNo(int no) {
      this.no = no;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public int getPeople() {
      return people;
   }

   public void setPeople(int people) {
      this.people = people;
   }

   public Timestamp getTime() {
      return time;
   }

   public void setTime(Timestamp time) {
      this.time = time;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }
   
   
}