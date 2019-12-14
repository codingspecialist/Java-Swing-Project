package dao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BooleanSupplier;

import gui.CustomerMainFrame;

public abstract class MyCount implements ActionListener{
	
	CustomerDaoCustomer daoc = CustomerDaoCustomer.getInstance();
	public static int a = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(a==1) {
			CustomerMainFrame.wait.setText(daoc.select()+"");
			a=0;
		}
	
	}
		
		
}

