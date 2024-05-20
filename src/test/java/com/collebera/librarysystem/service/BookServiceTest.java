package com.collebera.librarysystem.service;


import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.model.Book;
import com.collebera.librarysystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("1234567890");
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Author");

        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Test Book");
        book.setAuthor("Author");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO registeredBook = bookService.registerBook(bookDTO);

        assertNotNull(registeredBook);
        assertEquals("1234567890", registeredBook.getIsbn());
        assertEquals("Test Book", registeredBook.getTitle());
        assertEquals("Author", registeredBook.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbn("1234567890");
        book1.setTitle("Test Book 1");
        book1.setAuthor("Author 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbn("0987654321");
        book2.setTitle("Test Book 2");
        book2.setAuthor("Author 2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<BookDTO> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(1L, books.get(0).getId());
        assertEquals("1234567890", books.get(0).getIsbn());
        assertEquals("Test Book 1", books.get(0).getTitle());
        assertEquals("Author 1", books.get(0).getAuthor());
        assertEquals(2L, books.get(1).getId());
        assertEquals("0987654321", books.get(1).getIsbn());
        assertEquals("Test Book 2", books.get(1).getTitle());
        assertEquals("Author 2", books.get(1).getAuthor());
        verify(bookRepository, times(1)).findAll();
    }
}
