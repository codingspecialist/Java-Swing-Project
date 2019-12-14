package starz502Client.data;

public class DataTypeParser {
  
	/*ºó »ý¼ºÀÚ*/
	public DataTypeParser() {}
	
	public static Integer getDataType(String jSonData) {		
		return Integer.valueOf(String.valueOf(jSonData.charAt(12)));
	}
	
	/* DataTypeParser Test*/
//	public static void main(String[] args) {	
//		DataTypeParser dtp = new DataTypeParser();
//		
//		System.out.println(dtp.getDataType("{\"datatype\":0,\"username\":\"ssonsall\",\"password\":\"12345\"}"));
//	}
	
}
