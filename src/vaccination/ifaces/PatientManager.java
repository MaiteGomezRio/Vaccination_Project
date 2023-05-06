package vaccination.ifaces;



import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	//new 3 methods, we need to create several types of conditions and diseases
	public void insertCondition(Condition condition);
	public void insertDisease(Disease disease);
	public void assignDiseaseToPatient(int p_id, int d_id);
	public void removePatient(Patient patient);
	public List<Patient> searchPatientByName(String name);
	public Patient getPatientBeingAPatient(int p_id);
	public Patient getPatientBeingADoctor(int p_id);
	public Condition getCondition(String c_type);

	//TODO should we add a method to remove a patient ? 
	//TODO should we add a method searchPatientsByDoctor that gives us the patients that a doctor has? 
	public Disease getDisease(String d_name);
}
