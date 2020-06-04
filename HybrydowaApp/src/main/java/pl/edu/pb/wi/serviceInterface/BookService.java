package pl.edu.pb.wi.serviceInterface;

import pl.edu.pb.wi.dao.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBooks();

    Book getBook(Long id);

    Book addBook(Book book);

    void deleteBook(Long id);

    void rentBook(Long bookId, Long userId);

    void returnBook(Long bookId, Long userId);
}
