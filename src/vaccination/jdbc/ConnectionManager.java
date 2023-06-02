package vaccination.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/vaccination.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			createTables();
			insertTables();
			insertVaccines();

		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return c;
	}

	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}

	private void createTables() {
		try {
			Statement s = c.createStatement();
			
			String table_Doctor = "CREATE TABLE Doctor ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " id_document TEXT NOT NULL,"
					+ " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL,"
					+ " email TEXT NOT NULL)";
			s.executeUpdate(table_Doctor);
			
			String table_Patient = "CREATE TABLE Patient ("
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " id_document TEXT NOT NULL,"
					+ " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL,"
					+ " email TEXT NOT NULL,"
					+ " doctor_id INTEGER,"
					+ " FOREIGN KEY (doctor_id) REFERENCES Doctor(id) ON DELETE SET NULL);";
			s.executeUpdate(table_Patient);
			
			String table_Vaccine = "CREATE TABLE Vaccine ("
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL,"
					+ " dose INTEGER,"
					+ " disease_id INTEGER NOT NULL,"
					+ " FOREIGN KEY (disease_id) REFERENCES Disease(id) ON DELETE CASCADE);";
			s.executeUpdate(table_Vaccine);
			
			String table_Disease = "CREATE TABLE Disease ("
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL);";
			s.executeUpdate(table_Disease);
			
			String table_Condition = "CREATE TABLE Condition ("
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL);";
			s.executeUpdate(table_Condition);
			
			String table_Patient_Vaccine = "CREATE TABLE Patient_Vaccine("
					+ " patient_id INTEGER,"
					+ " vaccine_id INTEGER,"
					+ " FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE SET NULL,"
					+ " FOREIGN KEY (vaccine_id) REFERENCES Vaccine(id) ON DELETE SET NULL,"
					+ " PRIMARY KEY (patient_id, vaccine_id));";
			s.executeUpdate(table_Patient_Vaccine);
			
			String table_Patient_Disease = "CREATE TABLE Patient_Disease("
					+ " patient_id INTEGER,"
					+ " disease_id INTEGER,"
					+ " FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY (disease_id) REFERENCES Disease(id) ON DELETE CASCADE,"
					+ " PRIMARY KEY (patient_id, disease_id));";
			s.executeUpdate(table_Patient_Disease);
			
			String table_Patient_Condition = "CREATE TABLE Patient_Condition ("
					+ " patient_id INTEGER,"
					+ " condition_id INTEGER,"
					+ " FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY (condition_id) REFERENCES Condition(id) ON DELETE CASCADE,"
					+ " PRIMARY KEY (patient_id, condition_id));";
			s.executeUpdate(table_Patient_Condition);
			
			String table_Appointment = "CREATE TABLE Appointment("
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Date date NOT NULL,"
					+ " patient_id INTEGER NOT NULL,"
					+ " vaccine_id INTEGER NOT NULL,"
					+ " doctor_id INTEGER NOT NULL,"
					+ " FOREIGN KEY(patient_id) REFERENCES Patient(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY(vaccine_id) REFERENCES Vaccine(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY(doctor_id) REFERENCES Doctor(id) ON DELETE CASCADE);";
			s.executeUpdate(table_Appointment);
			
			String table_Vaccine_Condition = "CREATE TABLE Vaccine_Condition ("
					+ " vaccine_id INTEGER,"
					+ " condition_id INTEGER,"
					+ " FOREIGN KEY(vaccine_id) REFERENCES Vaccine(id) ON DELETE CASCADE,"
					+ " FOREIGN KEY(condition_id) REFERENCES Condition(id) ON DELETE CASCADE,"
					+ " PRIMARY KEY (vaccine_id, condition_id));";
			s.executeUpdate(table_Vaccine_Condition);
			
			
			s.close();
		} catch (SQLException e) {
			if (e.getMessage().contains("already exist")) {
				return;
			}
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}

	public void insertTables() {
		try {
			Statement s1 = c.createStatement();
			ResultSet rs = s1.executeQuery("SELECT COUNT(*) FROM Disease");
			rs.next();
			int rowCount = rs.getInt(1);
			rs.close();
			s1.close();
			Statement s2 = c.createStatement();
			ResultSet rs2 = s2.executeQuery("SELECT COUNT(*) FROM Condition");
			rs2.next();
			int rowCount2 = rs2.getInt(1);
			rs2.close();
			s2.close();
			// Insert data when tables are empty
			if (rowCount == 0 && rowCount2 == 0) {
				Statement s = c.createStatement();
				//DISEASES
				String insert_Disease = "INSERT INTO Disease (name)VALUES ('Haemophilus')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Pneumococcal')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('SRP')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Measles')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Papilloma(HPV)')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('ChickenPox')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Diphteria')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Dtpa')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Meningococcus')";
				s.execute(insert_Disease);
				insert_Disease = "INSERT INTO Disease(name)VALUES ('Covid-19')";
				s.execute(insert_Disease);
				
				//CONDITIONS
				String insert_Condition = "INSERT INTO Condition(name)VALUES ('Pregnant')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('Allergies')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('HIV')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('Stroke')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('Ictus')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('Epilepsy')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('Pneumonia')";
				s.execute(insert_Condition);
				insert_Condition = "INSERT INTO Condition(name)VALUES ('SpecialMedCon')";
				
				s.close();
			}
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}
	


	public void insertVaccines() {
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Vaccine");
			rs.next();
			int rowCount = rs.getInt(1);
			if (rowCount == 0) {
				
				String disease_name;
				int d_id;
				String insert_Vaccine = "INSERT INTO Vaccine (name, dose, disease_id) VALUES (?, ?, ?)";
				PreparedStatement p_insert = c.prepareStatement(insert_Vaccine);
				
				disease_name = "Haemophilus";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME VAXELIS FOR HAEMOPHILUS
				p_insert.setString(1, "Vaxelis");
				p_insert.setInt(2, 3);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				disease_name = "Pneumococcal";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME PV13 FOR PNEUMOCOCCAL
				p_insert.setString(1, "PCV13");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
					
				//VACCINE NAME PV15 FOR PNEUMOCOCCAL
				p_insert.setString(1, "PCV15");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				disease_name = "SRP";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME VAXXON SRP SALMONELLA FOR SRP
				p_insert.setString(1, "Vaxxon SRP Salmonella");
				p_insert.setInt(2, 2);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				disease_name = "Measles";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME MMR FOR MEASLES
				p_insert.setString(1, "MMR");
				p_insert.setInt(2, 2);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME MMRV FOR MEASLES
				p_insert.setString(1, "MMRV");
				p_insert.setInt(2, 2);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				
				disease_name = "Papilloma(HPV)";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME 9vHPV FOR PAPILLOMA
				p_insert.setString(1, "9vHPV");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME 4vHPV FOR PAPILLOMA
				p_insert.setString(1, "4vHPV");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME 2vHPV
				p_insert.setString(1, "2vHPV");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				disease_name = "ChickenPox";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME VARIVAX FOR CHICKENPOX
				p_insert.setString(1, "VariVax");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME PROQUAD FOR CHICKENPOX
				p_insert.setString(1, "ProQuad");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				
				disease_name = "Diphteria";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME DTPA
				p_insert.setString(1, "DTPA");
				p_insert.setInt(2, 5);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				disease_name = "Dtpa";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME DTPA FOR DTPA
				p_insert.setString(1, "DTP");
				p_insert.setInt(2, 3);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				
				disease_name = "Meningococcus";
				d_id = getDiseaseId(disease_name);
				
				//NAME VACCINE MENB FOR MENINGOCOCCUS
				p_insert.setString(1, "MenB");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				//NAME VACCINE MENC FOR MENINGOCOCCUS
				p_insert.setString(1, "MenC");
				p_insert.setInt(2, 1);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				
				disease_name = "Covid-19";
				d_id = getDiseaseId(disease_name);
				
				//VACCINE NAME PFIZER FOR COVID-19
				p_insert.setString(1, "Pfizer");
				p_insert.setInt(2, 3);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME MODERNA FOR COVID-19
				p_insert.setString(1, "Moderna");
				p_insert.setInt(2, 2);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				//VACCINE NAME FOR COVID-19
				p_insert.setString(1, "Astrazeneca");
				p_insert.setInt(2, 2);
				p_insert.setInt(3, d_id);
				p_insert.executeUpdate();
				
				p_insert.close();
				//so it assigns each time the conditions to the vaccines 
				assignConditionToVaccineConnection(); 
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}

	public void assignConditionToVaccineConnection() {
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Vaccine_Condition");
			rs.next();
			int rowCount = rs.getInt(1);
			if (rowCount == 0) {
				String assign_ConditionVaccine = "INSERT INTO Vaccine_Condition VALUES (?,?)";
				PreparedStatement p_assign = c.prepareStatement(assign_ConditionVaccine);
				
				Integer vaccine_id;
				Integer condition_id;
				String v_name;
				String c_name;
				
				v_name="Vaxelis";
				vaccine_id = getVaccineId(v_name);
				
				c_name = "Allergies";
				condition_id = getConditionId(c_name);
				p_assign.setInt(1, vaccine_id);// Assigns allergies to Vaxelis
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name="Pfizer";
				vaccine_id = getVaccineId(v_name);
				condition_id = getConditionId("Allergies");
				p_assign.setInt(1, vaccine_id);// Assigns allergies to Pfizer
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				c_name="HIV";
				condition_id = getConditionId(c_name);
				p_assign.setInt(1, vaccine_id);// assigns HIV to Pfizer
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name="ProQuad";
				condition_id = getConditionId(c_name);
				vaccine_id = getVaccineId(v_name);
				p_assign.setInt(1, vaccine_id);// assigns HIV to ProQuad
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name = "DTP";
				vaccine_id = getVaccineId("DTP");
				c_name = "Stroke";
				condition_id = getConditionId("Stroke");
				p_assign.setInt(1, vaccine_id);// assigns stroke to DTP
				p_assign.setInt(2, condition_id);
				
				c_name = "Pregnant";
				condition_id = getConditionId("Pregnant");
				p_assign.setInt(1, vaccine_id);// assigns pregnant to DTP
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name = "DTPa";
				vaccine_id = getVaccineId(v_name);
				p_assign.setInt(1, vaccine_id);// assigns pregnant to DTPa
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				c_name = "Ictus";
				condition_id = getConditionId(c_name);
				p_assign.setInt(1, vaccine_id);// assigns ictus to DTPa
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name = "MenB";
				vaccine_id = getVaccineId(v_name);
				p_assign.setInt(1, vaccine_id);// assigns ictus to MenB
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				v_name = "MenC";
				vaccine_id = getVaccineId(v_name);
				p_assign.setInt(1, vaccine_id);// assigns ictus to MenC
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				
				v_name = "Moderna";
				vaccine_id = getVaccineId(v_name);
				c_name = "Epilepsy";
				
				condition_id = getConditionId(c_name);
				p_assign.setInt(1, vaccine_id);// assigns epilepsy to Moderna
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				
				v_name = "VariVax";
				vaccine_id = getVaccineId(v_name);
				
				c_name = "Pneumonia";
				condition_id = getConditionId(c_name);
				p_assign.setInt(1, vaccine_id);// assigns pneumonia to Varivax
				p_assign.setInt(2, condition_id);
				p_assign.executeUpdate();
				p_assign.close();
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
	}

	public int getDiseaseId(String d_name) {
		int disease_id = -1;
		try {
			String rs_vaccine = "SELECT id FROM Disease WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(rs_vaccine);
			p.setString(1, d_name);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				disease_id = rs.getInt("id");
			}
			p.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return disease_id;
	}

	public int getVaccineId(String v_name) {
		int vaccine_id = -1;
		try {
			String rs_vaccine = "SELECT id FROM Vaccine WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(rs_vaccine);
			p.setString(1, v_name);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				vaccine_id = rs.getInt("id");
			}
			p.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return vaccine_id;
	}

	public int getConditionId(String c_name) {
		int condition_id = -1;
		try {
			String rs_condition = "SELECT id FROM Condition WHERE name LIKE ?";
			PreparedStatement p;
			p = c.prepareStatement(rs_condition);
			p.setString(1, c_name);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				condition_id = rs.getInt("id");
			}
			p.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return condition_id;
	}
}
