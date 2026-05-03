package com.prueba.nter.modules.products.infrastructure.controller;

import com.prueba.nter.modules.products.application.service.port.ProductCustomService;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import lombok.RequiredArgsConstructor;
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
     * @return
     */
    @GetMapping("/multiple/filter")
    public ResponseEntity<List<ProductOutputDto>> returnProductsByCategoryAndName(@RequestParam(value = "category", required = false) String category,
                                                                                  @RequestParam(value = "name", required = false) String name){
        return ResponseEntity.ok(productCustomService.findProductByCategoryAndOrName(category, name));
    }

    /**
     * Method to obtain products
     * with a lower price than the
     * passed
     * @param price
     * @return
     */
    @GetMapping("/price")
    public ResponseEntity<List<ProductOutputDto>> returnProductsByLessPrice(@RequestParam(value = "price") Double price){
        return ResponseEntity.ok(productCustomService.findProductsLessThanPrice(price));
    }

    /**
     * Method that retrieves the products
     * with an expiration date between
     * two dates
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/date")
    public ResponseEntity<List<ProductOutputDto>> returnProductsByDate(@RequestParam(value = "startDate") LocalDate startDate,
                                                                       @RequestParam(value = "endDate") LocalDate endDate){
        return ResponseEntity.ok(productCustomService.findProductsByDate(startDate, endDate));
    }

    /**
     * Method to return a product
     * with the category passed and
     * the lowest price
     * @param category
     * @return
     */
    @GetMapping("/category/price")
    public ResponseEntity<ProductOutputDto> returnProductByCategoryAndLowestPrice(@RequestParam(value = "category") String category){
        return ResponseEntity.ok(productCustomService.findProductsByCategoryAndLowestPrice(category));
    }

}

