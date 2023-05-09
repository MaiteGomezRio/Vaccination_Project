package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import vaccination.ifaces.DirectorManager;
import vaccination.ifaces.DoctorManager;
import vaccination.ifaces.PatientManager;
import vaccination.ifaces.VaccineManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCDoctorManager;
import vaccination.jdbc.JDBCPatientManager;
import vaccination.jdbc.JDBCVaccineManager;
import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

	private static DoctorManager doctorMan;
	private static PatientManager patientMan;
	private static VaccineManager vaccineMan;
	private static DirectorManager directorMan;

	public static void main(String[] args) {
		ConnectionManager conMan = new ConnectionManager();
		doctorMan = new JDBCDoctorManager(conMan.getConnection());
		patientMan = new JDBCPatientManager(conMan.getConnection());
		vaccineMan = new JDBCVaccineManager(conMan.getConnection());
		while (true) {
			try {
				System.out.println("Welcome to the Vaccination app!");
				System.out.println("Are you a doctor or a patient? Choose an option, please:");
				System.out.println("1. Doctor");
				System.out.println("2. Patient");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					System.out.println("What do you want to do?: ");
					System.out.println("1.Register in the app.");
					System.out.println("2.Select doctor"); // this will then change by login
					System.out.println("0.Exit");

					int choice2 = Integer.parseInt(r.readLine());

					switch (choice2) {
					case 1: {
						registerDoctor();
						break;
					}
					case 2: {
						selectDoctor();
						break;
					}
					case 0: {
						conMan.closeConnection();
						return;
					}
					}
					break;
				}
				case 2: {
					System.out.println("What do you want to do: ");
					System.out.println("1.Register in the app");
					System.out.println("2.Check my vaccines");
					System.out.println("0.Exit");

					int choice3 = Integer.parseInt(r.readLine());

					switch (choice3) {

					case 1: {
						registerPatient();
						break;

					}
					case 2: {

						checkVaccinesOfPatient();
						break;
					}
					case 0: {
						conMan.closeConnection();
						return;
					}
					}
					break;
				}
				case 0: {
					conMan.closeConnection();
					System.out.println("Thank you for using the database! Goodbye.");
					return;
				}
				}

			} catch (NumberFormatException e) {
				System.out.println("You didn't type a number, idiot!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		}

	}

	public static void registerDoctor() throws IOException {
		try {
			System.out.println("Please, input the doctor's data:");
			//TODO generate random id ???
			int id=generateRandom();
			
			System.out.println("Id_document:");
			String id_document = r.readLine();
			
			System.out.println("Name:");
			String name = r.readLine();
			
			System.out.println("Surname:");
			String surname = r.readLine();
			
			// System.out.println("Password:");
			// String password = r.readLine();

			Doctor doctor = new Doctor(id_document,name, surname);
			doctorMan.insertDoctor(doctor);
			System.out.println("You have registered as a doctor!");
			
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

	public static void registerPatient() throws IOException {

		System.out.println("Please, introduce the following information: ");
		
		System.out.println("Id: ");
		String id_document=r.readLine();
		System.out.println("Name: ");
		String name = r.readLine();
		System.out.println("Surname: ");
		String surname = r.readLine();
		
		System.out.println("Have you had any disease? (enter 'no' if negative) "); 
		String d_name = r.readLine();
		Disease disease;
		if(d_name.equalsIgnoreCase("no")) {
			disease=null;
		}else {
			disease = patientMan.getDisease(d_name);
			
		}
		System.out.println("Do you have/ Have you had any relevant condition, such as being pregnant, had a stroke, allergies....? (enter 'no' if negative)"); 
		String c_type = r.readLine();
		Condition condition;
		if(c_type.equalsIgnoreCase("no")) {
			condition=null;
		}else {
			condition = patientMan.getCondition(c_type);
			
		}
		Patient patient = new Patient(id_document,name, surname, disease, condition);
		
		patientMan.insertPatient(patient);
		//TODO NO SE INSERTA O DA PROBLEMAS EN ESTA LÍNEA
		patientMan.assignDiseaseToPatient(patient.getId(),disease.getId());
		
		System.out.println("You have registered as a patient!");
	}

	public static void registerVaccine() throws IOException {
		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		Vaccine vaccine = new Vaccine(name, dose);
		directorMan.insertVaccine(vaccine);
		System.out.println("Vaccine "+ name+" registered");
	}
	// used when you want to put an specific vaccine to a patient
	public static void selectVaccines() throws IOException {
		List<Vaccine> listVaccines = vaccineMan.getAllVaccines();
		System.out.println(listVaccines);
	}

	public static void checkVaccinesOfPatient()throws IOException {  //this return the list of vaccines of a patient
		//TODO CORRECT COMMENTED 07/5/2023
		System.out.println("Introduce the patient's name: ");
		String patient_name=r.readLine();
		//List<Patient> list=patientMan.searchPatientByName(patient_name);
		//System.out.println(list); 
		System.out.println("Choose which one it is, type its ID: "); 
		Integer id = Integer.parseInt(r.readLine());
   	 	System.out.println("The vaccines of the patient are: ");
   	 	List<Vaccine> vaccines = vaccineMan.searchVaccinesByPatient(id); 
   	 	System.out.println(vaccines);
   	 	/*System.out.println("If you want to modify something of a specific vaccine, type it's name: "); 
   	 	System.out.println("If not, press enter");
   	 	String v_name = r.readLine();
   	 	if(r.readLine()!=null) {
   	 		vaccineMenu(v_name); 
   	 	}else {
   		 return;
   	 	}*/
    }
	public static void assignVaccine(int patientId) throws IOException {

		System.out.println("Tell me the name of the vaccine ");
		String name = r.readLine();
		List<Vaccine> vaccines = vaccineMan.searchVaccinesByPatient(patientId);
		System.out.println(vaccines);//TODO 07/05/2023
		//vaccineMan.assignVaccineToPatient(name, patientId);
	}

	public static void removePatient(String p_name) {//TODO 07/05/2023
		//Patient patient=(Patient) patientMan.searchPatientByName(p_name);
		//patientMan.removePatient(patient);// TODO create remove method in patient
	}

	public static void removeDoctor(String d_name) {
		Doctor doctor=(Doctor) doctorMan.searchDoctorByName(d_name);
		doctorMan.removeDoctor(doctor);// TODO create remove method in doctor
	}

	public static void removeVaccine(String v_name) {
		vaccineMan.removeVaccine(v_name);
	}

	private static void doctorMenu(String id) {

		while (true) {
			try {
				System.out.println("Welcome doctor: ");
				System.out.println("Choose an option.");
				System.out.println("1. Register a new vaccine.");
				System.out.println("2. Check vaccines");
				System.out.println("3. Check vaccines of a patient.");
				System.out.println("4. Assign a vaccine to a patient."); 
				System.out.println("0. Return");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerVaccine();
					break;
				}
				case 2: {
					//selectVaccines();// TODO create a method that returns the list of vaccines in the databae
					break;           //TODO create method selectVaccines()
				}
				case 3: {
					//checkVaccinesOfPatient();
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
	
	public static void vaccineMenu(String name) {
   	 while(true) {
   		 try {
   			 System.out.println("What do you want to do to the vaccine: ");
   			 System.out.println("1.Assign to another patient");
   			 System.out.println("2.Remove the vaccine");
   			 System.out.println("0.Back to the doctor's menu");
   			 
   			 int choice = Integer.parseInt(r.readLine());
   			 
   			 switch(choice) {
   			 case 1:{
   				 //assignVaccine(int p_id);
   				 break;
   			 }
   			 case 2:{
   				 //removeVaccine(String v_name);
   				 break;
   			 }case 0:{
   				 return;
   			}}

   		 }catch(NumberFormatException e){
   			 System.out.println("You did not type a number.");
   			 e.printStackTrace();
   		 }catch(IOException e){
   			 System.out.println("I/O Exception");
   			 e.printStackTrace();
   		 }
   	 }
   	 }
	
	public static int generateRandom() {
		Random random = new Random();
        long randomNumber = random.nextLong() % 10000000000L;
        if (randomNumber < 0) {
            randomNumber += 10000000000L;
        }
        return (int)randomNumber;
	}

}
