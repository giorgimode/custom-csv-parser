package csv;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/send")
public class RESTService {

	@POST
	@Path("{filename}")
	@Consumes("text/csv")
	public Response consumeJSON(@PathParam("filename") String filename, String csvContent) {
		
	CSVParser.parser(filename, csvContent);

		return Response.status(200).entity("OK").build();
	}

}
