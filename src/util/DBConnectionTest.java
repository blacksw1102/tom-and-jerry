package util;

public class DBConnectionTest {
	public static void main(String[] args) {
		boolean result = true;
		result = DBManager.checkIdDuplicate("admin");
		System.out.println(result);
	}	
}
