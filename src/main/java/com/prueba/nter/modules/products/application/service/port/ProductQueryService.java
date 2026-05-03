package com.prueba.nter.modules.products.application.service.port;


import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;

/**
 * Service interface for managing products.
 */
public interface ProductQueryService {

    /**
     * Find the products by
     * the passed name in the database
     *
     * @param name
     */
    ProductOutputDto findProductsByName(String name);

    /**
     * Counts the total of
     * products by the category in the database
     *
     * @param category
     */
    Long countTotalProductsByCategory(String category);

    /**
     * Search in the database
     * the product by the name and
     * category passed
     *
     * @param category
     * @param name
     */
    ProductOutputDto findProductByCategoryAndName(String category, String name);
}
