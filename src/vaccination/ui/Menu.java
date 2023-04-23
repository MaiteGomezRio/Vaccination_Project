package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import vaccination.ifaces.DoctorManager;
import vaccination.ifaces.VaccineManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCDoctorManager;
import vaccination.pojos.Doctor;


public class Menu {
	 
	private static DoctorManager doctorMan; 
	private static VaccineManager vaccineMan; 

	 private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in)); 
	 
	 public static void main (String[] args) {
		    ConnectionManager conMan = new ConnectionManager();  
			doctorMan = new JDBCDoctorManager();
			
		 while(true) {
			 try {
					System.out.println("Are you a doctor or a patient?");
					System.out.println("1. Doctor");
					System.out.println("2. Patient"); 
					System.out.println("0. Exit");

					int choice = Integer.parseInt(r.readLine());

					switch (choice) {
					case 1: {
						System.out.println("What do you want to do: "); 
						System.out.println("1.Register in the app.");
						System.out.println("2.Log in");     //not sure about this
						System.out.println("0.Exit"); 
						
						int choice2 = Integer.parseInt(r.readLine()); 
						
						switch(choice2) {
						case 1: {
							registerDoctor();
							break;
						}
						case 2:{
							//login of the doctor??
							break; 
						}
						case 0: {
							break;
						}
						}
						break;
					}
					case 2: {
						System.out.println("What do you want to do: "); 
						System.out.println("1.Register in the app");
						System.out.println("2.See my information");
						System.out.println("0.Exit"); 
						
						int choice2 = Integer.parseInt(r.readLine()); 
						
						switch(choice2) {
						case 1:{
							break;
						}
						case 2:{
							break; 
						}
						}
					}

					case 0: {

						return;
					}
					}
				 
			 }catch(NumberFormatException e) {
				 System.out.println("you did not type a number"); 
				 e.printStackTrace();
			 }catch(IOException e) {
				 System.out.println("I/O Exception");
				 e.printStackTrace();
			 }
		 }  
	 }

	 public static void registerDoctor() throws IOException{
		 try {
		 System.out.println("Please, input the doctor's data:");
			System.out.println("Name:");
			String name = r.readLine();
			System.out.println("Surname:");
			String surname = r.readLine();
			System.out.println("ID:");
			String id = r.readLine();
			//System.out.println("Password:");
			//String password = r.readLine();
			
			Doctor o = new Doctor(id, name, surname); 
			doctorMan.insertDoctor(o);
		 }catch(IOException e) {
			 System.out.println("Exception");
			 e.printStackTrace(); 
		 }
	 
	 }

	 public static void selectDoctor() throws IOException{
		 System.out.println("Please, tell me the doctor's name: ");
		 String name = r.readLine();
		 List<Doctor> listDoctors = doctorMan.searchDoctorByName(name); 
		 System.out.println(listDoctors); 
		 System.out.println("Choose which one it is, type its ID: ");
		 String id = r.readLine(); 
		 ownerMenu(id); 
	 }

	private static void ownerMenu(String id) {
		// TODO Auto-generated method stub
		
	}
}


