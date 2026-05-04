package com.prueba.nter.modules.users.application.service.port;

import com.prueba.nter.modules.users.infrastructure.dto.input.UserInputDto;
import com.prueba.nter.modules.users.infrastructure.dto.ouput.UserOutputDto;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves all users
     * @return a list of all UserEntity
     */
    List<UserOutputDto> getAll(int pageNumber, int pageSize);

    /**
     * Create a new user
     * @param users the user entity to create
     */
    void create(List<UserInputDto> users);
}
