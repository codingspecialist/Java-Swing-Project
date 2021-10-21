package Client;

import java.util.ArrayList;

public class Testss {
	
	public static void main(String[] args) {
		ArrayList<String> userList = new ArrayList<>();
		userList.add("안녕");
		userList.add("반가워");
		System.out.println(userList); // USERLIST:안녕,반가워
		
		String sendUsernames = "";
		
		for (int i = 0; i < userList.size(); i++) {
			if(i == userList.size()-1) {
				sendUsernames += userList.get(i);
			}else {
				sendUsernames += userList.get(i)+",";	
			}
			
		}
		System.out.println(sendUsernames);
	}
}
