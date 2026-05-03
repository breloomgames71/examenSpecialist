package com.prueba.nter.modules.products.application.service.port;


import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductCustomService {

    /**
     * Search in the database
     * the product by the name and/or
     * category passed
     *
     * @param category category of the product
     * @param name name of the product
     */
    List<ProductOutputDto> findProductByCategoryAndOrName(String category, String name);

    /**
     * Search in the database
     * the products where the price is
     * smaller than the one passed
     *
     * @param price price of the product
     */
    List<ProductOutputDto> findProductsLessThanPrice(Double price);

    /**
     * Search in the database
     * the products where the expirationDate
     * is between two params
     *
     * @param startDate starting expirationDate
     * @param endDate ending expirationDate
     */
    List<ProductOutputDto> findProductsByDate(LocalDate startDate, LocalDate endDate);

    /**
     * Search in the database
     * the product by the category passed
     * and the lowest price
     *
     * @param category category of the product
     */
    ProductOutputDto findProductsByCategoryAndLowestPrice(String category);
}
