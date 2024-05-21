package com.collebera.librarysystem.controller;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.dto.BorrowerDTO;
import com.collebera.librarysystem.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @Operation(summary = "Register a new borrower")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Borrower registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<BorrowerDTO> registerBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO) {
        BorrowerDTO createdBorrower = borrowerService.registerBorrower(borrowerDTO);
        return new ResponseEntity<>(createdBorrower, HttpStatus.CREATED);
    }

    @Operation(summary = "Borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book borrowed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid borrower or book ID"),
            @ApiResponse(responseCode = "400", description = "Book already borrowed"),
            @ApiResponse(responseCode = "404", description = "Book or Borrower details not found")
    })
    @PatchMapping("/{borrowerId}/books-borrow/{bookId}")
    public ResponseEntity<BookDTO> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        BookDTO book = borrowerService.borrowBook(borrowerId, bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Return a borrowed book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid borrower or book ID")
    })
    @PatchMapping("/{borrowerId}/books-return/{bookId}")
    public ResponseEntity<BookDTO> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        BookDTO book = borrowerService.returnBook(borrowerId, bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}