package vaccination.ifaces;

import java.util.List;


import vaccination.pojos.Vaccine;

public interface VaccineManager {

	public List<Vaccine> searchVaccinesByPatient(int p_id);
	public List<Vaccine> searchVaccinesByDisease(int d_id);
	public Vaccine getVaccine(String name);//TODO not used so far
	public void assignVaccineToPatient (String v_name, int p_id);
	public void assignVaccineToDisease(int v_id, int d_id);
	public List<Vaccine> getAllVaccines();

}
