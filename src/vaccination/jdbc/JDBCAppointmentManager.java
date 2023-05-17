package vaccination.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.AppointmentManager;
import vaccination.pojos.Appointment;
import vaccination.pojos.Condition;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class JDBCAppointmentManager implements AppointmentManager{
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Connection c;

	public JDBCAppointmentManager(Connection c) {
		this.c = c;
	}

	@Override
	public void insertAppointment(Appointment appointment) {
		try {	
			String sql = "INSERT INTO Appintment (date, patient, vaccine, doctor)" + "VALUES (?,?,?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setDate(1, appointment.getDate());
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
		List<Appointment> list= new ArrayList<Appointment>();
		try {
			String sql ="SELECT * FROM Appointment WHERE p_id = ?";
			PreparedStatement p= c.prepareStatement(sql);
			p.setInt(1, p_id);
			ResultSet rs= p.executeQuery();
			while(rs.next()) {
				
				Integer patient_id = rs.getInt("patient_id");
				 Patient patient = new Patient(patient_id);
				 Integer vaccine_id= rs.getInt("vaccine_id");
				 Vaccine vaccine= new Vaccine(vaccine_id);
				 Integer doctor_id = rs.getInt("doctor_id");
		        Doctor doctor = new Doctor(doctor_id); 
		        String doa = rs.getString("Date");
				LocalDate doaLocalDate = LocalDate.parse(doa, formatter);       
				Date doaDate = Date.valueOf(doaLocalDate); 
		      
				Appointment appointment = new Appointment(doaDate,patient,vaccine,doctor);
				list.add(appointment); 
			}
		}
		catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
							
		return list; 
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
