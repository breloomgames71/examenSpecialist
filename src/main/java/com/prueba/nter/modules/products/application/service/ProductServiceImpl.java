package com.prueba.nter.modules.products.application.service;


import com.prueba.nter.modules.products.application.service.port.ProductService;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import com.prueba.nter.modules.products.infrastructure.mapper.ProductMapper;
import com.prueba.nter.modules.products.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Retrieves all products with pagination
     *
     * @return a list of all ProductEntity
     */
    @Override
    public List<ProductOutputDto> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productMapper.toListProductOutputDto(productRepository.findAll(pageRequest).stream().toList());
    }

    /**
     * Create new products from a list
     *
     * @param products the product entities to create
     */
    @Override
    public void create(List<ProductInputDto> products) {
        productRepository.saveAll(productMapper.toListProductEntity(products));
    }

    /**
     * Calculates the total of the prices
     * from the products passed
     *
     * @param products
     * @return
     */
    @Override
    public Double calculatePrices(List<ProductInputDto> products) {
        Double totalPrice = 0.0;
        for (ProductInputDto p : products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    /**
     * Counts the total of products
     * by the passed category
     * @param products
     * @param category
     * @return
     */
    @Override
    public Long countByCategory(List<ProductInputDto> products, String category) {
        Long totalProductsByCategory = 0L;
        for (ProductInputDto p : products){
            if (p.getCategory().equalsIgnoreCase(category)){
                totalProductsByCategory ++;
            }
        }
        return totalProductsByCategory;
    }
}
