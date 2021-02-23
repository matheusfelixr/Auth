
package com.matheusfelixr.authentication.util;

public final class EmailHelper {

	public static String maskEmail(String email) {
		
		char[] emailChars = email.toCharArray();
		
		int index = email.indexOf("@");

		for(int i=4; i <= index-1 ; i++) {
			emailChars[i] = '*';
		}
		
		email = String.valueOf(emailChars);
		return email;
	}
	

}
