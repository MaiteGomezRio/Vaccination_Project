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
		diseaseMan = new JDBCDiseaseManager(conMan.getConnection());
		condMan = new JDBCConditionManager(conMan.getConnection());
		appointmentMan = new JDBCAppointmentManager(conMan.getConnection());

		userMan = new JPAUserManager();
		int choice = -1;
		do {
			try {
				System.out.println("\nWelcome to the Vaccination app!");
				System.out.println("What do you want to do? :");
				System.out.println("1. Register");
				System.out.println("2. Log in");
				System.out.println("0. Exit");
				choice = Utilities.readInteger();
				switch (choice) {
				case 1: {
					registerMenu();
					break;
				}
				case 2: {
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
				System.out.println("You didn't type a valid option! Try an integer number between 0 and 2");
				choice = -1;
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		} while (!Utilities.validMenu(2, choice));
	}

	public static void registerMenu() {
		int choice = -1;
		do {

			System.out.println("1. Register as a doctor");
			System.out.println("2. Register as a patient");
			System.out.println("0. Exit");

			try {
				choice = Utilities.readInteger();
				switch (choice) {
				case 1: {
					registerDoctor();
					break;
				}
				case 2: {
					if (doctorMan.countNumberOfDoctors() == 0) {
						System.out.println("There are no doctors available. Come back later!\n");
					} else {
						registerPatient();
					}

					break;
				}
				case 0: {
					return;
				}
				}
			
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		} while (!Utilities.validMenu(2, choice));

	}

	public static void login() throws IOException {
		while (true) {
			System.out.println("\n Type 0 to go back to menu\n");

			System.out.println("Username: ");
			String username = Utilities.readString();
			if (username.equals("0")) {
				System.out.println("\n");
				break;
			}
			System.out.println("Password: ");
			String password = Utilities.readString();
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
		

			System.out.println("Please, input the doctor's data:");
			System.out.println("Id_document:");
			String id_document = Utilities.readString();
			System.out.println("Name:");
			String name = Utilities.readString();
			System.out.println("Surname:");
			String surname = Utilities.readString();
			String email="";
			do {
			System.out.println("Email: ");
			email = Utilities.readString();
			}while(Utilities.isValidEmail(email));
			String username = id_document;
			System.out.println("Password:");
			String password = Utilities.readString();
			System.out.println("Your username is: " + id_document + "\n");
			Doctor doctor = new Doctor(id_document, name, surname, email);
			doctorMan.insertDoctor(doctor);
			User user = new User(username, password, email);
			userMan.register(user);
			Role role = userMan.getRole("doctor");
			userMan.assignRole(user, role);

			System.out.println("You have registered as a doctor!");
			doctorMenu(user.getEmail());
		
	}

	public static void registerCondition() {
		
			System.out.println("Please, input the condition's name: ");
			String type = Utilities.readString();
			Condition condition = new Condition(type);
			directorMan.insertCondition(condition);
		
	}

	public static void registerDisease() {
		
			System.out.println("Please, tell me the name of the disease: ");
			String name = Utilities.readString();
			Disease disease = new Disease(name);
			directorMan.insertDisease(disease);
		
	}

	public static void registerPatient() throws IOException {
		System.out.println("Please, introduce the following information: ");

		System.out.println("ID document: ");
		String id_document = Utilities.readString();
		System.out.println("Name: ");
		String name = Utilities.readString();
		System.out.println("Surname: ");
		String surname = Utilities.readString();
		String email = "";
		do {
			System.out.println("email: ");
			email = Utilities.readString();
		} while (!Utilities.isValidEmail(email));
		System.out.println("password: ");
		String password = Utilities.readString();
		String username = id_document;
		System.out.println("Your username is: " + id_document + "\n");

		int bound = doctorMan.countNumberOfDoctors();
		System.out.println(bound);
		int doc_id = generateRandomInt(bound);
		System.out.println("doc id " + doc_id);
		Doctor doctor = doctorMan.getDoctorById(doc_id);
		System.out.println("doctor: " + doctor.toString());
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
		String name = Utilities.readString();
		System.out.println("doses: ");
		Integer dose = Utilities.readInteger();
		System.out.println("disease: ");
		String dis_name = Utilities.readString();
		Disease disease = diseaseMan.getDisease(dis_name);
		// int d_id = disease.getId();
		Vaccine vaccine = new Vaccine(name, dose, disease);
		directorMan.insertVaccine(vaccine);
		/*
		 * String c_name; int v_id = vaccine.getId(); do{ System.out.
		 * println("Tell me the name of the condition, if there are no more conditions press 0"
		 * ); c_name=r.readLine(); Condition condition = condMan.getCondition(c_name);
		 * int c_id = condition.getId(); directorMan.assignConditionToVaccine(c_id,
		 * v_id); }while(Integer.parseInt(c_name)!=0);
		 */
		System.out.println("Vaccine " + name + " registered");
	}

	public static void selectVaccines() throws IOException {
		List<Vaccine> listVaccines = vaccineMan.getAllVaccines();
		if (listVaccines.isEmpty()) {
			System.out.println("There is no vaccines registered in the database yet");
		} else {
			for (Vaccine vaccine : listVaccines) {
				System.out.print(vaccine);
				System.out.println(", " + diseaseMan.searchDiseaseById(vaccine.getDiseaseId()));
			}
		}

	}

	public static void selectPatients(int d_id) throws IOException {
		List<Patient> listPatients = patientMan.searchPatientsByDoctor(d_id);
		if (listPatients.isEmpty()) {
			System.out.println("There is no patients registered in the database yet");
		} else {
			for (Patient patient : listPatients) {
				System.out.println(patient);
			}
		}
	}

	public static void checkVaccinesOfDisease() throws IOException {
		System.out.println("Type the name of the disease you want to check: ");
		String d_name = Utilities.readString();

		Disease disease = new Disease(d_name);
		List<Vaccine> list = vaccineMan.searchVaccinesByDisease(disease.getId());

		if (list.isEmpty()) {
			System.out.println("There is no vaccines registered in the database yet");
		} else {

			for (Vaccine vaccine : list) {
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

	public static void checkVaccinesPatientHasToPut(int d_id) {// for doctor
		List<Patient> list = new ArrayList<>();
		List<Patient> listPatients = new ArrayList<>();
		
			listPatients = patientMan.searchPatientsByDoctor(d_id);
			if (listPatients.isEmpty()) {
				System.out.println("You don't have any patients!");
			} else {
				System.out.println("Introduce the name of the patient you want to check: ");
				String name = Utilities.readString();
				list = patientMan.searchPatientByName(name);
				if (list.isEmpty()) {
					System.out.println("You don't have any patients with that name. Please enter a valid name: ");
					System.out.println(list);
				} else {
					System.out.println("Tell me which one it is, type it's Id");
					int p_id = Utilities.readInteger();
					checkVaccinesAPatientHasToPut(p_id);
				}
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
		for (Appointment ap : list) {
			System.out.println(ap);
		}
	}

	public static void checkDosesOfVaccine() throws IOException {
		System.out.println("Type the name of the vaccine you want to check: ");
		String v_name = Utilities.readString();
		Vaccine vaccine = vaccineMan.getVaccine(v_name);
		System.out.println(v_name + " has the total of: " + vaccine.getDose() + " dosis");

	}

	public static void updateConditionsOfPatient(int p_id) {

		System.out.println("1. Add new conditions");
		System.out.println("2. Delete existing condition");
		int option=-1;
		do {
		try {
			option = Utilities.readInteger();
			switch (option) {
			case 1: {
				System.out.println("How many new conditions do you have? Introduce a number ");
				int number = Utilities.readInteger();
				for (int i = 0; i < number; i++) {
					System.out.println("Introduce condition name:");
					String c_type = Utilities.readString();
					Condition condition = condMan.getCondition(c_type);
					int c_id = condition.getId();
					condMan.updateConditionsOfPatient(p_id, c_id);
				}
				break;
			}
			case 2: {
				System.out.println("Which condition do you want to remove, type it's name: ");
				String type = Utilities.readString();
				Condition condition = condMan.getCondition(type);
				condMan.removeConditionOfPatient(p_id, condition.getId());
				System.out.println("Condition deleted! ");
				break;
			}
			}
		} catch (NumberFormatException e1) {
			System.out.println("You did not type a valid option. Try am integer number between 1 and 2");
			option=-1;
		}
		}while(Utilities.validMenu(2, option));
	}

	public static void removePatient() {
		System.out.println("Introduce the name of the patient you want to remove from the database: ");
		String p_name;		
			p_name = Utilities.readString();
			List<Patient> patients = patientMan.searchPatientByName(p_name);
			System.out.println(patients);
			System.out.println("Please tell me which one it is, type its id:");
			int p_id = Utilities.readInteger();
			directorMan.removePatient(p_id);
		
	}

	public static void removeDoctor() {
		
			System.out.println("Introduce the name of the doctor you want to remove from the database: ");
			String d_name =Utilities.readString();
			List<Doctor> doctors = doctorMan.searchDoctorByName(d_name);
			System.out.println(doctors);
			System.out.println("Please tell me which one it is, type its id:");
			int d_id = Utilities.readInteger();
			directorMan.removeDoctor(d_id);

		
	}

	public static void removeVaccine() {
		
			System.out.println("Introduce the name of the vaccine you want to remove from the database: ");
			String v_name = Utilities.readString();
			directorMan.removeVaccine(v_name);
		
	}

	public static void assignConditionToVaccine() {
		
			System.out.println("Tell me the name of the condition: ");
			String c_name = Utilities.readString();
			Condition condition = condMan.getCondition(c_name);
			int c_id = condition.getId();
			System.out.println("Tell me the name of the vaccine: ");
			String v_name = Utilities.readString();
			Vaccine vaccine = vaccineMan.getVaccine(v_name);
			int v_id = vaccine.getId();
			directorMan.assignConditionToVaccine(c_id, v_id);
		
	}

	public static void assignVaccineToPatient() throws IOException {
		System.out.println("Tell me the name of the patient.");
		String name =Utilities.readString();
		List<Patient> patients = patientMan.searchPatientByName(name);
		System.out.println(patients);
		System.out.println("Select the id of the patient: ");
		int id = Utilities.readInteger();
		System.out.println("Tell me the name of the vaccine ");
		String name_v = Utilities.readString();

		vaccineMan.assignVaccineToPatient(name_v, id);
	}

	public static int generateRandomInt(int bound) {
		Random random = new Random();
		return random.nextInt(bound) + 1;
	}

	public static void selectAppointments(int d_id) throws IOException {

		List<Appointment> listAppointments = appointmentMan.searchAppointmentsByDoctor(d_id);

		if (listAppointments.isEmpty()) {
			System.out.println("There are no medical appointments for the moment");

		} else {
			for (Appointment ap : listAppointments) {
				System.out.println(ap);
			}
		}
	}

	public static void setAppointment(int p_id) {
		// selects vaccine for this disease that do not have the patient conditions
		

			Patient patient = patientMan.getPatient(p_id);
			System.out.println("Please, tell me the disease you want to put a vaccine of.");
			String d_name = Utilities.readString();

			Disease disease = diseaseMan.getDisease(d_name);
			int d_id = disease.getId();
			Vaccine vaccine = condMan.getVaccineDependingOnCondition(d_id, p_id);

			System.out.println("Please, tell me the date at which you want to set the appointment. (yyyy-MM-dd)");
			String doa = Utilities.readString();
			LocalDate doaLocalDate = LocalDate.parse(doa, formatter);
			Date doaDate = Date.valueOf(doaLocalDate);// we should not show the date as the amount of seconds that... so
														// that is why we use Localdate.
			Appointment appointment =null;
			if (patient.getDoctor() == null) {
				int doc_id = doctorMan.getRandomId();
				patient.setDoctor(doctorMan.getDoctorById(doc_id));
				
				 appointment = new Appointment(doaDate, patient.getDoctor(), patient, vaccine);
			}else { 
			 appointment = new Appointment(doaDate, patient.getDoctor(), patient, vaccine);
			}
			// we need to turn it into a Date in order to store it into the db. When i
			// create the puts it whould pass the Date.
			appointmentMan.insertAppointment(appointment);
			System.out.println(" Great! Remember your appointment details:");
			System.out.println(" Appointment date: " + doaDate);
			System.out.println(" Doctor:" + patient.getDoctor());
			System.out.println(" Vaccine:" + vaccine);

		
	}


	public static void cancelAppointment(int p_id) {
		
			System.out.println("These are the list of your appointments: ");
			List<Appointment> appointments = appointmentMan.searchAppointmentsByPatient(p_id);
			for (Appointment ap : appointments) {
				System.out.println(ap);
			}

			System.out.println("Please, tell me the id of the appointment you want to remove: ");
			int id = Utilities.readInteger();
			appointmentMan.removeAppointment(id);
		
	}

	public static void patients2Xml(int id) throws IOException {
		System.out.println("Your patients in XML are:");
		List<Patient> listPatient = patientMan.searchPatientsByDoctor(id);
		Doctor doc = doctorMan.getDoctorById(id);
		doc.setPatients(listPatient);
		xmlMan.doctor2Xml(doc);
	}

	public static void insertpatientsFromXml() {

	}

	public static void directorMenu(String email) {

		int choice3 = -1;
		do {
			System.out.println("What do you want to do? ");
			System.out.println("1.Insert data.");
			System.out.println("2.Remove data.");
			System.out.println("3.Assign data.");
			System.out.println("0.Exit");

			try {
				choice3 = Utilities.readInteger();
				switch (choice3) {

				case 1: {
					int choice=-1;
				
					do {										
					System.out.println("1.Insert condition");
					System.out.println("2.Insert vaccine");
					System.out.println("3.Insert disease");
					System.out.println("0.Exit");

					 choice = Utilities.readInteger();
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
					while(!Utilities.validMenu(3, choice));
				}
				case 2: {
					
					int choice=-1;
					do {
					System.out.println("1.Remove doctor");
					System.out.println("2.Remove vaccine");
					System.out.println("3.Remove patient");
					System.out.println("0.Exit");

					choice = Utilities.readInteger();
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
					default: break;
					}
									
				}while(!Utilities.validMenu(3, choice));
				}
				case 3: {
					int choice=-1;
					do {
					System.out.println("1.Assign condition to vaccine");
					System.out.println("0.Exit");
					 choice = Utilities.readInteger();
					switch (choice) {
					case 1: {
						assignConditionToVaccine();
						break;
					}
					case 0: {
						return;
					}					
					default:break;				
					}
					
					
				}while(!Utilities.validMenu(1, choice));
				}
				default: break;
				}
				
				
			} catch (IOException e) {
				System.out.println("You did not type a valid option, try an integer number between 0 and 3");
				choice3=-1;
			}
		

			
	}while (!Utilities.validMenu(3, choice3));
	}
	

	public static void patientMenu(String email) {
		Patient patient = patientMan.getPatientByEmail(email);
		int option = -1;
		do {
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

				 option = Utilities.readInteger();

				switch (option) {
				case 1: {
					checkVaccinesAPatientHasOn(patient.getId());
					break;
				}
				case 2: {
					checkVaccinesAPatientHasToPut(patient.getId());
					break;
				}
				case 3: {
					checkAppointmentsOfPatient(patient.getId());
					break;
				}
				case 4: {
					setAppointment(patient.getId());
					break;
				}
				case 5: {
					cancelAppointment(patient.getId());
					break;
				}
				case 6: {
					updateConditionsOfPatient(patient.getId());
					break;
				}
				case 0: {
					return;
				}
				}
			
			} catch (NumberFormatException e) {
				System.out.println("You did not type a number");
				option=-1;
			}
		} while (!Utilities.validMenu(6, option));
	}

	private static void doctorMenu(String email) {
		int choice = -1;
		Doctor doctor = doctorMan.getDoctorByEmail(email);
		do {

			try {
				System.out.println("\nWelcome doctor: ");
				System.out.println("Choose an option.");
				System.out.println("1. Check vaccine supplies (all vaccines in the data)");
				System.out.println("2. Check vaccines of a patient.");
				System.out.println("3. Check all my patients");
				System.out.println("4. Check my appointments.");
				System.out.println("5. Export my patients to XML.");
				System.out.println("6. Import from XML.");
				System.out.println("0. Return");
				choice =  Utilities.readInteger();
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
				case 5: {
					patients2Xml(doctor.getId());
					break;
				}

				case 6: {
					insertpatientsFromXml();
					break;
				}
				case 0: {

					return;

				}
				}
<<<<<<< HEAD
				
=======

>>>>>>> branch 'master' of https://github.com/MaiteGomezRio/Vaccination_Project
			} catch (IOException e) {
				System.out.println("I/O exception");
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("You did not type a valid option. Try an integer number between 0 and 6");
				choice=-1;

			}

		} while (!Utilities.validMenu(6, choice));
	}

}