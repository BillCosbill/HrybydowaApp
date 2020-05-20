package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable long id){
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()){
            System.out.println("Brak książki o podanym id");
            return null;
        } else {
            return book.get();
        }
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book) {
        if (bookRepository.existsByAuthor(book.getAuthor()) && bookRepository.existsByTitle(book.getTitle())) {
            System.out.println("Książka o podanej nazwie i autorze już istnieje!!!");
        }
        book.setAvailable(true);
        bookRepository.save(book);
        System.out.println("Pomyślnie dodano nową książkę!");
    }

    @GetMapping("/bookDelete")
    void deleteBook(@RequestParam long id){
        Optional<Book> book = bookRepository.findById(id);

        if(book.isPresent()){
            bookRepository.delete(book.get());
        }
        else {
            System.out.println("Nie znaleziono książki poprzez id");
        }
    }
}
