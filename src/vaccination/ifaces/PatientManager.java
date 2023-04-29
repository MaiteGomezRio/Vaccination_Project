package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;

public interface PatientManager {

	public void insertPatient(Patient patient);
	public Patient searchPatientByName(String name);
	public Patient getPatientBeingAPatient(String p_id);
	public Patient getPatientBeingADoctor(String p_id);
	public Condition getCondition(String c_name);
	//TODO should we add a method to remove a patient ? 
	//TODO should we add a method searchPatientsByDoctor that gives us the patients that a doctor has? 
	public Disease getDisease(String d_type);
}
