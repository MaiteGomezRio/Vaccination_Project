package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	public static boolean validateDate(LocalDate doaLocalDate) {
		boolean ok=true;
		if(doaLocalDate.isAfter(LocalDate.now())) {
		ok=false;
		}
		return ok;
		}
		
		public static boolean isValidEmail(String email) { 
		String emailFormat = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailFormat);		
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		}
		
		//public static boolean notEmpty() {}
		
		public static boolean validMenu(int numOps, int num) {
			boolean ok=true;
			if (num>numOps || num<0) {
				ok=false;
			}
			
			return ok;
		}
			
		public static int readInteger() {
	        int num = 0;
	        boolean ok = false;
	        do {
	            try {
	            	num = Integer.parseInt(r.readLine());
	                if (num < 0) {
	                    ok = false;
	                    System.out.print("You didn't type a valid number!");
	                } else {
	                    ok = true;
	                }
	            } catch (IOException e) {
	                e.getMessage();
	            } catch (NumberFormatException nfe) {
	            	System.out.print("You didn't type a valid number!");
	            }
	        } while (!ok);

	        return num;
	    }

		
		public static String readString() {
	        String text = null;
	        boolean ok = false;
	        do {
	            try {
	            	text = r.readLine();
	                if (!text.isEmpty()) {
	                    ok = true;
	                } else {
	                    System.out.println("Empty string, please try again:");
	                }
	            } catch (IOException e) {

	            }
	        } while (!ok);

	        return text;
	    }

}
