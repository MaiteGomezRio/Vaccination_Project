package dogclinic.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vaccination.ifaces.*;
import vaccination.pojos.Role;
import vaccination.pojos.User; 

public class JPAUserManager implements UserManager{
	
	EntityManager em; 
	
	public JPAUserManager() {
		em = Persistence.createEntityManagerFactory("vaccination-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		//create the needed roles
		if(this.getRoles() == null) {
		   Role doctor = new Role("doctor");
		   Role patient = new Role("patient"); 
		   this.createRole(patient);
		   this.createRole(doctor);
		}
	}
	
	public void close() {
		em.close(); 
	}

	@Override
	public void register(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit(); 
	}

	@Override
	public User login(String name, String password) {
		Query q = em.createNativeQuery("SELECT FROM users WHERE name = ? AND password = ? ", User.class);
		q.setParameter(1, name);
		q.setParameter(2, password); 
		User user = (User)q.getSingleResult();
		return user; 
	}
	@Override
	public void assignRole(User user, Role role) {
		 em.getTransaction().begin();
		 user.setRole(role);
		 role.addUser(user); 
		 em.getTransaction().commit(); 
	}

	@Override
	public void createRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit(); 
		
	}
    @Override
    public List<Role> getRoles(){
    	Query q = em.createNativeQuery("SELECT * FROM roles", Role.class); 
    	List<Role> roles = (List<Role>)q.getResultList(); 
    	return roles; 
    }
}
