package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
// Because of the different packages the spring boot application can't
// discover the entity class so to scan the entities we use @EntityScan()
// and we passed the packages inside an object
@EntityScan({"com.shopme.common.entity", "com.shopme.admin.user"})
public class ShopmeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}

}
