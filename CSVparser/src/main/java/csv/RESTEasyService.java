package csv;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/send")
public class RESTEasyService {

	@POST
	@Consumes("text/csv")
	public Response consumeJSON(String student) {
		
	CSVParser.parser(student);

		return Response.status(200).entity("OK").build();
	}

}
