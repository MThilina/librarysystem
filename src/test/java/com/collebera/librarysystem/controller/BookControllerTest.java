package com.collebera.librarysystem.controller;

import com.collebera.librarysystem.dto.BookDTO;
import com.collebera.librarysystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO();
        bookDTO.setIsbn("1234567890");
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Author");
    }

    @Test
    void testRegisterBook() throws Exception {
        BookDTO returnedBookDTO = new BookDTO();
        returnedBookDTO.setId(1L);
        returnedBookDTO.setIsbn("1234567890");
        returnedBookDTO.setTitle("Test Book");
        returnedBookDTO.setAuthor("Author");

        Mockito.when(bookService.registerBook(Mockito.any(BookDTO.class))).thenReturn(returnedBookDTO);

        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.isbn", is("1234567890")))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    void testRegisterBookValidationErrors() throws Exception {
        BookDTO invalidBookDTO = new BookDTO();
        invalidBookDTO.setIsbn("");  // Invalid ISBN

        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<BookDTO> bookList = Arrays.asList(bookDTO);

        Mockito.when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].isbn", is("1234567890")))
                .andExpect(jsonPath("$[0].title", is("Test Book")))
                .andExpect(jsonPath("$[0].author", is("Author")));
    }
}
