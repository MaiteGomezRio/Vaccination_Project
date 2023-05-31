package vaccination.jdbc;

import java.io.IOException;
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
			String sql = "INSERT INTO Vaccine (name, dose, disease_id)" + "VALUES (?,?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1,vaccine.getName());
			p.setInt(2, vaccine.getDose());	
			p.setInt(3, vaccine.getDisease().getId());
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
	public void removePatient(int p_id) {
		try {
			String sql = "DELETE * FROM Patient WHERE id = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setInt(1, p_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeDoctor(int d_id) {
		try {
			String sql = "DELETE * FROM Doctor WHERE id = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setInt(1, d_id);
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
			String sql = "DELETE * FROM Vaccine WHERE name = ?";
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
		try {
			String sql="INSERT INTO Condition_Vaccine (condition_id, vaccine_id)" + "VALUES (?,?)";
			PreparedStatement p=c.prepareStatement(sql);	
			p.setInt(1, c_id);
			p.setInt(2, v_id);
			p.executeUpdate();
			p.close();
		}
		catch(SQLException sqx) {
			System.out.println("database error");
			sqx.printStackTrace();
			
		}
		
	}
	@Override 
	public void assignDiseaseToVaccine(int d_id,int v_id) {
		try {
			String sql = "INSERT into Disease_Vaccine(disease_id, vaccine_id) WHERE VALUES (?,?)"; 																			// vaccine
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
	
	
	


}
