package vaccination.jdbc;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionManager {

	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/vaccination.db.sqbpro");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			createTables();
		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}

	}
	private void createTables() {
		try {
			Statement s = c.createStatement();
			String table_Doctor = "CREATE TABLE Doctor (id INTEGER PRIMARY KEY AUTOINCREMENT,"+ " id_document TEXT NOT NULL," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Doctor);
			
			String table_Patient = "CREATE TABLE Patient (id INTEGER PRIMARY KEY AUTOINCREMENT,"+ " id_document TEXT NOT NULL," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Patient);
			
			String table_Vaccine = "CREATE TABLE Vaccine (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " dose INTEGER)";
			s.executeUpdate(table_Vaccine);
			
			String table_Disease = "CREATE TABLE Disease (id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Disease);
			
			String table_Condition = "CREATE TABLE Condition (identifier INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Condition); 
			
			String table_Patient_Vaccine = "CREATE TABLE Patient_Vaccine(patient_id INTEGER REFERENCES Patient(id)," + "vaccine_id INTEGER REFERENCES Vaccine(identifier))";
			s.executeUpdate(table_Patient_Vaccine);
			
			String table_Patient_Disease = "CREATE TABLE Patient_Disease (\"CREATE TABLE Is_Immune(patient_id INTEGER NOT NULL REFERENCES Patient(id),\"+\"disease_id INTEGER NOT NULL REFERENCES "
					+ "Disease(id))";
			s.executeUpdate(table_Patient_Disease);
			
			String table_Patient_Condition = "CREATE TABLE Patient_Condition (patient_id INTEGER REFERENCES Patient(id)," + "condition_identifier INTEGER REFERENCES Condition(identifier))"; 
			s.executeUpdate(table_Patient_Condition);  
			
			String table_Disease_Vaccine = "CREATE TABLE Disease_Vaccine (disease_id INTEGER REFERENCES Disease(identifier)," 
			         + "vaccine_identifier REFERENCES Vaccine(identifier))"; 
			s.executeUpdate(table_Disease_Vaccine); 
			
			String table_Puts = "CREATE TABLE Puts(Date Date PRIMARY KEY"+"patient_id INTEGER NOT NULL REFERENCES Patient(id),"+" vaccine_id INTEGER NOT NULL REFERENCES Vaccine(identifier)," + "attendance BOOLEAN)";
			s.executeUpdate(table_Puts);
			
			String table_Is_Immune="CREATE TABLE Is_Immune(patient_id INTEGER NOT NULL REFERENCES Patient(id),"+"disease_id INTEGER NOT NULL REFERENCES"
					+ "Disease(id))";
			s.executeUpdate(table_Is_Immune);
			String table_Patient_Condition_Vaccine="CREATE TABLE Patient_Condition_Vaccine(patient_id INTEGER NOT NULL REFERENCES Patient(id),"+"condition_id INTEGER NOT NULL "
					+ "REFERENCES Condition(id),"+"vaccine_id INTEGER NOT NULL REFERENCES Vaccine(id))";
			s.executeUpdate(table_Patient_Condition_Vaccine);

			s.close();
		} catch (SQLException e) {
			// Check if the exception is because the tables already exist
			if (e.getMessage().contains("already exist")) {
				return;
			}
			System.out.println("Database error.");
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
	
}
