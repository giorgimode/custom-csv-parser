package com.javacodegeeks.enterprise.rest.resteasy.resteasyclient;

import com.javacodegeeks.enterprise.rest.resteasy.Student;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class RESTEasyClient {

	public static void main(String[] args) {

		Student st = new Student("Catain", "Hook", 10, 12);
		
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client
					.target("http://localhost:8080/myrest/rest/jsonServices/send");

			Response response = target.request().post(
					Entity.entity(st, "application/json"));

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}


			System.out.println("Server response : \n");
			System.out.println(response.readEntity(String.class));
			
			response.close();

		} catch (Exception e) {

			e.printStackTrace();

		}


		
	}

}
