package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Condition;
import vaccination.pojos.Vaccine;

public interface ConditionManager {
	public List<Condition> checkConditionsOfAVaccine(int v_id);
	public Condition getCondition(String type); 
	public void updateConditionsOfPatient(int p_id, int c_id);
	public Vaccine getVaccineDependingOnCondition(int d_id, int p_id, int c_id);
	public void removeConditionOfPatient(int p_id, int c_id);
	public List<Condition> getConditionsOfPatient(int p_id);
}
