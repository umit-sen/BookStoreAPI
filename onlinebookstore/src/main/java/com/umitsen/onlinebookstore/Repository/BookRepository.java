package com.umitsen.onlinebookstore.Repository;

import com.umitsen.onlinebookstore.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByIsbn(String isbn);

    Optional<Book> findBookByIsbn(String isbn);
}
