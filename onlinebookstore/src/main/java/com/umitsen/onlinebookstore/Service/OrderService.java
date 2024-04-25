package com.umitsen.onlinebookstore.Service;

import com.umitsen.onlinebookstore.Entity.*;
import com.umitsen.onlinebookstore.Exception.*;
import com.umitsen.onlinebookstore.Repository.BookRepository;
import com.umitsen.onlinebookstore.Repository.OrderRepository;
import com.umitsen.onlinebookstore.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void placeNewOrder(OrderRequest orderRequest) throws InsufficientStockException, BookNotFoundException, InsufficientOrderTotalException, UserNotFoundException {
        // Convert the incoming OrderRequest into an Order

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(currentPrincipalName);

        Order newOrder = new Order();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // optionalUser içinde kullanıcı varsa işlem yapın

            newOrder.setUserId(user.getId());// Set the user who placed the order
        } else {
            // optionalUser içinde kullanıcı yoksa ne yapılacağını belirtin
            // Örneğin, bir hata fırlatabilir veya varsayılan bir kullanıcı oluşturabilirsiniz.
            throw new UserNotFoundException("User not found for email: " + currentPrincipalName);
        }
        double totalPrice = 0;

        // Create a list to store the Book objects
        List<Book> books = new ArrayList<>();

        for (BookRequest bookRequest : orderRequest.getBooks()) {

            Optional<Book> optionalBook = bookRepository.findBookByIsbn(bookRequest.getIsbn());

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                int requestedQuantity = bookRequest.getQuantity();

                if (requestedQuantity <= book.getStockQuantity()) {
                    Book newBook = new Book();
                    newBook.setIsbn(book.getIsbn());
                    newBook.setTitle(book.getTitle());
                    newBook.setAuthor(book.getAuthor());


                    books.add(newBook);

                    book.setStockQuantity(book.getStockQuantity() - requestedQuantity);
                    double fee;
                    fee = book.getPrice() * requestedQuantity;
                    totalPrice = totalPrice + fee;

                    books.add(book);
                } else {
                    // Handle insufficient stock
                    throw new InsufficientStockException("Insufficient stock for ISBN: " + book.getIsbn());
                }
            } else {
                // Handle book not found
                throw new BookNotFoundException("Book not found for ISBN: " + bookRequest.getIsbn());
            }
        }


        // Set the total price and books for the order
        newOrder.setTotalPrice(totalPrice);



        newOrder.setBooks(books);

        if (totalPrice < 25.0) {
            throw new InsufficientOrderTotalException("Order total is less than 25 dollars.");
        }

        orderRepository.save(newOrder);

        System.out.println( newOrder.getBooks());



    }

    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findAllByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Map<String, Object> getOrderDetailsById(Long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        List<Book> booksInOrder = order.getBooks(); // Assuming you have a proper relationship between Order and Book entities

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("order", order);
        orderDetails.put("books", booksInOrder);

        return orderDetails;
    }



}
