package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@CrossOrigin(origins = "*")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

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
        AtomicBoolean isForeignKey = new AtomicBoolean(false);

        if(book.isPresent()){
            //książka jest kluczem obcym w innej tabeli
            userRepository.findAll().forEach(x -> {
                if(x.getBooks().contains(book.get())){
                    isForeignKey.set(true);
                }
            });

            if(isForeignKey.get()){
                System.out.println("Nie można usunąć książki. Jest ona przypisana do przynajmniej jednego uzytkownika");
            } else {
                bookRepository.delete(book.get());
                System.out.println("Pomyślnie usunięto książkę");
            }
        }
        else {
            System.out.println("Nie znaleziono książki poprzez id");
        }
    }


    @GetMapping("/rentBook")
    public void rentBook(@RequestParam long idBook, @RequestParam long idUser){
        Optional<Book> book = bookRepository.findById(idBook);
        Optional<User> user = userRepository.findById(idUser);

        if(book.isPresent() && user.isPresent()){

            if(book.get().isAvailable()){
                book.get().setAvailable(false);
                user.get().addBook(book.get());

                bookRepository.save(book.get());
                userRepository.save(user.get());

                System.out.println("Pomyślnie wypożyczono książkę");
            } else {
                System.out.println("Książka jest niedostępna");
            }

        } else {
            System.out.println("Książka o id " + idBook + ", lub użytkownik o id " + idUser +" - nie istnieje");
        }
    }

    @GetMapping("/returnBook")
    public void returnBook(@RequestParam long idBook, @RequestParam long idUser){
        Optional<Book> book = bookRepository.findById(idBook);
        Optional<User> user = userRepository.findById(idUser);

        if(book.isPresent() && user.isPresent()){

            if(!book.get().isAvailable()){
                book.get().setAvailable(true);
                user.get().removeBook(book.get());

                bookRepository.save(book.get());
                userRepository.save(user.get());

                System.out.println(user.get().getBooks());
                System.out.println("Pomyślnie zwrócono książkę");
            } else {
                System.out.println("Książka jest już zwrócona");
            }

        } else {
            System.out.println("Książka o id " + idBook + ", lub użytkownik o id " + idUser +" - nie istnieje");
        }
    }

}
