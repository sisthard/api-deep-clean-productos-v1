package com.deep.clean.app.service.impl;

import com.deep.clean.app.client.SupplierRepresentativeClient;
import com.deep.clean.app.client.dto.SupplierRepresentativeResponse;
import com.deep.clean.app.dto.SupplierRepresentativeDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupplierRepresentativeServiceImpl {

    @Inject
    @RestClient
    SupplierRepresentativeClient supplierRepresentativeClient;

    public List<SupplierRepresentativeDTO> getInfo() {
        return supplierRepresentativeClient.users()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SupplierRepresentativeDTO mapToDTO(SupplierRepresentativeResponse response) {
        return SupplierRepresentativeDTO.builder()
                .id(response.id())
                .name(response.name())
                .email(response.email())
                .password(response.password())
                .role(response.role())
                .avatar(response.avatar())
                .proveedor("DEEP CLEAN") // Seteo manual del proveedor
                .build();
    }
}
