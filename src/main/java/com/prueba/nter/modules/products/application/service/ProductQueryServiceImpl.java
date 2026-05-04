package com.prueba.nter.modules.products.application.service;


import com.prueba.nter.modules.products.application.service.port.ProductQueryService;
import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import com.prueba.nter.modules.products.infrastructure.mapper.ProductMapper;
import com.prueba.nter.modules.products.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Finds a product by its name.
     *
     * @param name name of the product
     * @return product found mapped to ProductOutputDto
     */
    @Override
    public ProductOutputDto findProductsByName(String name) {
        ProductEntity product = productRepository.findByName(name);
        return productMapper.toProductOutputDto(product);
    }

    /**
     * Counts the total number of products by category.
     *
     * @param category category of the products
     * @return total number of products in that category
     */
    @Override
    public Long countTotalProductsByCategory(String category) {
        return productRepository.countByCategory(category);
    }

    /**
     * Finds a product by category and name.
     *
     * @param category category of the product
     * @param name name of the product
     * @return product found mapped to ProductOutputDto
     */
    @Override
    public ProductOutputDto findProductByCategoryAndName(String category, String name) {
        ProductEntity product = productRepository.findByCategoryAndName(category, name);
        return productMapper.toProductOutputDto(product);
    }

    /**
     * Retrieves all products with the given name,
     * ordered by price in ascending order.
     *
     * @param name name of the product to filter
     * @return list of products sorted by price (ascending)
     */
    @Override
    public List<ProductOutputDto> findProductsByNameOrderByPriceAsc(String name) {
        return productMapper.toListProductOutputDto(productRepository.findPricesByNameOrderByPriceAsc(name));
    }

    /**
     * Retrieves the product with the lowest price
     * for the given name.
     *
     * @param name name of the product to search
     * @return product with the lowest price among those with the given name
     */
    @Override
    public ProductOutputDto findCheapestProductByName(String name) {
        ProductEntity product = productRepository.findCheapestProductByName(name);
        return productMapper.toProductOutputDto(product);
    }
}
