package com.collebera.librarysystem.service;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.dto.BorrowerDTO;
import com.collebera.librarysystem.mappers.BookMapper;
import com.collebera.librarysystem.mappers.BorrowerMapper;
import com.collebera.librarysystem.model.Book;
import com.collebera.librarysystem.model.Borrower;
import com.collebera.librarysystem.repository.BookRepository;
import com.collebera.librarysystem.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

    public BorrowerDTO registerBorrower(BorrowerDTO borrowerDTO) {
        Borrower borrower = BorrowerMapper.INSTANCE.toEntity(borrowerDTO);
        borrower = borrowerRepository.save(borrower);
        return BorrowerMapper.INSTANCE.toDTO(borrower);
    }

    public BookDTO borrowBook(Long borrowerId, Long bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.isBorrowed()) {
            throw new RuntimeException("Book is already borrowed");
        }

        book.setBorrowed(true);
        bookRepository.save(book);
        return BookMapper.INSTANCE.toDTO(book);
    }

    public BookDTO returnBook(Long borrowerId, Long bookId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isBorrowed()) {
            throw new RuntimeException("Book was not borrowed");
        }

        book.setBorrowed(false);
        bookRepository.save(book);
        return BookMapper.INSTANCE.toDTO(book);
    }
}