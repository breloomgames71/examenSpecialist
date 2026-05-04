package com.prueba.nter.modules.products.application.service.port;


import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;

import java.util.List;

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

    /**
     * Retrieves all products with the given name,
     * ordered by price in ascending order.
     *
     * @param name name of the product to filter
     * @return list of products sorted by price from lowest to highest
     */
    List<ProductOutputDto> findProductsByNameOrderByPriceAsc(String name);

    /**
     * Retrieves the product with the lowest price
     * for the given name.
     *
     * @param name name of the product to search
     * @return product with the lowest price among those with the given name
     */
    ProductOutputDto findCheapestProductByName(String name);
}
