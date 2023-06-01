package vaccination.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.AppointmentManager;
import vaccination.pojos.Appointment;
import vaccination.pojos.Disease;
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
			
	         String sql = "INSERT INTO Appointment (Date, patient_id, vaccine_id, doctor_id)" + "VALUES (?, ?, ?, ? )";
	         PreparedStatement p = c.prepareStatement(sql);
	         //p.setInt(1,appointment.getId());
			 p.setDate(1, appointment.getDate());
			 p.setInt(2, appointment.getPatient().getId());
			 p.setInt(3, appointment.getVaccine().getId());
			 p.setInt(4, appointment.getDoctor().getId());
			 p.executeUpdate();
			 p.close();
		} catch (SQLException e) {
			System.out.println("database exception");
			e.printStackTrace(); 
		}

	}
	
	
	@Override
	public List<Appointment> searchAppointmentsByPatient(int p_id) {
		List<Appointment> list= new ArrayList<Appointment>();
		try {
			String sql ="SELECT ap.id, Date, patient_id, vaccine_id, doctor_id, d.name AS doctor_name, d.surname AS doctor_surname, v.name AS vaccine_name, v.dose AS vaccine_dose, di.name AS disease_name, v.disease_id AS disease_id FROM Appointment ap INNER JOIN Vaccine v ON v.id = ap.vaccine_id INNER JOIN Doctor d ON d.id = ap.doctor_id INNER JOIN Disease di ON v.disease_id = di.id WHERE patient_id = ?";
			PreparedStatement p= c.prepareStatement(sql);
			p.setInt(1, p_id);
			ResultSet rs= p.executeQuery();
			while(rs.next()) {
				
				Integer patient_id = rs.getInt("patient_id");
				 Patient patient = new Patient(patient_id);
				 Integer vaccine_id= rs.getInt("vaccine_id");
				 Vaccine vaccine= new Vaccine(vaccine_id);
				 vaccine.setName(rs.getString("vaccine_name"));
				 vaccine.setDose(rs.getInt("vaccine_dose"));
				 vaccine.setDisease(new Disease(rs.getInt("disease_id")));
				 vaccine.getDisease().setName(rs.getString("disease_name"));
				 Integer doctor_id = rs.getInt("doctor_id");
		        Doctor doctor = new Doctor(doctor_id); 
		        doctor.setName(rs.getString("doctor_name"));
		        doctor.setSurname(rs.getString("doctor_surname"));
				Date doaDate = new Date(rs.getLong("Date")); 
		      
				Appointment appointment = new Appointment(rs.getInt("id"), doaDate,doctor,patient,vaccine);
				appointment.setPatient(null);
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
	public List<Appointment> searchAppointmentsByDoctor(int d_id) {
		List<Appointment> list= new ArrayList<Appointment>();
		try {
			String sql ="SELECT id_document, ap.doctor_id, ap.Date, ap.id, ap.patient_id, p.name AS patient_name, p.surname AS patient_surname, vaccine_id, v.name AS vaccine_name, v.dose AS vaccine_dose, di.name AS disease_name, v.disease_id AS disease_id FROM Appointment ap INNER JOIN Vaccine v ON v.id = ap.vaccine_id INNER JOIN Patient p ON p.id = ap.patient_id INNER JOIN Disease di ON v.disease_id = di.id WHERE ap.doctor_id = ?";
			PreparedStatement p= c.prepareStatement(sql);
			p.setInt(1, d_id);
			ResultSet rs= p.executeQuery();
			while(rs.next()) {
				
				Integer patient_id = rs.getInt("patient_id");
				 Patient patient = new Patient(patient_id);
				 patient.setName(rs.getString("patient_name"));
				 patient.setSurname(rs.getString("patient_surname"));
				 patient.setId_document(rs.getString("id_document"));
				 Integer vaccine_id= rs.getInt("vaccine_id");
				 Vaccine vaccine= new Vaccine(vaccine_id);
				 vaccine.setName(rs.getString("vaccine_name"));
				 vaccine.setDose(rs.getInt("vaccine_dose"));
				 vaccine.setDisease(new Disease(rs.getInt("disease_id")));
				 vaccine.getDisease().setName(rs.getString("disease_name"));
				 Integer doctor_id = rs.getInt("doctor_id");
		        Doctor doctor = new Doctor(doctor_id); 
				Date doaDate = new Date(rs.getLong("Date")); 
		      
				Appointment appointment = new Appointment(rs.getInt("id"), doaDate,doctor,patient,vaccine);
				appointment.setDoctor(null);
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
	public void removeAppointment(int a_id) {
		try {
			String sql = "DELETE FROM Appointment WHERE id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, a_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}

	
}
