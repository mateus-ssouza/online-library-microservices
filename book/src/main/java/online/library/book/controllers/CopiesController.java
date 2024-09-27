package online.library.book.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import online.library.book.dtos.CreateCopyRequestDto;
import online.library.book.dtos.CopyResponseDto;
import online.library.book.services.CopyService;
import online.library.book.utils.MapperConverter;
import online.library.book.utils.ValidationErrors;

@RestController
@RequestMapping("api/v1/copies")
public class CopiesController {

    @Autowired
    private CopyService _copyService;

    @GetMapping
    public ResponseEntity<List<CopyResponseDto>> getAllCopys() {
        try {
            var copies = _copyService.getAll();
            var copiesDto = copies.stream()
                    .map(copy -> MapperConverter.convertToDto(copy, CopyResponseDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(copiesDto);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CopyResponseDto> getCopyById(@PathVariable Long id) {
        try {
            return _copyService.getById(id)
                    .map(copy -> ResponseEntity.ok(MapperConverter.convertToDto(copy, CopyResponseDto.class)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<CopyResponseDto> createCopy(@Valid @RequestBody CreateCopyRequestDto copyRequestDto,
            BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var savedCopy = _copyService.create(copyRequestDto.getTitle());
            var copyResponseDto = MapperConverter.convertToDto(savedCopy, CopyResponseDto.class);

            return new ResponseEntity<>(copyResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) {
        try {
            _copyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }
}
