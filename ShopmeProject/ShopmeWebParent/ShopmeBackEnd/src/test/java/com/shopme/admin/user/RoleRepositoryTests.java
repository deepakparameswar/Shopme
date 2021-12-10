package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

// please note that the test inserted values never be reflect the database table data
// which means it don't insert the data into the table the reason for that is by default
// spring DataJpaTest execute it as an transaction it will revert it back once success
// To turn off the roll back we can use the @Rollback() to false
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		// to persist this role object into the database we can use
		// the method of repo.save(<object>)
		// also we can assign the return value to a new role object savedRole
		// for the purpose of assertion
		Role savedRole = repo.save(roleAdmin);
		// assertThat() is an assertion method and isGreaterThan(0) is used to check the data already 
		// persisted into the database
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		
		Role roleSalesPerson = new Role("Salesperson", "manage product price, customers, shipping,"
				+"orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, products,"
				+"articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders and updates order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		
		repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
		
	}
	
}
