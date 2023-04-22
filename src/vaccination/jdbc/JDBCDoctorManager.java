package vaccination.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import vaccination.ifaces.DoctorManager;
import vaccination.pojos.Doctor; 

public class JDBCDoctorManager implements DoctorManager {
	
	private Connection c;

	public void JDBCOwnerManager(Connection c) {
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
}
