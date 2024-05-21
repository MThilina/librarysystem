package com.collebera.librarysystem.controller;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.dto.BorrowerDTO;
import com.collebera.librarysystem.exception.NotfoundException;
import com.collebera.librarysystem.service.BorrowerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowerController.class)
class BorrowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowerDTO borrowerDTO;

    @BeforeEach
    void setUp() {
        borrowerDTO = new BorrowerDTO();
        borrowerDTO.setName("John Doe");
        borrowerDTO.setEmail("john@example.com");
    }

    @Test
    void testRegisterBorrower() throws Exception {
        BorrowerDTO returnedBorrowerDTO = new BorrowerDTO();
        returnedBorrowerDTO.setId(1L);
        returnedBorrowerDTO.setName("John Doe");
        returnedBorrowerDTO.setEmail("john@example.com");

        Mockito.when(borrowerService.registerBorrower(Mockito.any(BorrowerDTO.class))).thenReturn(returnedBorrowerDTO);

        mockMvc.perform(post("/api/library/borrowers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    void testRegisterBorrowerValidationErrors() throws Exception {
        BorrowerDTO invalidBorrowerDTO = new BorrowerDTO();
        invalidBorrowerDTO.setName("");  // Invalid name

        mockMvc.perform(post("/api/library/borrowers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBorrowerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

    }

    @Test
    void testBorrowBook() throws Exception {
        Long borrowerId = 1L;
        Long bookId = 1L;

        BookDTO returnedBookDTO = new BookDTO();
        returnedBookDTO.setId(1L);
        returnedBookDTO.setIsbn("1234567890");
        returnedBookDTO.setTitle("Test Book");
        returnedBookDTO.setAuthor("Author");

        Mockito.when(borrowerService.borrowBook(borrowerId,bookId)).thenReturn(returnedBookDTO);

        mockMvc.perform(patch("/api/library/borrowers/{borrowerId}/books-borrow/{bookId}", borrowerId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    void testBorrowBookWithInvalidIds() throws Exception {
        Long borrowerId = 999L;
        Long bookId = 999L;

        Mockito.when(borrowerService.borrowBook(borrowerId,bookId)).thenThrow(new NotfoundException("Borrower not found"));

        mockMvc.perform(patch("/api/library/borrowers/{borrowerId}/books-borrow/{bookId}", borrowerId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Test
    void testReturnBook() throws Exception {
        Long borrowerId = 1L;
        Long bookId = 1L;

        BookDTO returnedBookDTO = new BookDTO();
        returnedBookDTO.setId(1L);
        returnedBookDTO.setIsbn("1234567890");
        returnedBookDTO.setTitle("Test Book");
        returnedBookDTO.setAuthor("Author");

        Mockito.when(borrowerService.returnBook(borrowerId,bookId)).thenReturn(returnedBookDTO);

        mockMvc.perform(patch("/api/library/borrowers/{borrowerId}/books-return/{bookId}", borrowerId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    void testReturnBookWithInvalidIds() throws Exception {
        Long borrowerId = 999L;
        Long bookId = 999L;

        Mockito.when(borrowerService.returnBook(borrowerId,bookId)).thenThrow(new NotfoundException("Borrower not found"));

        mockMvc.perform(patch("/api/library/borrowers/{borrowerId}/books-return/{bookId}", borrowerId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", notNullValue()));
    }
}
