package com.deep.clean.app.mapper;

import com.deep.clean.app.dto.ProductDTO;
import com.deep.clean.app.entity.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductMapper {

    public ProductDTO toDTO(ProductEntity entity) {
        if (entity == null) return null;
        return ProductDTO.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .name(entity.getName())
                .description(entity.getDescription())
                .categoryId(entity.getCategoryId())
                .currentStock(entity.getCurrentStock())
                .minStockThreshold(entity.getMinStockThreshold())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) return null;
        ProductEntity entity = new ProductEntity();
        updateEntityFromDTO(entity, dto);
        return entity;
    }

    public void updateEntityFromDTO(ProductEntity entity, ProductDTO dto) {
        if (dto == null) return;
        entity.setSku(dto.getSku());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategoryId(dto.getCategoryId());
        entity.setCurrentStock(dto.getCurrentStock());
        entity.setMinStockThreshold(dto.getMinStockThreshold());
        entity.setStatus(dto.getStatus());
    }
}