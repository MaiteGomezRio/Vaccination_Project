package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dogclinic.pojos.User;
import vaccination.ifaces.DoctorManager;
import vaccination.ifaces.PatientManager;
import vaccination.ifaces.VaccineManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCDoctorManager;
import vaccination.jdbc.JDBCVaccineManager;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class Menu {

	private static DoctorManager doctorMan;
	private static VaccineManager vaccineMan;
	private static PatientManager patientMan;

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

	public static void main (String[] args) {
		    ConnectionManager conMan = new ConnectionManager();  
			doctorMan = new JDBCDoctorManager(conMan.getConnection());
			patientMan= new JDBCPatientManager(conMan.getConnection());
			vaccineMan = new JDBCVaccineManager(conMan.getConnection()); 
			
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
						System.out.println("2.Select doctor");     //this will the change by login
						System.out.println("0.Exit"); 
						
						int choice2 = Integer.parseInt(r.readLine()); 
						
						switch(choice2) {
						case 1: {
							registerDoctor();
							break;
						}
						case 2:{
							selectDoctor();
							break; 
						}
						case 0: {
							conMan.closeConnection();
							return;
						}
						}
					}
					case 2: {
						System.out.println("What do you want to do: "); 
						System.out.println("1.Register in the app");
						System.out.println("2.Check my vaccines");
						System.out.println("0.Exit"); 
						
						int choice3 = Integer.parseInt(r.readLine()); 
						
						switch(choice3) {
						case 1:{

							registerPatient(String d_id);
							break;

						}case 2:{
							System.out.println("Check my vaccines.");
							checkVaccinesOfPatient(String id);
							break;
						}case 0:{
							return;
					}
					}

			 	}catch(NumberFormatException nfe){
			 		System.out.println("you did not type a number");
			 		nfe.printStackTrace();
			 		
			 	}catch(IOException ioe){
			 		System.out.println("I/O Exception");
			 		ioe.printStackTrace();
			 	}
	}

	public static void registerDoctor() throws IOException {
		try {
			System.out.println("Please, input the doctor's data:");
			System.out.println("Name:");
			String name = r.readLine();
			System.out.println("Surname:");
			String surname = r.readLine();
			System.out.println("ID:");
			String id = r.readLine();
			// System.out.println("Password:");
			// String password = r.readLine();

			Doctor o = new Doctor(id, name, surname);
			doctorMan.insertDoctor(o);
		} catch (IOException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}

	}

	public static void selectDoctor() throws IOException {
		System.out.println("Please, tell me the doctor's name: ");
		String name = r.readLine();
		List<Doctor> listDoctors = doctorMan.searchDoctorByName(name);
		System.out.println(listDoctors);
		System.out.println("Choose which one it is, type its ID: ");
		String id = r.readLine();
		doctorMenu(id);
	}

	public static void registerPatient(String d_id) throws IOException {
		System.out.println("Plase, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("surname: ");
		String surname = r.readLine();
		System.out.println("id: ");
		String id = r.readLine();
		Doctor doctor = doctorMan.getDoctor(d_id);
		boolean attendance = false; // TODO is attendance turn flase by defect?
		Patient patient = new Patient(id, name, surname, attendance, doctor); // TODO i dont know if the doctor has to
																				// be in the constructor.
		patientMan.insertPatient(patient);
	}

	private static void doctorMenu(String id) {

		while (true) {
			try {
				System.out.println("Welcome doctor: ");
				System.out.println("Choose an option.");
				System.out.println("1. Register a new vaccine.");
				System.out.println("2. Check vaccines"); // TODO method that checks all vaccines of the database
				System.out.println("3. Check vaccines of a patient.");
				System.out.println("0. Return");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerVaccine(id); // the id we pass is the patient's id who is going to put that vaccine
					break;
				}
				case 2: {

					break;
				}
				case 3: {
					checkVaccinesOfPatient(id);
					break;
				}
				case 0: {
					return;
				}
				}
			} catch (IOException e) {
				System.out.println("I/O exception");
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("You did not type a number");
				e.printStackTrace();
			}
		}
	}

	public static void registerVaccine(String id) throws IOException { // vaccines are added by the doctor, not the
																		// manager.

		// in this method we are registering a vaccine and linking it to its doctor.
		// This is what he does, but we are linking it to the patient, but the patient
		// cant add vaccines so i dont understand.
		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		Patient patient = patientMan.getPatient(id);
		Vaccine vaccine = new Vaccine(name, dose, patient);
		// TODO ????? HE PUTS DOCTOR INSTEAD OF PATIENT
		vaccineMan.insertVaccine(vaccine);
	}

	public static List<Vaccine> checkVaccinesOfPatient(String id)throws IOException {  //this return the list of vaccines of a patient

    	 System.out.println("The vaccines of the patient are: ");
    	 List<Vaccine> vaccines = vaccineMan.searchVaccineByPatient(id); 
    	 System.out.println(vaccines);
    	 System.out.println("If you want to modify something of a specific vaccine type it's name: "); 
    	 System.out.println("If not, press enter");
    	 String vaccineName = r.readLine();
    	 if(!r.readLine()=null) {
    	     vaccineMenu(vaccineName); 
    	 else {
    		 return;
    	 }
     }

	public static void vaccineMenu(int id) {
    	 while(true) {
    		 try {
    			 System.out.println("what do you want to do to the vaccine: ");
    			 System.out.println("1.Assign to another patient");
    			 System.out.println("2.Remove the vaccine");
    			 System.out.println("0.Back to the doctor's menu");
    			 
    			 int choice = Integer.parseInt(r.readLine());
    			 
    			 switch(choice) {
    			 case 1:{

	assignVaccine(String p_id);break;}case 2:{

	removeVaccine(String name);break;}case 0:{return;}}

	}catch(

	NumberFormatException e){System.out.println("You did not type a number.");e.printStackTrace();}catch(
	IOException e){System.out.println("I/O Exception");e.printStackTrace();
	}}}

	public static void assignVaccine(String patientId) throws IOException {

		System.out.println("Tell me the name of the vaccine ");
		String name = r.readLine();
		List<Vaccine> vaccines = vaccineMan.searchVaccineByName(name);
		System.out.println(vaccines);
		vaccineMan.assignVaccineToPatient(name, patientId);
	}

	public static void removeVaccine(String name) {
		vaccineMan.removeVaccine(name);
	}
}
