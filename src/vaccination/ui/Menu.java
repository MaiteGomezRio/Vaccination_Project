
package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.time.*;

import vaccination.ifaces.AppointmentManager;
import vaccination.ifaces.ConditionManager;
import vaccination.ifaces.DirectorManager;
import vaccination.ifaces.DiseaseManager;
import vaccination.ifaces.DoctorManager;
import vaccination.ifaces.PatientManager;
import vaccination.ifaces.UserManager;
import vaccination.ifaces.VaccineManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCDoctorManager;
import vaccination.jdbc.JDBCPatientManager;
import vaccination.jdbc.JDBCVaccineManager;
import vaccination.jpa.JPAUserManager;
import vaccination.pojos.Appointment;
import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;
import vaccination.pojos.Role;
import vaccination.pojos.Vaccine;
import vaccination.pojos.User; 

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 

	private static DoctorManager doctorMan;
	private static PatientManager patientMan;
	private static VaccineManager vaccineMan;
	private static DirectorManager directorMan;
	private static DiseaseManager diseaseMan;
	private static UserManager userMan; 
	private static ConditionManager conMan;
	private static AppointmentManager appointmentMan; 

	public static void main(String[] args) {
		ConnectionManager conMan = new ConnectionManager();
		doctorMan = new JDBCDoctorManager(conMan.getConnection());
		patientMan = new JDBCPatientManager(conMan.getConnection());
		vaccineMan = new JDBCVaccineManager(conMan.getConnection());
		userMan = new JPAUserManager();
		while (true) {
			try {
				System.out.println("Welcome to the Vaccination app!");
				System.out.println("What do you want to do? :");
				System.out.println("1. Register as a doctor");
				System.out.println("2. Register as a patient");
				System.out.println("3. Register as a director");
				System.out.println("4. Log in"); 
				System.out.println("0. Exit");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				    case 1: {
					     registerDoctor(); 
				    }
				    case 2: {
					     registerPatient(); 
				    }
				    case 3:{
				       	 registerDirector();
				    }
				    case 4:{
					     login(); 
				    }
				    case 0: {
					    conMan.closeConnection();
					    System.out.println("Thank you for using the database! Goodbye.");
					   return;
				    }
				}

			} catch (NumberFormatException e) {
				System.out.println("You didn't type a number!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		}

	}
	public static void login() throws IOException{
		while(true) {
		System.out.println("Username: ");
		String username = r.readLine();
		System.out.println("Password: ");
		String password = r.readLine();
		User user = userMan.login(username, password); 
		    if(user != null) {
		    	if(user.getRole().getName().equals("doctor")) {
		    		doctorMenu(user.getEmail());
		    	}else if(user.getRole().getName().equals("patient")) {
		    		patientMenu(user.getEmail());
		    	}else if(user.getRole().getName().equals("director")){
		    		directorMenu(user.getEmail());
		    	}
		    }else {
			    System.out.println("Wrong username/password combination"); 
		    }
		}
	}

	public static void registerDoctor() throws IOException {
		try {
			System.out.println("Please, input the doctor's data:");
			System.out.println("Id_document:");
			String id_document = r.readLine();
			System.out.println("Name:");
			String name = r.readLine();
			System.out.println("Surname:");
			String surname = r.readLine();
			System.out.println("Email: ");
			String email = r.readLine();
			String username=id_document;
			System.out.println("Password:");
			String password = r.readLine();

			Doctor doctor = new Doctor(id_document,name, surname, email);
			doctorMan.insertDoctor(doctor);
			User user = new User(username, password, email); 
			userMan.register(user);
			Role role = userMan.getRole("doctor"); 
			userMan.assignRole(user, role);
			
			System.out.println("You have registered as a doctor!");
			
		} catch (IOException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}

	}
	public static void registerCondition() {
		try {
			System.out.println("Please, input the condition's name: ");
			String type = r.readLine();
			Condition condition = new Condition(type);
			directorMan.insertCondition(condition); 
		}catch(IOException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
	}
	public static void registerDisease() {
		try {
			System.out.println("Please, tell me the name of the disease: ");
			String name = r.readLine(); 
			Disease disease = new Disease(name);
			directorMan.insertDisease(disease);
		}catch(IOException e) {
			System.out.println("I/OException");
			e.printStackTrace();
		}
	}
	//TODO delete if not used4
	/*public static void selectDoctor() throws IOException {
		System.out.println("Please, tell me the doctor's name: ");
		String name = r.readLine();
		List<Doctor> listDoctors = doctorMan.searchDoctorByName(name);
		System.out.println(listDoctors);
		System.out.println("Choose which one it is, type its ID: ");
		String id = r.readLine();
		doctorMenu(id);
	}*/

	
	
	public static void registerPatient() throws IOException {

		System.out.println("Please, introduce the following information: ");
		
		System.out.println("ID document: ");
		String id_document=r.readLine();
		System.out.println("Name: ");
		String name = r.readLine();
		System.out.println("Surname: ");
		String surname = r.readLine();
		System.out.println("email: ");
		String email = r.readLine(); 
		System.out.println("password: ");
		String password = r.readLine(); 
		String username=id_document;
		Patient patient = new Patient(id_document,name, surname, email);
		patientMan.insertPatient(patient);
		User user = new User(username, password, email);
		userMan.register(user);
		Role role = userMan.getRole("patient"); 
		userMan.assignRole(user, role);
		
		System.out.println("You have registered as a patient!");
	}

	public static void registerVaccine() throws IOException {
		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		System.out.println("disease: ");
		String dis_name=r.readLine();
		Disease disease= new Disease(dis_name);
		Vaccine vaccine = new Vaccine(name, dose, disease);
		directorMan.insertVaccine(vaccine);
		System.out.println("Vaccine "+ name+" registered");
	}
	public static void registerDirector() {
		
		try {
			System.out.println("Introduce your id:");
			String id_document=r.readLine();
			System.out.println("Password:");
			String password=r.readLine();
			System.out.println("Email:");
			String email = r.readLine();
			String username=id_document;
			User user = new User(username, password, email); 
			userMan.register(user);
			Role role = userMan.getRole("director"); 
			userMan.assignRole(user, role);
			
			System.out.println("You have registered as a director!");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public static void selectVaccines() throws IOException {
		List<Vaccine> listVaccines = vaccineMan.getAllVaccines();
		System.out.println(listVaccines);
	}

	public static void checkVaccinesOfPatientBeingADoctor()throws IOException { 
		try {
			System.out.println("Introduce the patient's name: ");
			String patient_name=r.readLine();
			List<Patient> list=patientMan.searchPatientByName(patient_name);
			System.out.println(list); 
			System.out.println("Choose which one it is, type its ID: "); 
			Integer id = Integer.parseInt(r.readLine());
	   	 	System.out.println("The vaccines of the patient are: ");
	   	 	List<Vaccine> vaccines = vaccineMan.searchVaccinesByPatient(id); 
	   	 	System.out.println(vaccines);
		}catch(IOException e) {
			System.out.println("I/O Exception"); 
			e.printStackTrace();
		}
    }
	public static void checkVaccinesOfPatientBeingAPatient(int p_id) {
		System.out.println("Your vaccines are:"); 
		List<Vaccine> vaccines = vaccineMan.searchVaccinesByPatient(p_id); 
		System.out.println(vaccines); 
	}
	public static void checkVaccinesOfDisease() throws IOException{
		System.out.println("Type the name of the disease you want to check: ");
		String d_name= r.readLine();	
		Disease disease=new Disease(d_name);
		List<Vaccine> list=vaccineMan.searchVaccinesByDisease(disease.getId());
		System.out.println(list); 
			
	}
	public static void checkVaccinesOfPatient() throws IOException{
		System.out.println("Type the name of the disease you want to check vaccines of: ");
		String d_name= r.readLine();	
		Disease disease=diseaseMan.getDisease(d_name);
		List<Vaccine> list=vaccineMan.searchVaccinesByDisease(disease.getId());
		System.out.println(list); 
			
	}
	public static void checkAppointmentsOfPatient(int p_id) {
		System.out.println("Your appointments are: ");
		List<Appointment> list=appointmentMan.searchAppointmentsByPatient(p_id);
		System.out.println(list); 
	}
	
	public static void checkDosesOfVaccine() throws IOException{
		System.out.println("Type the name of the vaccine you want to check: ");
		String v_name=r.readLine();
		Vaccine vaccine=vaccineMan.getVaccine(v_name);
		System.out.println(v_name+" has the total of: "+vaccine.getDose()+ " dosis");	
	}
	public static void checkConditionsVaccine()  throws IOException{
		
		System.out.println("Which vaccine would yo like to check, type the name: ");
		String v_name=r.readLine();
		Vaccine vaccine=vaccineMan.getVaccine(v_name);
		List<Condition> conditions=conMan.checkConditionsOfAVaccine(vaccine.getId());
		System.out.println(conditions);
	
	}

	//TODO checkConditionOfPatient
	//TODO checkVaccinesAPatientHasOn
	//TODO checkVaccinesAPatientHasToPut
	//TODO checkDiseasesOfPatient con immunity
	public static void updateConditionsOfPatient(int p_id) {
		
		System.out.println("How many new conditions do you have? Introduce a number ");
		int number;
		try {
			number = Integer.parseInt(r.readLine());
			for(int i=0; i<number; i++) {
				System.out.println("Introduce condition name:");
				String c_type=r.readLine();
				Condition condition=new Condition(c_type);
				int c_id = condition.getId();
				conMan.updateConditionsOfPatient(p_id, c_id);
			}		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		} 	
	}

	public static void removePatient() {
		System.out.println("Introduce the name of the patient you want to remove from the database: ");
		String p_name;
		try {
			p_name = r.readLine();
			directorMan.removePatient(p_name);
		} catch (IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}
	}
	
	public static void removeDoctor() {
		try {
		System.out.println("Introduce the name of the doctor you want to remove from the database: ");
		String d_name=r.readLine();
		directorMan.removeDoctor(d_name);
		
		}catch(IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}
	}

	public static void removeVaccine() {
		try {
			System.out.println("Introduce the name of the vaccine you want to remove from the database: ");
			String v_name=r.readLine();
			directorMan.removeVaccine(v_name);
			
		}catch(IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}
		
	}

	public static void assignDoctorToPatient() {
		try {
			System.out.println("Introduce the name of the doctor you want to assign: ");
			String d_name=r.readLine();
			List<Doctor> doctors=doctorMan.searchDoctorByName(d_name);
			System.out.println("Introduce the id of the doctor: ");
			int d_id = Integer.parseInt(r.readLine()); 
			System.out.println("Now, introduce the name of the patient: ");
			String p_name=r.readLine();
			List<Patient> patients = patientMan.searchPatientByName(p_name); 
			System.out.println("Introduce the id of the doctor: "); 
			int p_id = Integer.parseInt(r.readLine()); 
			directorMan.assignDoctorToPatient(d_id,p_id);
		} catch (IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}
	}
	
	public static void assignConditionToVaccine() {
		try {
			System.out.println("Tell me the name of the condition: ");
			String c_name = r.readLine(); 
			System.out.println("Tell me the name of the vaccine: "); 
			String v_name = r.readLine(); 
			directorMan.assignConditionToVaccine(c_name, v_name);
		}catch(IOException e) {
			System.out.println("I/O Excepption"); 
			e.printStackTrace();
		}
	}
	

	public static void assignVaccineToPatient() throws IOException {
		System.out.println("Tell me the name of the patient.");
        String name = r.readLine(); 
        List<Patient> patients=patientMan.searchPatientByName(name);
        System.out.println(patients);
        System.out.println("Select the id of the patient: ");
        int id = Integer.parseInt(r.readLine());
		System.out.println("Tell me the name of the vaccine ");
		String name_v = r.readLine();
		
		vaccineMan.assignVaccineToPatient(name_v, id);
	}
	
	
	public static int generateRandomInt(int bound) {
	    Random random = new Random();
	    return random.nextInt(bound);
	}
	public static void setAppointment(int p_id) {
		try {
			//NOS DIJO RODRIGO QUE DIESEMOS POR HECHO QUE ANTES DE SET APPOINTMENT, EL PACIENTE HA HECHO UN UPDATE DE SUS CONDITIONS.
            
			
			Patient patient = patientMan.getPatient(p_id); 
			//first ask what disease they want to get vaccinated
			System.out.println("Please, tell me the disease you want to put a vaccine of.");
			String d_name = r.readLine(); 			
			Disease disease = diseaseMan.getDisease(d_name); 
			int d_id = disease.getId();
			// Get all the possible vaccines according to the disease
			List<Vaccine> vaccines = vaccineMan.searchVaccinesByDisease(d_id); 
			for(int i=0; i<vaccines.size();i++ ) {				
				Vaccine v=vaccines.get(i);				
			List<Condition> conditionsVaccine=conMan.checkConditionsOfAVaccine(v.getId());
			//search and show the patient the relevant conditions of the vaccines
			System.out.println(conditionsVaccine);
			}			
			// verify that the condition chosen, get the one that does not match with a vaccine condition
            System.out.println("Do you have any of these relevant conditions, type the name: "); 
            String c_name = r.readLine(); 
            Condition condition = conMan.getCondition(c_name); 
            int c_id = condition.getId(); 
            Vaccine vaccine = conMan.getVaccineDependingOnCondition(d_id, c_id); 
            
			System.out.println("Please, tell me the date at which you want to set the appointment. (yyyy-MM-dd)");
			String doa = r.readLine();
			LocalDate doaLocalDate = LocalDate.parse(doa, formatter);       // the date is usually stored in the db as java.sql.Date, which stores the date as the amount of seconds that have passed sinc
			Date doaDate = Date.valueOf(doaLocalDate);
			int bound=doctorMan.countNumberOfDoctors();
			int doc_id=generateRandomInt(bound);
			Doctor doctor=doctorMan.getDoctorById(doc_id);														//we should not show the date as the amount of seconds that... so that is why we use Localdate.
			Appointment appointment = new Appointment(doaDate, doctor, patient, vaccine); 	//we need to turn it into a Date in order to store it into the db. When i create the puts i whould pass the Date. 
			appointmentMan.insertAppointment(appointment);
			
		}catch(IOException e) {
			System.out.println("I/O Exception");
			e.printStackTrace(); 
		}
	}
	
	public static void checkMyAppointmentsBeingAPatient(int p_id) {
		System.out.println("Your vaccines are:"); 
		List<Appointment> appointments = appointmentMan.checkAppointmentsOfPatient(p_id);
		System.out.println(appointments); 
	}
	
	public static void cancelAppointment(int p_id) {
		try {
			System.out.println("These are the list of your appointments: ");
			List<Appointment> appointments = appointmentMan.checkAppointmentsOfPatient(p_id);
			System.out.println(appointments); 
			System.out.println("Please, tell me the id of the appointment you want to remove: ");
			int id = Integer.parseInt(r.readLine());
			//Appointment appointment = appointmentMan.getAppointmentById(int id); 
			appointmentMan.removeAppointment(p_id);
		 
		}catch(IOException e) {
			System.out.println("I/O Exception");
			e.printStackTrace();
		}
	}
	public static void directorMenu(String email) {
		
		while(true) {
			System.out.println("What do you want to do? ");
			System.out.println("1.Insert data.");
			System.out.println("2.Remove data.");
			System.out.println("3.Assign data.");
			System.out.println("0.Exit");
			
			int choice3;
			try {
				choice3 = Integer.parseInt(r.readLine());
				switch(choice3) {
				
				case 1:{
					System.out.println("1.Insert condition");
					System.out.println("2.Insert vaccine");
					System.out.println("3.Insert disease");
					System.out.println("0.Exit");
					
					int choice=Integer.parseInt(r.readLine());
					switch(choice) {
					case 1:{
						registerCondition();
						break;
					}case 2:{
						registerVaccine();
						break;
					}case 3:{
						registerDisease();
						break;
					}case 0:{
						return;
					}
					}
					
				}
				case 2:{
					System.out.println("1.Remove doctor");
					System.out.println("2.Remove vaccine");
					System.out.println("3.Remove patient");
					System.out.println("0.Exit");
					
					int choice=Integer.parseInt(r.readLine());
					switch(choice) {
					case 1:{
						removeDoctor();
						break;
					}case 2:{
						removeVaccine();
						break;
					}case 3:{
						removePatient();
						break; 
					}case 0:{
						return;
					}
					}
					
				}case 4:{
					System.out.println("1.Assign condition to vaccine");
					System.out.println("2.Assign doctor to patient");
					System.out.println("3.Assign disease to vaccine");
					System.out.println("0.Exit");
					int choice=Integer.parseInt(r.readLine());
					switch(choice) {
					case 1:{
						assignConditionToVaccine();
						break;
					}case 2:{
						assignDoctorToPatient();
						break;
					}case 3:{
						//assignDiseaseToVaccine();
					}case 0:{
						return;
					}
					}	
				}case 0:{
					return;
				}
				}
			}catch (NumberFormatException | IOException e) {
					e.printStackTrace();
			}
		}		
	}	
	
	public static void patientMenu(String email) {
		Patient patient = patientMan.getPatientByEmail(email);
		while(true) {
			try {
				System.out.println("Welcome patient!"); 
				System.out.println("What do you want to do? Choose an option: ");
				System.out.println("1. Check my vaccines");
				System.out.println("2. Check my appointments"); 
				System.out.println("3. Set an appointment"); 
				System.out.println("4. Cancel an appointment"); 
				System.out.println("5. Update my conditions"); 
				System.out.println("0. Exit"); 
				int option = Integer.parseInt(r.readLine()); 
				
				switch(option) {
					case 1:{
						checkVaccinesOfPatientBeingAPatient(patient.getId()); 
					}
					case 2:{
						checkMyAppointmentsBeingAPatient(patient.getId()); 
					}
					case 3:{
						setAppointment(patient.getId()); 
					}
					case 4:{
						//TODO
					}case 5:{
						updateConditionsOfPatient(patient.getId());
					}
					case 0:{
						return; 
					}
				}
			}catch(IOException e){
				System.out.println("I/O Exception");
				e.printStackTrace();
			}catch(NumberFormatException e) {
				System.out.println("You did not type a number");
				e.printStackTrace();
			}
		}
	}

	private static void doctorMenu(String email) {
        Doctor doctor = doctorMan.getDoctorByEmail(email); 
		while (true) {
			try {
				System.out.println("Welcome doctor: ");
				System.out.println("Choose an option.");
				System.out.println("1. Register a new vaccine.");
				System.out.println("2. Check vaccines");
				System.out.println("3. Check vaccines of a patient.");
				System.out.println("4. Assign a vaccine to a patient.");
				System.out.println("5. Unassign vaccine of a patient."); 				
				System.out.println("0. Return");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				    case 1: {
					    registerVaccine();
					    break;
				    }
				    case 2: {
					    selectVaccines();
					    break;           
				    }
				    case 3: {
					    checkVaccinesOfPatientBeingADoctor();
					    break;
				    }
				    case 4:{
					    assignVaccineToPatient(); 
					    break;
				    }case 5:{
				    	
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
}
