package com.prueba.nter.modules.users.application.service;

import com.prueba.nter.modules.users.application.service.port.UserService;
import com.prueba.nter.modules.users.domain.UserEntity;
import com.prueba.nter.modules.users.infrastructure.dto.input.UserInputDto;
import com.prueba.nter.modules.users.infrastructure.dto.ouput.UserOutputDto;
import com.prueba.nter.modules.users.infrastructure.mapper.UserMapper;
import com.prueba.nter.modules.users.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Retrieves all products with pagination
     *
     * @return a list of all ProductEntity
     */
    @Override
    public List<UserOutputDto> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return userMapper.toListUserOutputDto(userRepository.findAll(pageRequest).stream().toList());
    }

    /**
     * Create new users from a list
     *
     * @param users the user entities to create
     */
    @Override
    public void create(List<UserInputDto> users) {
        if (users.isEmpty()) {
            return;
        }

        List<UserEntity> userEntities = users.stream()
                .filter(userDto -> !userRepository.existsByEmailIgnoreCase(userDto.getEmail()))
                .map(userMapper::toUserEntity)
                .toList();
        userRepository.saveAll(userEntities);
    }
}
