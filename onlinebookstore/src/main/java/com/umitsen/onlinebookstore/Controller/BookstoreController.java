package com.umitsen.onlinebookstore.Controller;

import com.umitsen.onlinebookstore.Entity.Book;
import com.umitsen.onlinebookstore.Service.BookServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class BookstoreController {

    private final BookServiceimp bookServiceimp;

    @Autowired
    public BookstoreController(BookServiceimp bookServiceimp){
    this.bookServiceimp = bookServiceimp;
}
    // Book Endpoints

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooksOrderedByCreationDateDesc() {
        List<Book> books = bookServiceimp.getAllBooksOrderedByCreationDateDesc();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/books/{isbn}")
    @PreAuthorize("hasRole('USER')")
    public Book getBookById(@PathVariable String isbn){
        return bookServiceimp.findByIsbn(isbn);
    }
    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addBook(@RequestBody Book bookDTO) {

        boolean success = bookServiceimp.addBook(bookDTO);

        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book");
        }
    }

    @PutMapping("/books/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBookDetails(@PathVariable String isbn, @RequestBody Book book){

       boolean result = bookServiceimp.updateBook(isbn,book);
        if(result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book updated successfully");
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book ISBN not found");
    }
    @DeleteMapping("/books/{isbn}")
    @PreAuthorize("hasRole('ADMIM')")
    public ResponseEntity<String> deleteByISBN(@PathVariable String isbn){
       boolean result = bookServiceimp.deleteBookById(isbn);
       if (result){
           return ResponseEntity.status(HttpStatus.OK).body("Book deleted");

       }else
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }

}


