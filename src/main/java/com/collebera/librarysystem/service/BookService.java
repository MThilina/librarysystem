package com.collebera.librarysystem.service;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.mappers.BookMapper;
import com.collebera.librarysystem.model.Book;
import com.collebera.librarysystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO registerBook(BookDTO bookDTO) {
        Book book = BookMapper.INSTANCE.toEntity(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.INSTANCE.toDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}