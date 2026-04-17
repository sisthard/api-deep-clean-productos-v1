package com.deep.clean.app.dto;

import com.deep.clean.app.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    
    @NotBlank(message = "SKU no puede estar vacío")
    private String sku;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    
    private String description;
    private Integer categoryId;
    private Integer currentStock;
    private Integer minStockThreshold;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}