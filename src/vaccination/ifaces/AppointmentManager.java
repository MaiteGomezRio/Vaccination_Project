package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.Appointment;

public interface AppointmentManager {
    public void insertAppointment(Appointment appointment); 
    public List<Appointment> searchAppointmentsByPatient(int p_id); 
    public List<Appointment> searchAppointmentsByDoctor(int d_id); 
    public void removeAppointment(int a_id); 
}
