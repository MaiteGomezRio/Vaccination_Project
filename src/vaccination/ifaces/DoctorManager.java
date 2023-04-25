package vaccination.ifaces;

import java.sql.Connection;
import java.util.List;

import vaccination.pojos.Doctor;



public interface DoctorManager {
	public void insertDoctor(Doctor doctor);
	public List<Doctor> searchDoctorByName(String name);
	public Doctor getDoctor(String d_id);

	//TODO should we add a method to remove a doctor?
}
