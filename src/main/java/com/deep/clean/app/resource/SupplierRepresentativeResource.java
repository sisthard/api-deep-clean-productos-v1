package com.deep.clean.app.resource;

import com.deep.clean.app.service.impl.SupplierRepresentativeServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/supplier-representative")
public class SupplierRepresentativeResource {

    @Inject
    SupplierRepresentativeServiceImpl supplierRepresentativeService;

    @GET
    @Path("/info-representatives")
    public Response getSupplierRepresentativeInfo() {
        return Response.ok(
                supplierRepresentativeService.getInfo()).build();
    }

}