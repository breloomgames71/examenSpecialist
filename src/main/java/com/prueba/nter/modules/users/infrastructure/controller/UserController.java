package com.prueba.nter.modules.users.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.nter.constants.Constants;
import com.prueba.nter.error.exception.FileReadingException;
import com.prueba.nter.modules.users.application.service.port.UserService;
import com.prueba.nter.modules.users.infrastructure.dto.input.UserInputDto;
import com.prueba.nter.modules.users.infrastructure.dto.ouput.UserOutputDto;
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
@RequestMapping("/api/v1/web/users")
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    /**
     * Method to retrieve a list
     * of all users
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserOutputDto>> getAllUsers(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                                              @RequestParam(defaultValue = "4", required = false) int pageSize){
        return ResponseEntity.ok(userService.getAll(pageNumber, pageSize));
    }

    /**
     * Method to save data coming
     * from a file
     * @param file
     * @return
     */
    @PostMapping(path = ("/file"),consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> saveUsersByFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body(Constants.EMPTY_FILE);
        }
        List<UserInputDto> products = readUsersFromFile(file);
        userService.create(products);
        return ResponseEntity.ok().build();
    }

    /**
     * Method to parse the file
     * to a user input dto
     * @param file
     * @return
     */
    private List<UserInputDto> readUsersFromFile(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<List<UserInputDto>>() {
            });
        } catch (Exception e) {
            throw new FileReadingException(Constants.ERROR_PROCESSING_PRODUCT_FILE, 400);
        }
    }
}
