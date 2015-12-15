package csv.resteasyclient;


import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class RESTEasyClient {

	public static void main(String[] args) {

	//	Student st = new Student("Catain", "Hook", 10, 12);
		String myString="\"a1\";\"a2\";\"a3\";\"a4\";\"id\";\"label\"\n" +
				"5.1;3.5;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
				"4.9;3.0;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
				"5.0;3.3;1.4;0.2;\"id_50\";\"Iris-setosa\"\n" +
				"7.0;3.2;4.7;1.4;\"id_51\";\"Iris-versicolor\"\n" +
				"5.1;2.5;3.0;1.1;\"id_99\";\"Iris-versicolor\"\n" +
				"5.7;2.8;4.1;1.3;\"id_99\";\"Iris-versicolor\"\n" +
				"6.3;3.3;6.0;2.5;\"id_101\";\"Iris-virginica\"\n" +
				"5.8;2.7;5.1;1.9;\"id_102\";\"Iris-virginica\"\n";

		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client
					.target("http://localhost:8080/csvparser/send");

			Response response = target.request().post(
					Entity.entity(myString, "text/csv"));

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
