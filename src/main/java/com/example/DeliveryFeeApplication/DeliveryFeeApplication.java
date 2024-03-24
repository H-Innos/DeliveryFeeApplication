package com.example.DeliveryFeeApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*************************************
 * REST interface for calculating the delivery fee of an order based on the location, vehicle and
 * current weather condtions in the location.
 *
 * Author: Henrik Innos
 * Date: March 2024
 *************************************/

@SpringBootApplication
@EnableScheduling
public class DeliveryFeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeApplication.class, args);
	}

}
