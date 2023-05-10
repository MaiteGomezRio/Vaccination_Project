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
	public void assignConditionToVacccine(int c_id, int v_id);
	public void assignDoctorToPatient(int c_id, int v_id);

}
