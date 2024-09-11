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
import online.library.account.services.AdminService;
import online.library.account.utils.MapperConverter;
import online.library.account.utils.ValidationErrors;

@RestController
@RequestMapping("api/v1/admins")
public class AdminsController {

    @Autowired
    private AdminService _adminService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllAdmins() {
        try {
            var admins = _adminService.getAll();
            var adminsDto = admins.stream()
                    .map(admin -> MapperConverter.convertToDto(admin, UserResponseDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(adminsDto);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getAdminById(@PathVariable Long id) {
        try {
            return _adminService.getById(id)
                    .map(admin -> ResponseEntity.ok(MapperConverter.convertToDto(admin, UserResponseDto.class)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createAdmin(@Valid @RequestBody CreateUserRequestDto adminRequestDto,
            BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var admin = MapperConverter.convertToEntity(adminRequestDto, User.class);
            var savedUser = _adminService.create(admin);
            var adminResponseDto = MapperConverter.convertToDto(savedUser, UserResponseDto.class);

            return new ResponseEntity<>(adminResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateAdmin(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto adminRequestDto, BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var admin = MapperConverter.convertToEntity(adminRequestDto, User.class);
            var updatedUser = _adminService.update(id, admin);
            var adminResponseDto = MapperConverter.convertToDto(updatedUser, UserResponseDto.class);

            return ResponseEntity.ok(adminResponseDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
            _adminService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }
}
