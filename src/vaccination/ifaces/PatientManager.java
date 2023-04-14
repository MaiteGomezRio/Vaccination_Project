package vaccination.ifaces;

import java.util.List;


import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	public List<Patient> searchPatientByID(String p_id);
	public Patient getPatient(String p_id);
	
}
