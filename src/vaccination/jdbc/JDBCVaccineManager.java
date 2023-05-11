package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.VaccineManager;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class JDBCVaccineManager implements VaccineManager {

	private Connection c;

	public JDBCVaccineManager(Connection c) {
		this.c = c;

	}

	@Override
	public List<Vaccine> searchVaccinesByPatient(int p_id){
		List<Vaccine> list = new ArrayList<Vaccine>();
		try {
			String sql = "SELECT vaccine_id FROM Patient_Vaccine WHERE p_id LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, p_id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Integer dose = rs.getInt("dose");
				Vaccine v = new Vaccine(name, dose);
				list.add(v);
			}
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Vaccine getVaccine(String v_name) {
		try {
			String sql = "SELECT * FROM Vaccine WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, v_name);
			ResultSet rs = p.executeQuery();
			rs.next();
			String name = rs.getString("name");
			Integer dose = rs.getInt("dose");
			Vaccine v = new Vaccine(name, dose);
			return v;
		
			} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void assignVaccineToPatient(int v_id, int p_id) {
		try {
			String sql = "INSERT into Patient_Vaccine(v_id, p_id) WHERE VALUES (?,?)"; 																			// vaccine
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, v_id);
			p.setInt(2, p_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}
	
	@Override 
	public void assignVaccineToDisease(int v_id,int d_id) {
		try {
			String sql = "INSERT into Disease_Vaccine(d_id, v_id) WHERE VALUES (?,?)"; 																			// vaccine
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, d_id);
			p.setInt(2, v_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Vaccine> getAllVaccines(){
		List<Vaccine> list = new ArrayList<Vaccine>(); 
		try {
		   String sql = "SELECT * FROM Vaccine"; 
		   PreparedStatement p = c.prepareStatement(sql); 
		   ResultSet rs = p.executeQuery(); 
			while (rs.next()) {
				String name = rs.getString("name");
				Integer dose = rs.getInt("dose");
				Vaccine v = new Vaccine(name, dose);
				list.add(v);
			}
		   
		}catch(SQLException e) {
			System.out.println("database error"); 
			e.printStackTrace();
		}
		return list; 
	}

	
}
