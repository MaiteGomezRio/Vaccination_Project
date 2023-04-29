package vaccination.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

public class Menu2 {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

	private static DoctorManager doctorMan;
	private static PatientManager patientMan;
	private static VaccineManager vaccineMan;

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
			System.out.println("Name:");
			String name = r.readLine();
			System.out.println("Surname:");
			String surname = r.readLine();
			System.out.println("ID:");
			String id = r.readLine();
			// System.out.println("Password:");
			// String password = r.readLine();

			Doctor doctor = new Doctor(id, name, surname);
			doctorMan.insertDoctor(doctor);
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
		System.out.println("Name: ");
		String name = r.readLine();
		System.out.println("Surname: ");
		String surname = r.readLine();
		System.out.println("ID: ");
		String id = r.readLine();
		String d_name = r.readLine();
		Disease disease = patientMan.getDisease(d_name);
		String c_name = r.readLine();
		Condition condition = patientMan.getCondition(c_name);
		// the database associates randomly the doctor to the patients???
		Patient patient = new Patient(id, name, surname, disease, condition);
		patientMan.insertPatient(patient);
	}

	public static void registerVaccine() throws IOException {
		// in this method we are registering a vaccine and linking it to its doctor.

		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		Vaccine vaccine = new Vaccine(name, dose);
		// we are only registering a vaccine, when we associate it then it it linked to
		// a patient but
		// until then we don't need the patient
		vaccineMan.insertVaccine(vaccine);
	}

	// used when you want to put an specific vaccine to a patient
	public static void selectVaccines() throws IOException {
		System.out.println("Please, tell me the vaccine's name: ");
		String name = r.readLine();
		List<Vaccine> listVaccines = vaccineMan.searchVaccineByName(name);
		System.out.println(listVaccines);
		System.out.println("Choose which one it is, type its ID: ");
		String id = r.readLine();
		doctorMenu(id);
	}

	public static void checkVaccinesOfPatient()throws IOException {  //this return the list of vaccines of a patient
	
		System.out.println("Introduce your name: ");
		String patient_name=r.readLine();
		Patient patient=patientMan.searchPatientByName(patient_name);
   	 	System.out.println("The vaccines of the patient are: ");
   	 	List<Vaccine> vaccines = vaccineMan.searchVaccinesByPatient(patient.getId()); 
   	 	System.out.println(vaccines);
   	 	System.out.println("If you want to modify something of a specific vaccine, type it's name: "); 
   	 	System.out.println("If not, press enter");
   	 	String v_name = r.readLine();
   	 	if(r.readLine()!=null) {
   	 		vaccineMenu(v_name); 
   	 	else {
   		 return;
   	 	}
    }
	}

	public static void removePatient(String p_name) {
		patientMan.removePatient(p_name);// TODO create remove method in patient
	}

	public static void removeDoctor(String d_name) {
		doctorMan.removeDoctor(d_name);// TODO create remove method in doctor
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
				System.out.println("0. Return");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerVaccine(); // the id we pass is the patient's id who is going to put that vaccine
					break;
				}
				case 2: {
					selectVaccines();// TODO create a method that returns the list of vaccines in the databae
					break;
				}
				case 3: {
					checkVaccinesOfPatient();
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
