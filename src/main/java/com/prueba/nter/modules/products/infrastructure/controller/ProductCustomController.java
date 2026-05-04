package com.prueba.nter.modules.products.infrastructure.controller;

import com.prueba.nter.modules.products.application.service.port.ProductCustomService;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing Products with Custom queries.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/custom/products")
public class ProductCustomController {
    private final ProductCustomService productCustomService;

    /**
     * Method to return a list
     * of products by its category
     * and/or name
     * @param category
     * @param name
     * @return ProductOutputDto
     */
    @GetMapping("/multiple/filter")
    public ResponseEntity<List<ProductOutputDto>> getProductsByCategoryAndName(@RequestParam(value = "category", required = false) String category,
                                                                                  @RequestParam(value = "name", required = false) String name){
        return ResponseEntity.ok(productCustomService.findProductByCategoryAndOrName(category, name));
    }

    /**
     * Method to obtain products
     * with a lower price than the
     * passed
     * @param price
     * @return ProductOutputDto
     */
    @GetMapping("/price")
    public ResponseEntity<List<ProductOutputDto>> getProductsByLessPrice(@RequestParam(value = "price") Double price){
        return ResponseEntity.ok(productCustomService.findProductsLessThanPrice(price));
    }

    /**
     * Method that retrieves the products
     * with an expiration date between
     * two dates
     * @param startDate
     * @param endDate
     * @return ProductOutputDto
     */
    @GetMapping("/date")
    public ResponseEntity<List<ProductOutputDto>> getProductsByDate(@RequestParam(value = "startDate") LocalDate startDate,
                                                                       @RequestParam(value = "endDate") LocalDate endDate){
        return ResponseEntity.ok(productCustomService.findProductsByDate(startDate, endDate));
    }

    /**
     * Method to return a product
     * with the category passed and
     * the lowest price
     * @param category
     * @return ProductOutputDto
     */
    @GetMapping("/category/price")
    public ResponseEntity<ProductOutputDto> getProductByCategoryAndLowestPrice(@RequestParam(value = "category") String category){
        return ResponseEntity.ok(productCustomService.findProductsByCategoryAndLowestPrice(category));
    }

    /**
     * Retrieves products that belong to a user filtered by email.
     * Optionally filters the result by category and brand.
     *
     * @param email user email used to find the related products
     * @param category optional product category filter
     * @param brand optional product brand filter
     * @return list of products that match the provided filters
     */
    @GetMapping("/user")
    public ResponseEntity<List<ProductOutputDto>> getProductsByUserEmail(
            @RequestParam String email,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand) {

        return ResponseEntity.ok(
                productCustomService.findProductsByUserEmail(email, category, brand)
        );
    }

    /**
     * Retrieves products that belong to the oldest users in the system.
     *
     * @param numberOfUsers number of the oldest users to search
     * @return list of products associated with the oldest users
     */
    @GetMapping("/oldest-users")
    public ResponseEntity<List<ProductOutputDto>> getProductsFromOldestUsers(
            @RequestParam Integer numberOfUsers) {

        return ResponseEntity.ok(
                productCustomService.findProductsFromOldestUsers(numberOfUsers)
        );
    }

    /**
     * Performs an advanced product search using dynamic filters,
     * pagination and sorting.
     *
     * @param name optional product name filter
     * @param category optional product category filter
     * @param brand optional product brand filter
     * @param minPrice optional minimum price filter
     * @param maxPrice optional maximum price filter
     * @param startDate optional expiration start date filter
     * @param endDate optional expiration end date filter
     * @param providerId optional provider identifier filter
     * @param page page number
     * @param size page size
     * @param sortBy field used for sorting
     * @param direction sorting direction
     * @return paginated list of products matching the filters
     */
    @GetMapping("/advanced-search")
    public ResponseEntity<Page<ProductOutputDto>> getAdvancedSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long providerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                productCustomService.advancedSearch(
                        name,
                        category,
                        brand,
                        minPrice,
                        maxPrice,
                        startDate,
                        endDate,
                        providerId,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

}

