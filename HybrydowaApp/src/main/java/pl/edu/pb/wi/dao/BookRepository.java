package pl.edu.pb.wi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.dao.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Boolean existsByTitle(String title);
    Boolean existsByAuthor(String author);

    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthor(String author);
}
