package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Disease;

public interface DiseaseManager {
	
	public List<Disease> searchDiseasesByPatient(int p_id);
	public List<Disease> searchDiseaseByVaccine(int v_id); 	
	public List<Disease> searchDiseaseByName(String d_name);
	public Disease getDisease(String name);
}
