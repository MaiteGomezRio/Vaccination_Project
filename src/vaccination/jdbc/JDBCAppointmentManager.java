package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vaccination.ifaces.AppointmentManager;
import vaccination.pojos.Appointment;
import vaccination.pojos.Condition;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class JDBCAppointmentManager implements AppointmentManager{
	private Connection c;

	public JDBCAppointmentManager(Connection c) {
		this.c = c;
	}

	@Override
	public void insertAppointment(Appointment appointment) {
		try {	
	         String sql = "INSERT INTO Appointment (date, doctor, patient, vaccine)" + "VALUES (?, ?, ?, ? )";
	         PreparedStatement p = c.prepareStatement(sql);
			 p.setDate(1, appointment.getDate());
			 p.setInt(2, appointment.getDoctor().getId());
			 p.setInt(3, appointment.getPatient().getId());
			 p.setInt(4, appointment.getVaccine().getId());
			 p.executeUpdate();
			 p.close();
		} catch (SQLException e) {
			System.out.println("database exception");
			e.printStackTrace(); 
		}
		//TODO IMPORTANTE
	/*@Override
	public List<Appointment> searchAppointmentsByPatient(int p_id) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		try {
			String sql = "SELECT * FROM Appointment WHERE patient_id = ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, p_id);   
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				int id = rs.getInt("id");
				Date date = rs.getDate("date"); 
				int patient_id = rs.getInt("patient_id");
				int vaccine_id = rs.getInt("vaccine_id"); 
				int doctor_id = rs.getInt("doctor_id");
				
				Appointment appointment = new Appointment(id, date, Doctor, Patient, Vaccine);
				appointments.add(appointment); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return appointments; 
	}*/
	@Override
	public List<Appointment> searchAppointmentsByDoctor(int d_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAppointment(int a_id) {
		try {
			String sql = "DELETE * FROM Appointment WHERE id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, a_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Appointment> searchAppointmentsByPatient(int p_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
