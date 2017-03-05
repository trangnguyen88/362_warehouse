package com.champion.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import javax.xml.ws.Endpoint;

//@SpringBootApplication
public class ChampionApplication {

	public static void main(String[] args) {
		//SpringApplication.run(OrderManagementService.class, args);
		String baseUrl = "http://localhost:9000/";
		startWebServices(baseUrl,new OrderManagementService());
		startWebServices(baseUrl,new CustomerManagementService());
		startWebServices(baseUrl,new ProductManagementService());
		startWebServices(baseUrl,new VendorManagementService());
	}

	private static void startWebServices(String baseUrl,Object service){
		String address = baseUrl + service.toString();
		Endpoint.publish(address, service);
		System.out.println(service.toString() + " started successfully");
	}
}
