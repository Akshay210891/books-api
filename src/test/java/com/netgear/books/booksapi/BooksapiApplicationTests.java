package com.netgear.books.booksapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netgear.books.booksapi.entity.Book;
import com.netgear.books.booksapi.exception.ResourceNotFoundException;
import com.netgear.books.booksapi.repository.BookRepository;
import com.netgear.books.booksapi.service.BooksApi;
import com.netgear.books.booksapi.service.BooksApiImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class BooksapiApplicationTests  {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private Book testBook;

	@InjectMocks
	private BooksApiImpl booksApi;
    @Autowired
    private BooksApiImpl booksApiImpl;

	@BeforeEach
	@JsonFormat(pattern = "dd-MM-yyyy")
	void setUp() {
		String tempdate = "01-01-2025";
		Date BookDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try{
			BookDate = formatter.parse(tempdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
		testBook = new Book("Test Book", "Akshay Chawla", BookDate,"Fiction");
	}

	//@Test
	void testGetAllBooks() {
		when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook));
		Page<Book> testBook = booksApi.getAllBooks(0,5);
		Mockito.when(testBook.getTotalElements()).thenReturn(1L);
		assertEquals(1, testBook.getTotalElements());
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testGetBookById() {
		when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
		Book book = booksApi.getBookById(1L);
		assertEquals(testBook, book);
		verify(bookRepository, times(1)).findById(1L);
	}

	@Test
	void testGetBookById_NotFOund() {
		when(bookRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> booksApi.getBookById(1L));
	}

	@Test
	void updateBook() {
		String tempdate = "01-12-2024";
		Date updatedDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try{
			updatedDate = formatter.parse(tempdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Book updatedBook = new Book("Updated title", "Updated Author",updatedDate, "Drama");
		updatedBook.setId(1L);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
		when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

		Book result = booksApi.updateBook(updatedBook);
		result.setId(1L);

		assertEquals(updatedBook.getTitle(), result.getTitle());
		assertEquals(updatedBook.getAuthor(), result.getAuthor());
		assertEquals(updatedBook.getPublishedDate(), result.getPublishedDate());
		verify(bookRepository, times(1)).findById(1L);
		verify(bookRepository, times(1)).save(any(Book.class));
	}

}
