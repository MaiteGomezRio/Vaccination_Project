package vaccination.ifaces;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Vaccine;

public interface DirectorManager {
	
	public void insertVaccine(Vaccine vaccine);
	public void insertCondition(Condition condition);
	public void insertDisease(Disease disease);
	public void removePatient(String p_name);
	public void removeDoctor(String d_name);
	public void removeVaccine(String v_name);
	public void assignConditionToVaccine(String c_name, String v_name);	
	public void assignDoctorToPatient(int d_id, int p_id);
	
}
