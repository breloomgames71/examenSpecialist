package com.prueba.nter.modules.products.infrastructure.mapper;

import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    /**
     * This method maps the product input
     * into the product entity
     * @param input product input dto
     * @return product entity
     */
    ProductEntity toProductEntity(ProductInputDto input);

    /**
     * This method maps the product input list
     * into the product entity list
     * @param input product input dto list
     * @return product entity list
     */
    List<ProductEntity> toListProductEntity(List<ProductInputDto> input);

    /**
     * This method maps the product entity
     * into the product output dto
     * @param input product entity
     * @return product output dto
     */
    ProductOutputDto toProductOutputDto(ProductEntity input);

    /**
     * This method maps the product list
     * into the product output dto list
     * @param input product entity list
     * @return product output dto list
     */
    List<ProductOutputDto> toListProductOutputDto(List<ProductEntity> input);
}
