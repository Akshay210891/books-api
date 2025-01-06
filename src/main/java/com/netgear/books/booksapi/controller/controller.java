package com.netgear.books.booksapi.controller;

import ch.qos.logback.core.pattern.parser.Parser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netgear.books.booksapi.entity.Book;
import com.netgear.books.booksapi.exception.ResourceNotFoundException;
import com.netgear.books.booksapi.service.BooksApi;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("v1/api/books")
public class controller {
    @Autowired
    private static final Logger log = LoggerFactory.getLogger(controller.class);

    @Autowired
    private final BooksApi booksApi;

    controller(BooksApi booksApi) {
        this.booksApi = booksApi;
    }

    @GetMapping
    public ResponseEntity<Page<Book>>getAllBooks(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Page<Book> booksList = booksApi.getAllBooks(pageNo, pageSize);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Book> getBookById(@PathVariable("Id") Long Id) {
        log.info("Finding book by code: {}", Id);
        Book book = booksApi.getBookById(Id);
        return new ResponseEntity<>(book, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        log.info("Adding Book: {}", book);
        Book createdbook = booksApi.createBook(book);
        return new ResponseEntity<>(createdbook, CREATED);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Book> updateBook(@PathVariable("Id") Long Id, @RequestBody Book book) {
        log.info("Updated Book with Id: {}", Id);
        Book updatedbook = booksApi.updateBook(book);
        return new ResponseEntity<>(updatedbook, HttpStatus.OK);
        //Optional<Book> updated = booksApi.updateBook(Id, updateBook);
        //return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("Id") Long Id) {
        log.info("Deleting book with Id: {}", Id);
        booksApi.deleteBook(Id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author) {
        log.info("Finding books by Author: {}", author);
        List<Book> booksList = booksApi.findBookByAuthor(author);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        log.info("Finding books by Title: {}", title);
        List<Book> booksList = booksApi.findBookByTitle(title);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getBookByGenre(@PathVariable String genre) {
        log.info("Finding books by Genre: {}", genre);
        List<Book> booksList = booksApi.findBookByGenre(genre);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

    @GetMapping("/date/{pdate}")
    @JsonFormat(pattern = "dd-MM-yyy")
    public ResponseEntity<List<Book>> getBookByPublishedDate(@PathVariable String pdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date publishedDate = null;
        try {
            publishedDate = formatter.parse(pdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        log.info("Finding books by PublishedDate: {}", publishedDate);
        List<Book> booksList = booksApi.findBookByPublishedDate(publishedDate);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Book>> findBookByKeyword(@PathVariable String keyword) {
        log.info("Finding books by Genre: {}", keyword);
        List<Book> booksList = booksApi.findBookByKeyword(keyword);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
        //return booksApi.getBookById(Id).map(ResponseEntity::ok).orElseThrow(() -> BookNotFoundException.forId(Id));
    }

}
