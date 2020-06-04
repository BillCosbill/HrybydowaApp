package pl.edu.pb.wi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.dao.entity.Book;
import pl.edu.pb.wi.service.BookService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping
    public Book getBook(@RequestParam Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/rentBook")
    public void rentBook(@RequestParam Long idBook, @RequestParam Long idUser) {
        bookService.rentBook(idBook, idUser);
    }

    @GetMapping("/returnBook")
    public void returnBook(@RequestParam Long idBook, @RequestParam Long idUser) {
        bookService.returnBook(idBook, idUser);
    }

}
