package com.deep.clean.app.client;

import com.deep.clean.app.client.dto.SupplierRepresentativeResponse;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v1")
@RegisterRestClient(configKey = "escuelajs-api")
public interface SupplierRepresentativeClient {

    @Path("/users")
    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    List<SupplierRepresentativeResponse> users();
}
