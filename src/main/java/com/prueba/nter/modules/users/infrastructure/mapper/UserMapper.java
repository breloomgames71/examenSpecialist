package com.prueba.nter.modules.users.infrastructure.mapper;

import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import com.prueba.nter.modules.users.domain.UserEntity;
import com.prueba.nter.modules.users.infrastructure.dto.input.UserInputDto;
import com.prueba.nter.modules.users.infrastructure.dto.ouput.UserOutputDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * This method maps the user input
     * into the user entity
     * @param input user input dto
     * @return user entity
     */
    UserEntity toUserEntity(UserInputDto input);

    /**
     * This method maps the user input list
     * into the user entity list
     * @param input user input dto list
     * @return user entity list
     */
    List<UserEntity> toListUserEntity(List<UserInputDto> input);

    /**
     * This method maps the user entity
     * into the user output dto
     * @param input user entity
     * @return user output dto
     */
    UserOutputDto toUserOutputDto(UserEntity input);

    /**
     * This method maps the user list
     * into the user output dto list
     * @param input user entity list
     * @return user output dto list
     */
    List<UserOutputDto> toListUserOutputDto(List<UserEntity> input);
}
