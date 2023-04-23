package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import vaccination.ifaces.VaccineManager;
import vaccination.pojos.Vaccine;

public class JDBCVaccineManager implements VaccineManager {
	
	private Connection c;

	public void JDBCVaccineManager(Connection c) {
		this.c = c;

	}
	
	@Override 
	public void removeVaccine(String name) {
		try {
			String sql = "DELETE FROM Vaccine WHERE name = ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, name); 
			p.executeUpdate();
			p.close(); 
		}catch(SQLException e) {
			System.out.println("database error"); 
			e.printStackTrace();
		}
	}

	@Override
	public void insertVaccine(Vaccine vaccine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vaccine> searchVaccineByPatient(String p_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vaccine getVaccine(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignVaccineToPatient(String v_name, String p_id) {
		// TODO Auto-generated method stub
		
	}

}
