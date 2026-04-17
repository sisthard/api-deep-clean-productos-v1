package com.deep.clean.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRepresentativeDTO {
    Integer id;
    String email;
    String password;
    String name;
    String role;
    String avatar;
    String proveedor;
}