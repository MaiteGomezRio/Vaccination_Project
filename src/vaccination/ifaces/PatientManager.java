package vaccination.ifaces;



import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	public void assignDiseaseToPatient(int p_id, int d_id);
	public List<Patient> searchPatientByDoctor(int d_id);
	public Patient getPatientInfo(int p_id);
	public List<Patient> searchPatientByName(String name);
	
}
 