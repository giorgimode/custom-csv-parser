package com.javacodegeeks.enterprise.rest.resteasy;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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
	public Response consumeJSON(String student) {
		
	CSVParser.parser(student);

		return Response.status(200).entity("OK").build();
	}

}
