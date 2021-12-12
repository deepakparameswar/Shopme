package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
// @AutoConfigureTestDatabase(replace = Replace.NONE) to run the test on the real database
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	// The TestEntityManager class is provided by Spring Data JPA for unit testing with repository
	private TestEntityManager entityManager;
	
	@Test
	// Run this method as empty to create the table in the database by Hibernate it will create two tables users and users_roles(we added the code for that 
	// in the User entity and many-to-many relationship thats why when we run the method it will also create the users_roles table)
	public void testCreateUser() {
		
		// We can use entityManager to get a specific role from the database
		// And here 1 indicating that it is an admin and 1 is the id of Admin(we can check the roles database to view the id)
		Role roleAdmin = entityManager.find(Role.class, 1);
		
		// Run this method with constructors in order to insert the data to the table
		User userDeepak = new User("deepak@domain.com", "Admin@123", "Deepak", "K");
		// Set the role for the user also
		userDeepak.addRole(roleAdmin);
		
		// save() return an persisted object
		User savedUser = repo.save(userDeepak);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	// Create new user with Two Roles
	public void testCreateNewUserWithTwoRole() {
		
		User userRavi = new User("ravi@domain.com", "Admin@123", "Ravi", "Kumar");
		
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		
		User savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	// get all users from table
	public void testListAllUsers() {
		
		Iterable<User> listUsers = repo.findAll();
		
		// To print all users we need toString methods for the properties the we want to see in each of the User and Role entity classes else it show as object(we can't read)
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserById() {
		
		User userDeepak = repo.findById(1).get();
		
		System.out.println(userDeepak);
		
		assertThat(userDeepak).isNotNull();
		
	}
	
	@Test
	// Update user details
	public void testUpdateUserDetails() {
		
		User userDeepak = repo.findById(1).get();
		// update the enable to true
		userDeepak.setEnabled(true);
		userDeepak.setEmail("deepakK@domain.com");
		
		repo.save(userDeepak);
	}
	
	@Test
	// Update role of an user
	public void testUpdateUserRoles() {
		
		User userRavi = repo.findById(2).get();
		
		Role roleEditor = new Role(3);
		Role roleeSalesPerson = new Role(2);
		
		// Remove the editor role
		userRavi.getRoles().remove(roleEditor);
		// Add new salesPerson role to user
		userRavi.addRole(roleeSalesPerson);
		
		repo.save(userRavi);
		
	}
	
	@Test
	// Delete an existing user using id
	public void testDeleteUser() {
		
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "deepakK@domain.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
}
