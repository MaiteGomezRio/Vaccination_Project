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
}
