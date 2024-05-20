package com.collebera.librarysystem.service;


import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.dto.BorrowerDTO;
import com.collebera.librarysystem.model.Book;
import com.collebera.librarysystem.model.Borrower;
import com.collebera.librarysystem.repository.BookRepository;
import com.collebera.librarysystem.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterBorrower() {
        BorrowerDTO borrowerDTO = new BorrowerDTO();
        borrowerDTO.setName("John Doe");
        borrowerDTO.setEmail("john@example.com");

        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");
        borrower.setEmail("john@example.com");

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        BorrowerDTO registeredBorrower = borrowerService.registerBorrower(borrowerDTO);

        assertNotNull(registeredBorrower);
        assertEquals(1L, registeredBorrower.getId());
        assertEquals("John Doe", registeredBorrower.getName());
        assertEquals("john@example.com", registeredBorrower.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    void testBorrowBook() {
        Long borrowerId = 1L;
        Long bookId = 1L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(false);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO borrowedBook = borrowerService.borrowBook(borrowerId, bookId);

        assertNotNull(borrowedBook);
        assertEquals(bookId, borrowedBook.getId());
        assertTrue(book.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testBorrowBookThrowsExceptionWhenBorrowerNotFound() {
        Long borrowerId = 1L;
        Long bookId = 1L;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowerService.borrowBook(borrowerId, bookId);
        });

        assertEquals("Borrower not found", exception.getMessage());
    }

    @Test
    void testBorrowBookThrowsExceptionWhenBookNotFound() {
        Long borrowerId = 1L;
        Long bookId = 1L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowerService.borrowBook(borrowerId, bookId);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void testReturnBook() {
        Long borrowerId = 1L;
        Long bookId = 1L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(true);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO returnedBook = borrowerService.returnBook(borrowerId, bookId);

        assertNotNull(returnedBook);
        assertEquals(bookId, returnedBook.getId());
        assertFalse(book.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBookThrowsExceptionWhenBookNotBorrowed() {
        Long borrowerId = 1L;
        Long bookId = 1L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        Book book = new Book();
        book.setId(bookId);
        book.setBorrowed(false);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowerService.returnBook(borrowerId, bookId);
        });

        assertEquals("Book was not borrowed", exception.getMessage());
    }
}
