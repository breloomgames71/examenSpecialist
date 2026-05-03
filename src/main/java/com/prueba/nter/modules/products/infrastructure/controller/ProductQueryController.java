package com.prueba.nter.modules.products.infrastructure.controller;

import com.prueba.nter.modules.products.application.service.port.ProductQueryService;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
     */
    @GetMapping("/name")
    public ResponseEntity<ProductOutputDto> returnAllProductsByName(@RequestParam("name") String name){
        return ResponseEntity.ok(productQueryService.findProductsByName(name));
    }
    /**
     * Method that counts the total
     * of products with the passed
     * category
     * @param category
     * @return
     */
    @GetMapping("/category")
    public ResponseEntity<String> returnTotalByCategory(@RequestParam("category") String category){
        String message = "The total of products by the category is: " + productQueryService.countTotalProductsByCategory(category);
        return ResponseEntity.ok(message);
    }
    /**
     * Method that returns a product
     * with the category and name
     * passed
     * @param category
     * @param name
     * @return
     */
    @GetMapping("/multiple/filter")
    public ResponseEntity<ProductOutputDto> returnProductByCategoryAndName(@RequestParam("category") String category,
                                                                           @RequestParam("name") String name){
        return ResponseEntity.ok(productQueryService.findProductByCategoryAndName(category, name));
    }

}
