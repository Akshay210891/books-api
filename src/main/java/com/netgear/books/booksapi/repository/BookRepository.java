package com.netgear.books.booksapi.repository;
import com.netgear.books.booksapi.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findBookByAuthor(String author);

    List<Book> findBookByTitleContainingIgnoreCase(String title);

    List<Book> findBookByGenre(String genre);

    List<Book> findBookByPublishedDateGreaterThan(Date publishedDate);

    Page<Book> findAll(Pageable pageable);

    @Autowired
    @Query(value = "SELECT * FROM Book WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(author) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true )
    List<Book> findBookByKeyword(@Param("keyword") String keyword);


}