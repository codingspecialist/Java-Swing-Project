package gui;

public class Hello {
	
	
	
	public static void main(String[] args) {
	String str = "USERLIST:ssar,cos,love";
		
		String protocol[] = str.split(":");
		
		String temp[] = protocol[1].split(",");
		
		System.out.println(temp.length);
		
		for (String string : temp) {
			// list갱신
			System.out.println(" append : "+string);
		}
	}
}
