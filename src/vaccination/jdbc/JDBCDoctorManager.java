package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.DoctorManager;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient; 

public class JDBCDoctorManager implements DoctorManager {
	
	private Connection c;

	public JDBCDoctorManager(Connection c) {
		this.c = c;

	}
	@Override
	public void insertDoctor(Doctor doctor) {

		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO Doctor (id, name, surname) VALUES ('" + doctor.getId() + "', "
					+ doctor.getName() + ", '" + doctor.getSurname() + "')";
			s.executeUpdate(sql);
			s.close();
		} catch (SQLException e) {
			System.out.println("Database exception.");
			e.printStackTrace();
		}

	}
	@Override
	public List<Doctor> searchDoctorByName(String name){
		List<Doctor> list = new ArrayList<Doctor>(); 
		try {
			String sql = "SELECT * FROM Doctor WHERE name LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, "%"+name+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				String id = rs.getString("id"); 
				String n = rs.getString("name"); 
				String surname = rs.getString("surname");
				Doctor d = new Doctor(id, n, surname); 
				//I DO NOT HAVE THE LIST OF PATIENTS 
				list.add(d); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list; 
	}
	@Override
	public Doctor getDoctor(String p_id) {
		try {
		String sql = "SELECT * FROM Doctor WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setString(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id = rs.getString("p_id"); 
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        Doctor doctor = new Doctor(id, name, surname); 
        return doctor; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
}

