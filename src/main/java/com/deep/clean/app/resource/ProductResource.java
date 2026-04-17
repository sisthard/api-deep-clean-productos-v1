package com.deep.clean.app.resource;

import com.deep.clean.app.dto.ProductDTO;
import com.deep.clean.app.kafka.ProductProducer;
import com.deep.clean.app.service.ProductService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @Inject
    ProductProducer productProducer;

    @GET
    @Path("/all")
    public Response getAll() {
        return Response.status(Response.Status.OK)
                .entity(productService.findAll()).build();
    }

    @GET
    @Path("/find/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK)
                .entity(productService.findById(id)).build();
    }

    @POST
    @Path("/create")
    public Response create(@Valid ProductDTO productDto) {
        // 1. Persistir (Si falla aquí, lanza ConflictException y no llega a Kafka)
        ProductDTO created = productService.create(productDto);
        
        // 2. Notificar (Solo si la transacción de DB terminó exitosamente)
        productProducer.sendProductEvent(
                created.getId(),
                created.getSku(),
                created.getName(),
                created.getStatus() != null ? created.getStatus().name() : "ACTIVE"
        );

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/update/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProductDTO productDto) {
        ProductDTO updated = productService.update(id, productDto);
        
        productProducer.sendProductEvent(
                updated.getId(),
                updated.getSku(),
                updated.getName(),
                updated.getStatus() != null ? updated.getStatus().name() : "ACTIVE"
        );

        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        productService.delete(id);
        return Response.noContent().build();
    }
}