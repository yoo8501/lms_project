package lus.db;

public class UserInformation {

	public static String[] user_data = new String[4];

	public UserInformation(int primary_id, String name, String id, String pw) {
		Integer pid = primary_id;
		
		user_data[0] = id;
		user_data[1] = pw;
		user_data[2] = name;
		user_data[3] = pid.toString();
	}

	public static String getCurrentUserID() {
		return user_data[0];
	}
}
