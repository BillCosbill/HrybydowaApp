package pl.edu.pb.wi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.dao.entity.Book;
import pl.edu.pb.wi.payload.request.RentalRequest;
import pl.edu.pb.wi.serviceInterface.BookService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/rent")
    public void rentBook(@RequestBody RentalRequest rentalRequest) {
        bookService.rentBook(rentalRequest.getBookId(), rentalRequest.getUserId());
    }

    @PutMapping("/return")
    public void returnBook(@RequestBody RentalRequest rentalRequest) {
        bookService.returnBook(rentalRequest.getBookId(), rentalRequest.getUserId());
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
    }
}
