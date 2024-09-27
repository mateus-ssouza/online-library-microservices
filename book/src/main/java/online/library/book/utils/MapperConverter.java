package online.library.book.utils;

import org.modelmapper.ModelMapper;

public class MapperConverter {
     private static final ModelMapper modelMapper = new ModelMapper();

     // Convert from Model to DTO
    public static <D, T> D convertToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    // Convert from DTO to Model
    public static <D, T> T convertToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
