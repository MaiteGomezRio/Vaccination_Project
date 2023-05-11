package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import vaccination.ifaces.DirectorManager;
import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Vaccine;

public class JDBCDirectorManager implements DirectorManager{
	
	private Connection c;

	public JDBCDirectorManager(Connection c) {
		this.c = c;

	}
	
	
	
	@Override
	public void insertVaccine(Vaccine vaccine) {
		try {	
			String sql = "INSERT INTO Vaccine (name, dose)" + "VALUES (?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1,vaccine.getName());
			p.setInt(2, vaccine.getDose());
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database exception");
			e.printStackTrace();
		}

	}

	@Override
	public void insertCondition(Condition con) {
		try {
			Statement s = c.createStatement(); 
			String sql = "INSERT INTO Condition (type) VALUES ('"+con.getType()+"')"; 
			s.executeUpdate(sql); 
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}
	@Override
	
	public void insertDisease(Disease disease) {
		try {
			Statement s = c.createStatement(); 
			String sql = "INSERT INTO Disease (name) VALUES ('"+disease.getName()+"')"; 
			s.executeUpdate(sql); 
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}

	@Override
	public void removePatient(String name) {
		try {
			String sql = "DELETE FROM Patient WHERE name = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setString(1, name);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeDoctor(String name) {
		try {
			String sql = "DELETE FROM Doctor WHERE name = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setString(1, name);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeVaccine(String name) {
		try {
			String sql = "DELETE FROM Vaccine WHERE name = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, name);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}
	@Override
	public void assignConditionToVaccine(int c_id, int v_id) {
		// TODO create this method
		
	}
	
	@Override
	public void assignDoctorToPatient(int d_id, int p_id) {
		try {
			String sql= "UPDATE Patient SET doctor=? WHERE p_id= ?";
			PreparedStatement p = c.prepareStatement(sql);			
			p.setInt(1, d_id);
			p.setInt(2, p_id);
			p.executeUpdate();
			p.close();
			
		
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}
	
	


}
