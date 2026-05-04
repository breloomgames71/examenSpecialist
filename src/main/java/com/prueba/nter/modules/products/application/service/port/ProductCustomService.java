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

    /**
     * Retrieves products associated with a specific user email.
     * Optionally filters the results by category and brand.
     *
     * @param email user email used to filter products
     * @param category optional category filter
     * @param brand optional brand filter
     * @return list of products that match the given criteria
     */
    List<ProductOutputDto> findProductsByUserEmail(
            String email,
            String category,
            String brand
    );

    /**
     * Retrieves products that belong to the oldest users in the system.
     *
     * @param numberOfUsers number of oldest users to consider
     * @return list of products associated with the oldest users
     */
    List<ProductOutputDto> findProductsFromOldestUsers(Integer numberOfUsers);

    /**
     * Performs an advanced search with dynamic filters, pagination and sorting.
     *
     * @param name optional product name filter
     * @param category optional category filter
     * @param brand optional brand filter
     * @param minPrice optional minimum price filter
     * @param maxPrice optional maximum price filter
     * @param startDate optional start date for expiration filtering
     * @param endDate optional end date for expiration filtering
     * @param providerId optional provider identifier filter
     * @param page page number (starting from 0)
     * @param size number of elements per page
     * @param sortBy field used for sorting
     * @param direction sorting direction (asc or desc)
     * @return paginated result of products matching the filters
     */
    Page<ProductOutputDto> advancedSearch(
            String name,
            String category,
            String brand,
            Double minPrice,
            Double maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Long providerId,
            int page,
            int size,
            String sortBy,
            String direction
    );
}
