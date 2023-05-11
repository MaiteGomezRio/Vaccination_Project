package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Doctor;

public interface DoctorManager {
	public void insertDoctor(Doctor doctor);
	public List<Doctor> searchDoctorByName(String name);
	public Doctor getDoctor(String d_id);

}
