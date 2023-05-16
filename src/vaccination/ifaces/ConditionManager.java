package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Condition;

public interface ConditionManager {
	public List<Condition> checkConditionsOfAVaccine(int v_id);
	public Condition getCondition(String type); 
	public void updateConditionsOfPatient(int p_id, int c_id);
}
