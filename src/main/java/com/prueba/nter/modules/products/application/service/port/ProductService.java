package com.prueba.nter.modules.products.application.service.port;

import com.prueba.nter.modules.products.domain.entity.ProductEntity;

import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Retrieves all products
     * @return a list of all ProductEntity
     */
    List<ProductEntity> getAll();

    /**
     * Create a new product
     * @param product the product entity to create
     * @return the created ProductEntity
     */
    ProductEntity create(ProductEntity product);

    //...

}
