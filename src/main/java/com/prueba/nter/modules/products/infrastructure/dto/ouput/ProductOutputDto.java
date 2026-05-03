package com.prueba.nter.modules.products.infrastructure.dto.ouput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOutputDto {

    Long id;
    String name;
    String description;
    Double price;
    Integer quantity;
    String category;
    String brand;
    Date expirationDate;
}
