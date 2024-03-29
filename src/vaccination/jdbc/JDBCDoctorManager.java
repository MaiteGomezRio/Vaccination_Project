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

public class JDBCDoctorManager implements DoctorManager {
	
	private Connection c;

	public JDBCDoctorManager(Connection c) {
		this.c = c;

	}
	@Override
	public void insertDoctor(Doctor doctor) {

		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO Doctor (id_document, name, surname, email) VALUES ('" + doctor.getId_document() + "', '"
					+ doctor.getName() + "', '" + doctor.getSurname() + "','"+doctor.getEmail()+"')";
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
				int id = rs.getInt("id");
				String id_document = rs.getString("id_document");
				String n = rs.getString("name"); 
				String surname= rs.getString("surname");
				Doctor doctor = new Doctor(id, id_document,n, surname); 
				list.add(doctor); 
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
        int id = rs.getInt("p_id"); 
        String id_document = rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        Doctor doctor = new Doctor(id, id_document,name, surname); 
        rs.close();
	    p.close();
        return doctor; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
	public Doctor getDoctorById(int id) {
		try {
		String sql = "SELECT * FROM Doctor WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setInt(1, id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();   
        Doctor doctor = new Doctor(id, rs.getString("id_document"),rs.getString("name"), rs.getString("surname"), rs.getString("email")); 
        rs.close();
        p.close(); 
        return doctor; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
	@Override
	public Doctor getDoctorByEmail(String email) {
		try {
		String sql = "SELECT * FROM Doctor WHERE email LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setString(1, email); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  
        Integer id = rs.getInt("id"); 
        String id_document=rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        Doctor doctor = new Doctor(id, id_document,name, surname, email); 
        rs.close();
        p.close(); 
        return doctor; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}

	@Override
	public int countNumberOfDoctors() {
		try {
			int val = -1;
			String sql = "SELECT COUNT(*) FROM Doctor"; 
			PreparedStatement p = c.prepareStatement(sql); 
			ResultSet rs = p.executeQuery(); 
			rs.next();
			val = rs.getInt(1); //the 1 means that it counts the number of rows that are in the first column of the table (doctor id) 
	        
			rs.close();
			p.close();
			return val;
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getRandomId() {
		try {
			String sql = "SELECT id FROM Doctor ORDER BY RANDOM() LIMIT1"; 
			PreparedStatement p = c.prepareStatement(sql);
			ResultSet rs = p.executeQuery();
			rs.next(); 
			int d_id = rs.getInt("id");
			rs.close();
			p.close(); 
			return d_id; 
		}catch(SQLException e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
		return 0; 
	}
}

