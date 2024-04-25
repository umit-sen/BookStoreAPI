package com.umitsen.onlinebookstore.Service;

import com.umitsen.onlinebookstore.Entity.Book;

import java.util.List;

public interface BookService {
     List<Book> getAllBooksOrderedByCreationDateDesc();
     Book findByIsbn(String isbn);
     boolean addBook(Book book);
     boolean updateBook(String isbn, Book updatedBook);
     boolean deleteBookById(String isbn);

}
