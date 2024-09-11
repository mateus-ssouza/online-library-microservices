package online.library.account.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import online.library.account.dtos.CreateUserRequestDto;
import online.library.account.dtos.UpdateUserRequestDto;
import online.library.account.dtos.UserResponseDto;
import online.library.account.models.User;
import online.library.account.services.UserService;
import online.library.account.utils.MapperConverter;
import online.library.account.utils.ValidationErrors;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {

    @Autowired
    private UserService _userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        try {
            var users = _userService.getAll();
            var usersDto = users.stream()
                    .map(user -> MapperConverter.convertToDto(user, UserResponseDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(usersDto);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        try {
            return _userService.getById(id)
                    .map(user -> ResponseEntity.ok(MapperConverter.convertToDto(user, UserResponseDto.class)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto userRequestDto,
            BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var user = MapperConverter.convertToEntity(userRequestDto, User.class);
            var savedUser = _userService.create(user);
            var userResponseDto = MapperConverter.convertToDto(savedUser, UserResponseDto.class);

            return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto userRequestDto, BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var user = MapperConverter.convertToEntity(userRequestDto, User.class);
            var updatedUser = _userService.update(id, user);
            var userResponseDto = MapperConverter.convertToDto(updatedUser, UserResponseDto.class);

            return ResponseEntity.ok(userResponseDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            _userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }
}
