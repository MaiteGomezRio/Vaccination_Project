package vaccination.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import vaccination.ifaces.AppointmentManager;
import vaccination.pojos.Appointment;

public class JDBCAppointmentManager implements AppointmentManager{
	private Connection c;

	public JDBCAppointmentManager(Connection c) {
		this.c = c;
	}

	@Override
	public void insertAppointment(Appointment appointment) {
		//TODO
	}
	
	@Override
	public List<Appointment> checkAppointmentsOfPatient(int p_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> checkAppointmentsOfDoctor(int d_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		
	}
}
