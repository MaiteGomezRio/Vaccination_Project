package vaccination.ifaces;

import java.util.List;


import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	public List<Patient> searchPatientByName(String name);
	public Patient getPatientBeingAPatient(String p_id);
	public Patient getPatientBeingADoctor(String p_id);
	
	//TODO should we add a method to remove a patient ? 
	//TODO should we addd a method searchPatientsByDoctor that gives us the patients that a doctor has? 
}
