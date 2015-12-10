package com.javacodegeeks.enterprise.rest.resteasy;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/jsonServices")
public class RESTEasyJSONServices {

	@GET
	@Path("/print/{name}")
	@Produces("application/json")
	public Student produceJSON( @PathParam("name") String name ) {
		
		Student st = new Student(name, "Marco",19,12);

		return st;

	}
	
	@POST
	@Path("/send")
	@Consumes("text/csv")
	public Response consumeJSON(String student ) {
		
		String output = student;

	//	sortCSV(student);

		return Response.status(200).entity(output).build();
	}

	/*private void sortCSV(String student) {

		try(Stream<String> lines = student){

			SortedMap<String, List<String>> collect = lines
					.collect(Collectors.groupingBy(l -> String.valueOf(l.split(",", 4)[2]), TreeMap::new, Collectors
							.toList()));

		}



	}*/


}
