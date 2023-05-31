package vaccination.ifaces;



import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient, int d_id);
	public List<Patient> searchPatientsByDoctor(int d_id);
	public Patient getPatient(int p_id);
	public List<Patient> searchPatientByName(String name);
	public Patient getPatientByEmail(String email); 
	
}
 