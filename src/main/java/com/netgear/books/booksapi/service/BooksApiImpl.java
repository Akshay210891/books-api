package com.netgear.books.booksapi.service;

import com.netgear.books.booksapi.entity.Book;
import com.netgear.books.booksapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BooksApiImpl implements BooksApi {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookById(Long Id) {
        return bookRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Book not found for id :"+ Id));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book currentBook = bookRepository.findById(book.getId()).get();
        currentBook.setTitle(book.getTitle());
        currentBook.setAuthor(book.getAuthor());
        currentBook.setPublishedDate(book.getPublishedDate());
        currentBook.setGenre(book.getGenre());
        Book updatedBook = bookRepository.save(currentBook);
        return updatedBook;
    }

    @Override
    public void deleteBook(Long Id) {
        bookRepository.deleteById(Id);
    }

    @Override
    public Page<Book> getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return bookRepository.findBookByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findBookByAuthor(author);
    }

    @Override
    public List<Book> findBookByGenre(String genre) {
        return bookRepository.findBookByGenre(genre);
    }

    @Override
    public List<Book> findBookByPublishedDate(Date publisheddate) {
        return bookRepository.findBookByPublishedDateGreaterThan(publisheddate);
    }

    @Override
    public List<Book> findBookByKeyword(String keyword) {
        return bookRepository.findBookByKeyword(keyword);
    }
}
