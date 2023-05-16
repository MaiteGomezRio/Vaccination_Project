package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Appointment;

public interface AppointmentManager {
    public void insertAppointment(Appointment appointment); 
    public List<Appointment> checkAppointmentsOfPatient(int p_id); 
    public List<Appointment> checkAppointmentsOfDoctor(int d_id); 
    public void removeAppointment(Appointment appointment); 
    public void getAppointmentById(int id);
    //public void assignAppointmentToPatientAndVaccine()   i think this method would be the same one as insertAppointment. 
}
