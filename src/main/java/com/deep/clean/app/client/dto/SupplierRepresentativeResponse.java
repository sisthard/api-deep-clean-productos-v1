package com.deep.clean.app.client.dto;

public record SupplierRepresentativeResponse(
        Integer id,
        String email,
        String password,
        String name,
        String role,
        String avatar
) {
}