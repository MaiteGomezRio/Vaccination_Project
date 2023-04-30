package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	// TODO if we remove the vaccine do we also have to remove it from the patient
	// (?) i think i could create a method called removeVaccineFromPatient and call
	// it from this method.
	@Override
	public void insertVaccine(Vaccine vaccine) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO Vaccine (name, dose) VALUES ('" + vaccine.getName() + "','" + vaccine.getDose()
					+ "')";
			s.executeUpdate(sql);
			s.close();
		} catch (SQLException e) {
			System.out.println("database exception");
			e.printStackTrace();
		}

	}

	@Override
	public List<Vaccine> searchVaccinesByPatient(int p_id) {
		List<Vaccine> list = new ArrayList<Vaccine>();
		try {
			String sql = "SELECT name FROM Patient_Vaccine WHERE p_id LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, "%" + p_id + "%"); // the percentages are so it looks for every name that contains that word.
												// Ex: if you type dri it looks for rodrigo too.
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String n = rs.getString("name");
				Integer dose = rs.getInt("dose");
				Patient patient = new Patient(p_id);
				Vaccine v = new Vaccine(n, dose, patient);
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
			String patient_id = rs.getString("p_id");
			Patient patient = new Patient(patient_id);
			Vaccine v = new Vaccine(name, dose, patient);
			return v;
			// TODO i don't know if i should create another method for when it is not
			// assigned to a patient, it would be the same but without putting patient on the
			// constructor.
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assignVaccineToPatient(String v_name, String p_id) {
		try {
			String sql = "INSERT into Patient_Vaccine(v_name, p_id) WHERE VALUES (?,?)"; // TODO i think we need to																				// vaccine
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, v_name);
			p.setString(2, p_id);
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
