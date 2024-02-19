package com.isabelsilva.vendorsearch;

import com.isabelsilva.vendorsearch.controllers.VendorSearchController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class VendorsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendorsearchApplication.class, args);
	}

}
