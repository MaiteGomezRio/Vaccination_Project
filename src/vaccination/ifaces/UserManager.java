package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.*; 

public interface UserManager {
	  
      public void register(User user);   
      public void assignRole(User user, Role role);
      public Role getRole(String name);
      //public void assignRole(int userId, int roleId);  //another way of doing it
      public User login(String name, String password);   //if user doesnt exit return null 
      public void createRole(Role role); 
      public List<Role> getRoles(); 
      public void deleteUser(String name, String password);
}
