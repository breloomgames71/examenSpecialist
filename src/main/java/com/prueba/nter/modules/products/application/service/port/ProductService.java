package com.prueba.nter.modules.products.application.service.port;

import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;

import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Retrieves all products
     * @return a list of all ProductEntity
     */
    List<ProductOutputDto>  getAll(int pageNumber, int pageSize);

    /**
     * Create a new product
     * @param products the product entity to create
     */
    void create(List<ProductInputDto> products);

    /**
     *  Calculates the total of the prices
     *  from the products passed
     * @param products
     * @return
     */
    Double calculatePrices(List<ProductInputDto> products);

    /**
     * Counts the total of products
     * by the passed category
     * @param products
     * @param category
     * @return
     */
    Long countByCategory(List<ProductInputDto> products, String category);
}
