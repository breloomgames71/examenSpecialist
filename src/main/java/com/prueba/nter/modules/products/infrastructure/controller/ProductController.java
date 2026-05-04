package com.prueba.nter.modules.products.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.nter.constants.Constants;
import com.prueba.nter.error.exception.FileReadingException;
import com.prueba.nter.modules.products.application.service.port.ProductService;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * REST controller for managing Products.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/web/products")
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    /**
     * Method to retrieve a list
     * of all products
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductOutputDto>> getAllProducts(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                                    @RequestParam(defaultValue = "4", required = false) int pageSize){
        return ResponseEntity.ok(productService.getAll(pageNumber, pageSize));
    }

    /**
     * Method to save data coming
     * from a file
     * @param file
     * @return
     */
    @PostMapping(path = ("/file"),consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> saveProductsByFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body(Constants.EMPTY_FILE);
        }
        List<ProductInputDto> products = readProductsFromFile(file);
        productService.create(products);
        return ResponseEntity.ok().build();
    }

    /**
     * Method that receives a file
     * and counts the total of prices
     * in the file
     * @param file
     * @return
     */
    @PostMapping(path = ("/file/price"),consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> calculateTotalPriceFromFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body(Constants.EMPTY_FILE);
        }
        List<ProductInputDto> products = readProductsFromFile(file);
        String message = Constants.PRICE + productService.calculatePrices(products);
        return ResponseEntity.ok(message);
    }

    /**
     * Method that counts all the
     * products in the file with
     * the specific category passed
     * @param file
     * @return
     */
    @PostMapping(path = ("/file/category"),consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> countByCategory(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("category") String category) {
        if (file.isEmpty()){
            return ResponseEntity.badRequest().body(Constants.EMPTY_FILE);
        }
        List<ProductInputDto> products = readProductsFromFile(file);
        String message = Constants.TOTAL_PRODUCTS_CATEGORY + category + " is: " + productService.countByCategory(products, category);
        return ResponseEntity.ok(message);
    }

    /**
     * Method to parse the file
     * to a product input dto
     * @param file
     * @return
     */
    private List<ProductInputDto> readProductsFromFile(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<List<ProductInputDto>>() {
            });
        } catch (Exception e) {
            throw new FileReadingException(Constants.ERROR_PROCESSING_PRODUCT_FILE, 400);
        }
    }
}
