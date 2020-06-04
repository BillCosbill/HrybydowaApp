package pl.edu.pb.wi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dao.BookRepository;
import pl.edu.pb.wi.dao.UserRepository;
import pl.edu.pb.wi.dao.entity.Book;
import pl.edu.pb.wi.dao.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBook(Long id){
        return bookRepository.findById(id).orElse(null);
    }
    
    public Book addBook(Book book) {
        if (bookRepository.existsByAuthor(book.getAuthor()) && bookRepository.existsByTitle(book.getTitle())) {
            System.out.println("Książka o podanej nazwie i autorze już istnieje!!!");
        }
        book.setAvailable(true);
        bookRepository.save(book);
        System.out.println("Pomyślnie dodano nową książkę!");
        return book;
    }

    public void deleteBook(Long id){
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

    public void rentBook(Long idBook, Long idUser){
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

    public void returnBook(Long idBook, Long idUser){
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

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        addBook(new Book("LOTR", "J.R.R. Tolkien", 1937));
        addBook(new Book("Mars", "R. Kosik", 2003));
        addBook(new Book("Siewca Wiatru", "M. L. Kossakowska", 2004));
        addBook(new Book("Gra o Tron", "G.R.R. Martin", 1996));
    }
}
