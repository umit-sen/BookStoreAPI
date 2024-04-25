package com.umitsen.onlinebookstore.Service;
import com.umitsen.onlinebookstore.Repository.BookRepository;
import com.umitsen.onlinebookstore.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceimp implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceimp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> getAllBooksOrderedByCreationDateDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "CreatedAt");
        return bookRepository.findAll(sort);
    }
    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }


    @Override
    public boolean addBook(Book book) {
        try {

            bookRepository.save(book);

            return true; // Kitap başarıyla eklendi
        } catch (Exception e) {

            return false; // Kitap eklenirken bir hata oluştu
        }
    }
    @Override
    public boolean updateBook(String isbn, Book updatedBook) {
        // ISBN numarasına sahip kitabı veritabanında bul
        Book existingBook = bookRepository.findByIsbn(isbn);

        // Eğer kitap mevcutsa güncelleme işlemini gerçekleştir
        if (existingBook != null) {
            // Güncellenmiş kitap detaylarını atama
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setStockQuantity(updatedBook.getStockQuantity());
            // Güncellenmiş kitap detaylarını kaydet
            bookRepository.save(existingBook);
            return true;
        } else {

            return false;
        }
    }
    @Override
    public boolean deleteBookById(String isbn){
        Book book = bookRepository.findByIsbn(isbn);
        if(book != null) {
            bookRepository.delete(book);
            return true;
        }else
            return false;
    }

}


