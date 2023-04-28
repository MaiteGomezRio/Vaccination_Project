package vaccination.ifaces;

import java.util.List;


import vaccination.pojos.Vaccine;

public interface VaccineManager {
	public void insertVaccine(Vaccine vaccine);
	public void removeVaccine(String v_name);
	public List<Vaccine> searchVaccinesByPatient(String p_id);
	public Vaccine getVaccine(String name);
	public void assignVaccineToPatient (String v_name, String p_id);

}
