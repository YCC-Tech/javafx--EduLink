package dto;

public class User {
	
	private static String username;

	public static String getEmail() {
		return username;
	}

	public static void setEmail(String email) {
		User.username = email;
	}
	
	
	

}
