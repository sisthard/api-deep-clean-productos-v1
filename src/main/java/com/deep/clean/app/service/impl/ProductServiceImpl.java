package com.deep.clean.app.service.impl;

import com.deep.clean.app.dto.ProductDTO;
import com.deep.clean.app.entity.ProductEntity;
import com.deep.clean.app.exception.AppException;
import com.deep.clean.app.exception.ConflictException;
import com.deep.clean.app.mapper.ProductMapper;
import com.deep.clean.app.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {
        return ProductEntity.<ProductEntity>listAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        return ProductEntity.<ProductEntity>findByIdOptional(id)
                .map(entity -> productMapper.toDTO(entity))
                .orElseThrow(() -> new AppException("Producto no encontrado", Response.Status.NOT_FOUND));
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDto) {
        if (ProductEntity.find("sku", productDto.getSku()).firstResultOptional().isPresent()) {
            throw new ConflictException("Ya existe un producto registrado con el SKU: " + productDto.getSku());
        }

        try {
            ProductEntity entity = productMapper.toEntity(productDto);
            entity.persistAndFlush();
            return productMapper.toDTO(entity);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new ConflictException("Error de duplicidad de datos en DB.");
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDto) {
        ProductEntity entity = ProductEntity.<ProductEntity>findByIdOptional(id)
                .orElseThrow(() -> new AppException("Producto no encontrado", Response.Status.NOT_FOUND));

        if (!entity.getSku().equals(productDto.getSku()) && 
            ProductEntity.find("sku", productDto.getSku()).firstResultOptional().isPresent()) {
            throw new ConflictException("El SKU ya está en uso.");
        }

        productMapper.updateEntityFromDTO(entity, productDto);
        entity.flush();
        return productMapper.toDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductEntity entity = ProductEntity.<ProductEntity>findByIdOptional(id)
                .orElseThrow(() -> new AppException("Producto no encontrado", Response.Status.NOT_FOUND));
        entity.delete();
    }
}
