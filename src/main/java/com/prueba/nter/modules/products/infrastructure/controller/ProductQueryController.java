package com.prueba.nter.modules.products.infrastructure.controller;

import com.prueba.nter.constants.Constants;
import com.prueba.nter.modules.products.application.service.port.ProductQueryService;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Products with Queries.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/query/products")
public class ProductQueryController {
    private final ProductQueryService productQueryService;

    /**
     * Method to return a product
     * with the passed name
     * @param name
     * @return ProductOutputDto
     */
    @GetMapping("/name")
    public ResponseEntity<ProductOutputDto> getAllProductsByName(@RequestParam("name") String name){
        return ResponseEntity.ok(productQueryService.findProductsByName(name));
    }
    /**
     * Method that counts the total
     * of products with the passed
     * category
     * @param category
     * @return String
     */
    @GetMapping("/category")
    public ResponseEntity<String> getTotalByCategory(@RequestParam("category") String category){
        String message = Constants.TOTAL_PRODUCTS_BY_CATEGORY + productQueryService.countTotalProductsByCategory(category);
        return ResponseEntity.ok(message);
    }
    /**
     * Method that returns a product
     * with the category and name
     * passed
     * @param category
     * @param name
     * @return ProductOutputDto
     */
    @GetMapping("/multiple/filter")
    public ResponseEntity<ProductOutputDto> getProductByCategoryAndName(@RequestParam("category") String category,
                                                                           @RequestParam("name") String name){
        return ResponseEntity.ok(productQueryService.findProductByCategoryAndName(category, name));
    }

    /**
     * Retrieves all products with the given name,
     * ordered by price in ascending order.
     *
     * This endpoint returns products across all providers
     * that match the specified name.
     *
     * @param name name of the product to filter
     * @return ResponseEntity containing a list of products sorted by price (ascending)
     */
    @GetMapping("/prices")
    public ResponseEntity<List<ProductOutputDto>> findProductsByNameOrderByPriceAsc(
            @RequestParam String name) {

        return ResponseEntity.ok(productQueryService.findProductsByNameOrderByPriceAsc(name));
    }

    /**
     * Retrieves the product with the lowest price
     * for the given name.
     *
     * @param name name of the product to search
     * @return ResponseEntity containing the cheapest product
     */
    @GetMapping("/cheapest")
    public ResponseEntity<ProductOutputDto> findCheapestProductByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                productQueryService.findCheapestProductByName(name)
        );
    }
}
