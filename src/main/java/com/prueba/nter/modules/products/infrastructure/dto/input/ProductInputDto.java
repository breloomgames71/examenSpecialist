package com.prueba.nter.modules.products.infrastructure.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInputDto {

    String name;
    String description;
    Double price;
    Integer quantity;
    String category;
    String brand;
    Date expirationDate;

}
