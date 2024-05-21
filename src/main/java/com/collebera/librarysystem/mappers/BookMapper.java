package com.collebera.librarysystem.mappers;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book book);

    Book toEntity(BookDTO bookDTO);
}