package vaccination.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
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
import vaccination.ifaces.XMLManager;
import vaccination.jdbc.ConnectionManager;
import vaccination.jdbc.JDBCAppointmentManager;
import vaccination.jdbc.JDBCConditionManager;
import vaccination.jdbc.JDBCDirectorManager;
import vaccination.jdbc.JDBCDiseaseManager;
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
import vaccination.xml.XMLManagerImpl;
import vaccination.pojos.User;

public class Menu {
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static XMLManager xmlMan = new XMLManagerImpl();
	private static DoctorManager doctorMan;
	private static PatientManager patientMan;
	private static VaccineManager vaccineMan;
	private static DirectorManager directorMan;
	private static DiseaseManager diseaseMan;
	private static UserManager userMan;
	private static ConditionManager condMan;
	private static AppointmentManager appointmentMan;
	
	public static void main(String[] args) {
	 	  

		 ConnectionManager conMan = new ConnectionManager();
	 	 doctorMan = new JDBCDoctorManager(conMan.getConnection());
	 	 patientMan = new JDBCPatientManager(conMan.getConnection());
	 	 vaccineMan = new JDBCVaccineManager(conMan.getConnection()); 	 
	 	 directorMan = new JDBCDirectorManager(conMan.getConnection());
	 	 diseaseMan= new JDBCDiseaseManager(conMan.getConnection());
	 	 condMan = new JDBCConditionManager(conMan.getConnection()); 
	 	 appointmentMan=new JDBCAppointmentManager(conMan.getConnection());
	 	 
	 	 userMan = new JPAUserManager();
	 	 
	 	 while (true) {
	 	 	 try {
	 	 	 	 System.out.println("\nWelcome to the Vaccination app!");
	 	 	 	 System.out.println("What do you want to do? :");
	 	 	 	 System.out.println("1. Register");
	 	 	 	 System.out.println("2. Log in"); 
	 	 	 	 System.out.println("0. Exit");
	 	 	 	 int choice = Integer.parseInt(r.readLine());
	 	 	 	 switch (choice) {
	 	 	 	 case 1: {
	 	 	 	 	 registerMenu();
	 	 	 	 	 break;
	 	 	 	 }
	 	 	 	 case 2:{
	 	 	 	 	 login(); 
	 	 	 	 	 break;
	 	 	 	 }
	 	 	 	 case 0: {
	 	 	 	 	 conMan.closeConnection();
	 	 	 	 	 System.out.println("Thank you for using the database! Goodbye.");
	 	 	 	 	 userMan.disconnect();
	 	 	 	 	 return;
	 	 	 	 }
	 	 	 	 default:
	 	 	 		 break;
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
	
	public static void registerMenu() {
		System.out.println("1. Register as a doctor");
 	 	System.out.println("2. Register as a patient");
 	 	System.out.println("0. Exit");
 	 	int choice;
		try {
			choice = Integer.parseInt(r.readLine());
			switch (choice) {
	 	 	 case 1: {
	 	 	 	 registerDoctor(); 
	 	 	 	 break;
	 	 	 }
	 	 	 case 2: {
	 	 		 if(doctorMan.countNumberOfDoctors()==0) {
	 	 			 System.out.println("There are no doctors available. Come back later!\n");
	 	 		 }else {
	 	 			registerPatient();
	 	 		 }
	 	 	 	  
	 	 	 	 break;
	 	 	 }case 0:{
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
	public static void login() throws IOException {
		while (true) {
			System.out.println("\n Press 0 to go back to menu\n");
			
			System.out.println("Username: ");
			String username = r.readLine();
			if(username.equals("0")) {
				System.out.println("\n");
				break;
			}
			System.out.println("Password: ");
			String password = r.readLine();
			User user = userMan.login(username, password);
			if (user != null) {
				if (user.getRole().getName().equals("doctor")) {
					doctorMenu(user.getEmail());
				} else if (user.getRole().getName().equals("patient")) {
					patientMenu(user.getEmail());
				} else if (user.getRole().getName().equals("director")) {
					directorMenu(user.getEmail());
				}
			} else {
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
			String username = id_document;
			System.out.println("Password:");
			String password = r.readLine();
			System.out.println("Your username is: "+id_document+"\n");
			Doctor doctor = new Doctor(id_document, name, surname, email);
			doctorMan.insertDoctor(doctor);
			User user = new User(username, password, email);
			userMan.register(user);
			Role role = userMan.getRole("doctor");
			userMan.assignRole(user, role);

			System.out.println("You have registered as a doctor!");
			doctorMenu(user.getEmail());
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
		} catch (IOException e) {
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
		} catch (IOException e) {
			System.out.println("I/OException");
			e.printStackTrace();
		}
	}

	public static void registerPatient() throws IOException {
		System.out.println("Please, introduce the following information: ");

		System.out.println("ID document: ");
		String id_document = r.readLine();
		System.out.println("Name: ");
		String name = r.readLine();
		System.out.println("Surname: ");
		String surname = r.readLine();
		System.out.println("email: ");
		String email = r.readLine();
		System.out.println("password: ");
		String password = r.readLine();
		String username = id_document;
		System.out.println("Your username is: "+id_document+"\n");
		
		int bound = doctorMan.countNumberOfDoctors();
		System.out.println(bound);
		int doc_id = generateRandomInt(bound);
		System.out.println("doc id "+doc_id);
		Doctor doctor = doctorMan.getDoctorById(doc_id);
		System.out.println("doctor: "+doctor.toString());
		Patient patient = new Patient(id_document, name, surname, email, doctor);
		
		patientMan.insertPatient(patient, doctor);
		User user = new User(username, password, email);
		userMan.register(user);
		Role role = userMan.getRole("patient");
		userMan.assignRole(user, role);

		System.out.println("You have registered as a patient!\n");
		patientMenu(user.getEmail());
		
	}

	public static void registerVaccine() throws IOException {
		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		System.out.println("disease: ");
		String dis_name = r.readLine();
		Disease disease = diseaseMan.getDisease(dis_name);
		//int d_id = disease.getId();
		Vaccine vaccine = new Vaccine(name, dose, disease);
		directorMan.insertVaccine(vaccine);
		/*String c_name;
		int v_id = vaccine.getId();
	    do{
	    	System.out.println("Tell me the name of the condition, if there are no more conditions press 0");
	    	c_name=r.readLine();
	    	Condition condition = condMan.getCondition(c_name);
	    	int c_id = condition.getId();
	    	directorMan.assignConditionToVaccine(c_id, v_id);
	    }while(Integer.parseInt(c_name)!=0); */
		System.out.println("Vaccine " + name + " registered");
	}

	public static void selectVaccines() throws IOException {
		List<Vaccine> listVaccines = vaccineMan.getAllVaccines();
		if (listVaccines.isEmpty()) {
			System.out.println("There is no vaccines registered in the database yet");
		}else {
		for(Vaccine vaccine: listVaccines) {
			System.out.print(vaccine);						
			System.out.println(", "+diseaseMan.searchDiseaseById(vaccine.getDiseaseId()));
		}
		}
		
	}

	public static void selectPatients(int d_id) throws IOException {
		List<Patient> listPatients = patientMan.searchPatientsByDoctor(d_id);
		if (listPatients.isEmpty()) {
			System.out.println("There is no patients registered in the database yet");
		}else {
	 for(Patient patient: listPatients) {
		System.out.println(patient);
	}
		}
	}
	
	
	public static void checkVaccinesOfDisease() throws IOException {
		System.out.println("Type the name of the disease you want to check: ");
		String d_name = r.readLine();

		Disease disease = new Disease(d_name);
		List<Vaccine> list = vaccineMan.searchVaccinesByDisease(disease.getId());
		
		if (list.isEmpty()) {
			System.out.println("There is no vaccines registered in the database yet");
		}else {
		
		for(Vaccine vaccine: list) {
			System.out.println(vaccine);
		}
		}
	}

	public static void checkVaccinesAPatientHasToPut(int p_id) {
		System.out.println("The vaccines you have have to put on are: ");
		List<Appointment> appointments = appointmentMan.searchAppointmentsByPatient(p_id);
		Iterator<Appointment> it = appointments.iterator();
		List<Vaccine> vaccines = new ArrayList<>();
		Vaccine vaccine;
		while (it.hasNext()) {
			Date date = Date.valueOf(LocalDate.now());
			if (it.next().getDate().after(date)) {
				vaccine = it.next().getVaccine();
				vaccines.add(vaccine);

			}
		}
		System.out.println(vaccines);
	}

	public static void checkVaccinesPatientHasToPut(int d_id) {//for doctor
		List<Patient> list=new ArrayList<>();
		List<Patient> listPatients = new ArrayList<>();
		try {
			listPatients = patientMan.searchPatientsByDoctor(d_id);
			if (listPatients.isEmpty()) {
				System.out.println("You don't have any patients!");
			}else {
				System.out.println("Introduce the name of the patient you want to check: ");
				String name = r.readLine();
				list = patientMan.searchPatientByName(name);
				if(list.isEmpty()) {
					System.out.println("You don't have any patients with that name. Please enter a valid name: ");
					System.out.println(list);
				}else{
					System.out.println("Tell me which one it is, type it's Id");
					int p_id = Integer.parseInt(r.readLine());
					checkVaccinesAPatientHasToPut(p_id);
				}
			}	
		} catch (NumberFormatException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}

	}

	public static void checkVaccinesAPatientHasOn(int p_id) {
		System.out.println("The vaccines you have already put on are: ");
		List<Appointment> appointments = appointmentMan.searchAppointmentsByPatient(p_id);
		Iterator<Appointment> it = appointments.iterator();
		List<Vaccine> vaccines = new ArrayList<>();
		Vaccine vaccine;
		while (it.hasNext()) {
			Date date = Date.valueOf(LocalDate.now());
			if (it.next().getDate().before(date)) {
				vaccine = it.next().getVaccine();
				vaccines.add(vaccine);

			}
		}
		System.out.println(vaccines);
	}

	public static void checkConditionsOfPatient(int p_id) throws IOException {
		System.out.println("Your conditions are: ");
		List<Condition> list = condMan.getConditionsOfPatient(p_id);
		System.out.println(list);

	}

	public static void checkAppointmentsOfPatient(int p_id) {
		System.out.println("Your appointments are: ");
		List<Appointment> list = appointmentMan.searchAppointmentsByPatient(p_id);
	 	 for(Appointment ap : list) {
	 		System.out.println(ap); 
	 	 }
	}

	public static void checkDosesOfVaccine() throws IOException {
		System.out.println("Type the name of the vaccine you want to check: ");
		String v_name = r.readLine();
		Vaccine vaccine = vaccineMan.getVaccine(v_name);		
		System.out.println(v_name + " has the total of: " + vaccine.getDose() + " dosis");

	}

	public static void updateConditionsOfPatient(int p_id) {

	 	 System.out.println("1. Add new conditions");
	 	 System.out.println("2. Delete existing condition");
	 	 int option;
	 	 try {
	 	 	 option = Integer.parseInt(r.readLine());
	 	 	 switch(option) {
	 	 	 case 1:{
	 	 	 	 System.out.println("How many new conditions do you have? Introduce a number ");
	 	 	 	 int number = Integer.parseInt(r.readLine());
	 	 	 	 for(int i=0; i < number; i++) {
	 	 	 	 	 System.out.println("Introduce condition name:");
	 	 	 	 	 String c_type=r.readLine();
	 	 	 	 	 Condition condition = condMan.getCondition(c_type);
	 	 	 	 	 int c_id = condition.getId();
	 	 	 	 	 condMan.updateConditionsOfPatient(p_id, c_id);
	 	 	 	 }
	 	 	 	 break;
	 	 	 }case 2:{
	 	 		 System.out.println("Which condition do you want to remove, type it's name: ");
	 	 	 	 String type = r.readLine();
	 	 	 	 Condition condition=condMan.getCondition(type);
	 	 	 	 condMan.removeConditionOfPatient(p_id, condition.getId());
	 	 	 	 System.out.println("Condition deleted! ");
	 	 	 	 break;
	 	 	 }
	 	 	 }
	 	 } catch (NumberFormatException e1) {
	 	 	 System.out.println("You did not type a number");
	 	 	 e1.printStackTrace();
	 	 } catch (IOException e1) {
	 	 	 System.out.println("I/O Exception");
	 	 	 e1.printStackTrace();
	 	 }
	 }

	public static void removePatient() {
	 	 System.out.println("Introduce the name of the patient you want to remove from the database: ");
	 	 String p_name;
	 	 try {
	 	 	 p_name = r.readLine();
	 	 	 List<Patient> patients = patientMan.searchPatientByName(p_name); 
	 	 	 System.out.println(patients); 
	 	 	 System.out.println("Please tell me which one it is, type its id:"); 
	 	 	 int p_id = Integer.parseInt(r.readLine()); 
	 	 	 directorMan.removePatient(p_id);
	 	 } catch (IOException e) {
	 	 	 System.out.println("I/O exception");
	 	 	 e.printStackTrace();
	 	 }
	}

	public static void removeDoctor() {
	 	 try {
	 	 System.out.println("Introduce the name of the doctor you want to remove from the database: ");
	 	 String d_name=r.readLine();
	 	 List<Doctor> doctors = doctorMan.searchDoctorByName(d_name); 
	 	 System.out.println(doctors); 
	 	 System.out.println("Please tell me which one it is, type its id:"); 
	 	 int d_id = Integer.parseInt(r.readLine()); 
	 	 directorMan.removeDoctor(d_id);
	 	 
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

	public static void assignConditionToVaccine() {
		try {
			System.out.println("Tell me the name of the condition: ");
			String c_name = r.readLine();
			Condition condition = condMan.getCondition(c_name);
			int c_id = condition.getId();
			System.out.println("Tell me the name of the vaccine: ");
			String v_name = r.readLine();
			Vaccine vaccine = vaccineMan.getVaccine(v_name);
			int v_id = vaccine.getId();
			directorMan.assignConditionToVaccine(c_id, v_id);
		} catch (IOException e) {
			System.out.println("I/O Excepption");
			e.printStackTrace();
		}
	}

	public static void assignVaccineToPatient() throws IOException {
		System.out.println("Tell me the name of the patient.");
		String name = r.readLine();
		List<Patient> patients = patientMan.searchPatientByName(name);
		System.out.println(patients);
		System.out.println("Select the id of the patient: ");
		int id = Integer.parseInt(r.readLine());
		System.out.println("Tell me the name of the vaccine ");
		String name_v = r.readLine();

		vaccineMan.assignVaccineToPatient(name_v, id);
	}

	public static int generateRandomInt(int bound) {
		Random random = new Random();
		return random.nextInt(bound)+1;
	}

	public static void selectAppointments(int d_id) throws IOException {
		
		List<Appointment> listAppointments = appointmentMan.searchAppointmentsByDoctor(d_id);
		
		if (listAppointments.isEmpty()) {
			System.out.println("There are no medical appointments for the moment");
			
		}else {
	 	 for(Appointment ap : listAppointments) {
	 		System.out.println(ap); 
	 	 }
		}
	}

	public static void setAppointment(int p_id) {//TODO finish	
	 	 //selects vaccine for this disease that do not have the patient conditions
	 	 try {
	 	 	 
	 	 	 Patient patient = patientMan.getPatient(p_id); 
	 	 	 System.out.println("Please, tell me the disease you want to put a vaccine of.");
	 	 	 String d_name = r.readLine(); 	 	 	 

	 	 	 Disease disease = diseaseMan.getDisease(d_name); 
	 	 	 int d_id = disease.getId();
	 	 	 Vaccine vaccine = condMan.getVaccineDependingOnCondition(d_id, p_id);
			

	 	 	 System.out.println("Please, tell me the date at which you want to set the appointment. (yyyy-MM-dd)");
	 	 	 String doa = r.readLine();
	 	 	 LocalDate doaLocalDate = LocalDate.parse(doa, formatter); 
	 	 	 Date doaDate = Date.valueOf(doaLocalDate);//we should not show the date as the amount of seconds that... so that is why we use Localdate.	 	 	 	 	 	 	 	 	 	 	 
	 	 	 
	 	 	 if(patient.getDoctor()==null) {
		 	 	 int doc_id = doctorMan.getRandomId();
		 	 	patient.setDoctor(doctorMan.getDoctorById(doc_id)); 
		 	 	 Appointment appointment = new Appointment(doaDate, patient.getDoctor(), patient, vaccine);
	 	 	 }
	 	 	 Appointment appointment = new Appointment(doaDate,patient.getDoctor(), patient, vaccine); 
	 	 	 //we need to turn it into a Date in order to store it into the db. When i create the puts it whould pass the Date. 
	 	 	 appointmentMan.insertAppointment(appointment);
	 	 	System.out.println(" Great! Remember your appointment details:");
	 	 	System.out.println(" Appointment date: " + doaDate);
	 	 	System.out.println(" Doctor:" + patient.getDoctor());
	 	 	System.out.println(" Vaccine:" + vaccine);

	 	 }catch(IOException e) {
	 	 	 System.out.println("I/O Exception");
	 	 	 e.printStackTrace(); 
	 	 }
	}

	public static void cancelAppointment(int p_id) {
	 	 try {
	 	 	 System.out.println("These are the list of your appointments: ");
	 	 	 List<Appointment> appointments = appointmentMan.searchAppointmentsByPatient(p_id);
	 	 	 for(Appointment ap : appointments) {
	 	 		System.out.println(ap); 
	 	 	 }
	 	 	 
	 	 	 System.out.println("Please, tell me the id of the appointment you want to remove: ");
	 	 	 int id = Integer.parseInt(r.readLine());
	 	 	 appointmentMan.removeAppointment(id);
	 	
	 	 }catch(IOException e) {
	 	 	 System.out.println("I/O Exception");
	 	 	 e.printStackTrace();
	 	 }
	}

	public static void patients2Xml(int id) throws IOException {
		System.out.println("Your patients in XML are:");
		List<Patient> listPatient = patientMan.searchPatientsByDoctor(id);
		Doctor doc = doctorMan.getDoctorById(id);
		doc.setPatients(listPatient);
		xmlMan.doctor2Xml(doc);
	}
	public static void insertDoctorFromXml() {
		System.out.println("Introduce the xml file's name: ");
		String name;
		try {
			name = r.readLine();
			File xml= new File("./xmls/"+name+".xml");
			System.out.println("Your patients in java are:");
			xmlMan.xml2Doctor(xml);
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		}
		
	}
	
	public static void directorMenu(String email) {

		while (true) {
			System.out.println("What do you want to do? ");
			System.out.println("1.Insert data.");
			System.out.println("2.Remove data.");
			System.out.println("3.Assign data.");
			System.out.println("0.Exit");

			int choice3;
			try {
				choice3 = Integer.parseInt(r.readLine());
				switch (choice3) {

				case 1: {
					System.out.println("1.Insert condition"); //works
					System.out.println("2.Insert vaccine");   //works
					System.out.println("3.Insert disease");   //wprks
					System.out.println("0.Exit");

					int choice = Integer.parseInt(r.readLine());
					switch (choice) {
					case 1: {
						registerCondition();
						break;
					}
					case 2: {
						registerVaccine();
						break;
					}
					case 3: {
						registerDisease();
						break;
					}
					case 0: {
						
						return;
						
					}
					}
					break; 
				}
				case 2: {
					System.out.println("1.Remove doctor"); 
					System.out.println("2.Remove vaccine"); 
					System.out.println("3.Remove patient");  
					System.out.println("0.Exit");

					int choice = Integer.parseInt(r.readLine());
					switch (choice) {
					case 1: {
						removeDoctor();
						break;
					}
					case 2: {
						removeVaccine();
						break;
					}
					case 3: {
						removePatient();
						break;
					}
					case 0: {
						return;
					}
					}
					break;
				}
				case 3: {
					System.out.println("1.Assign condition to vaccine");
					System.out.println("0.Exit");
					int choice = Integer.parseInt(r.readLine());
					switch (choice) {
					case 1: {
						assignConditionToVaccine();
						break;
					}
					case 0: {
						return;
					}
					}
					break; 
				}
				case 0: {
					return;
				}
				}
			} catch (NumberFormatException | IOException e) {
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
	 	 	 	 System.out.println("1. Check vaccines I have already put on");
	 	 	 	 System.out.println("2. Check vaccines I still have to put");
	 	 	 	 System.out.println("3. Check my appointments"); 
	 	 	 	 System.out.println("4. Set an appointment"); 
	 	 	 	 System.out.println("5. Cancel an appointment"); 
	 	 	 	 System.out.println("6. Update my conditions"); 
	 	 	 	 System.out.println("0. Exit"); 
	 	 	 	 
	 	 	 	 int option = Integer.parseInt(r.readLine()); 
			
	 	 	 	 switch(option) {
	 	 	 	 	 case 1:{
	 	 	 	 	 	 checkVaccinesAPatientHasOn(patient.getId()); 
	 	 	 	 	 	 break;
	 	 	 	 	 }case 2:{
	 	 	 	 	 	 checkVaccinesAPatientHasToPut(patient.getId());
	 	 	 	 	 	 break;
	 	 	 	 	 }
	 	 	 	 	 case 3:{
	 	 	 	 	 	 checkAppointmentsOfPatient(patient.getId()); 
	 	 	 	 	 	 break;
	 	 	 	 	 }
	 	 	 	 	 case 4:{
	 	 	 	 	 	 setAppointment(patient.getId());
	 	 	 	 	 	 break;
	 	 	 	 	 }
	 	 	 	 	 case 5:{
	 	 	 	 	 	 cancelAppointment(patient.getId());
	 	 	 	 	 	 break;
	 	 	 	 	 }case 6:{
	 	 	 	 	 	 updateConditionsOfPatient(patient.getId());
	 	 	 	 	 	 break;
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
				System.out.println("\nWelcome doctor: ");
				System.out.println("Choose an option.");
				System.out.println("1. Check vaccine supplies (all vaccines in the data)");
				System.out.println("2. Check vaccines of a patient.");
				System.out.println("3. Check all my patients");
				System.out.println("4. Check my appointments.");
				System.out.println("5. Export my patients to XML.");
				System.out.println("6. Import patients from XML.");
				System.out.println("0. Return");
				int choice = Integer.parseInt(r.readLine());
				switch (choice) {
				case 1: {
					selectVaccines();
					break;
				}
				case 2: {
					checkVaccinesPatientHasToPut(doctor.getId());
					break;
				}
				case 3: {
					selectPatients(doctor.getId());
					break;
				}
				case 4: {
					selectAppointments(doctor.getId());
					break;
				}
				case 5:{
					patients2Xml(doctor.getId());
					break;
				}
				
				case 6:{
					insertDoctorFromXml();
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

}