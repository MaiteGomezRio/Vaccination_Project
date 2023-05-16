package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		try {	
			String sql = "INSERT INTO Appintment (date, patient, vaccine, doctor)" + "VALUES (?,?,?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setLoc(1, appointment.getDate());
			p.setString(2,appointment.getPatient().getName());
			p.setString(3, appointment.getVaccine().getName());	
			p.setString(4, appointment.getDoctor().getName());
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database exception");
			e.printStackTrace();
		}
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
