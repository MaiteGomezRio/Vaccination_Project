package vaccination.ifaces;

import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Vaccine;

public interface DirectorManager {
	
	public void insertVaccine(Vaccine vaccine);
	public void insertCondition(Condition condition);
	public void insertDisease(Disease disease);
	public void removePatient(int p_id);
	public void removeDoctor(int d_id);
	public void removeVaccine(String v_name);
	public void assignConditionToVaccine(int c_id, int v_id);	
	public void assignDoctorToPatient(int d_id, int p_id);
	public void assignDiseaseToVaccine(int d_id, int v_id); 
}
