package com.deep.clean.app.service;

import com.deep.clean.app.dto.ProductDTO;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO product);
    ProductDTO update(Long id, ProductDTO product);
    void delete(Long id);
}