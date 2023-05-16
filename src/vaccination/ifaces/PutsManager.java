package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Puts;

public interface PutsManager {
    public void insertAppointment(Puts puts); 
    public List<Puts> checkAppointmentsOfPatient(int p_id); 
    public List<Puts> checkAppointmentsOfDoctor(int d_id); 
    public void removeAppointment(Puts puts); 
    //public void assignAppointmentToPatientAndVaccine()   i think this method would be the same one as insertAppointment. 
}
