package vaccination.ifaces;



import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	public void assignDiseaseToPatient(int p_id, int d_id);
	public void removePatient(Patient patient);
	public List<Patient> searchPatientByDoctor(int d_id);
	public Patient getPatientInfoBeingAPatient(int p_id);
	public Patient getPatientBeingADoctor(int p_id);
	
}
 