package com.netgear.books.booksapi.service;

import com.netgear.books.booksapi.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

import java.util.Date;

public interface BooksApi {
    Page<Book> getAllBooks(int pageNo, int pageSize);
    Book getBookById(Long Id);
    Book createBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long Id);
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(String author);
    List<Book> findBookByGenre(String genre);
    List<Book> findBookByPublishedDate(Date publishedDate);
    List<Book> findBookByKeyword(String keyword);
}
