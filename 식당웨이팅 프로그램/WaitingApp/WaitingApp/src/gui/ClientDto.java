package gui;

import java.sql.Timestamp;

public class ClientDto {
	private int seq;
	private String phone;
	private int  nParty;
	private Timestamp receTime;
	
	
	public ClientDto(int seq, String phone, int nParty, Timestamp receTime) {
		super();
		this.seq = seq;
		this.phone = phone;
		this.nParty = nParty;
		this.receTime = receTime;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getnParty() {
		return nParty;
	}


	public void setnParty(int nParty) {
		this.nParty = nParty;
	}


	public Timestamp getReceTime() {
		return receTime;
	}


	public void setReceTime(Timestamp receTime) {
		this.receTime = receTime;
	}
	
	
	
	
	
}
