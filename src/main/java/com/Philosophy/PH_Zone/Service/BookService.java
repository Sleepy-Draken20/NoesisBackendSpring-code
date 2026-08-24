package com.Philosophy.PH_Zone.Service;

import com.Philosophy.PH_Zone.Model.Book;
import com.Philosophy.PH_Zone.Model.Philosopher;
import com.Philosophy.PH_Zone.Repository.BookRepo;
import com.Philosophy.PH_Zone.Repository.PhilosopherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepo repo;
    private final PhilosopherRepo philosopherRepo;

    @Autowired
    public BookService(BookRepo repo, PhilosopherRepo philosopherRepo) {
        this.repo = repo;
        this.philosopherRepo=philosopherRepo;
    }


    // BOOKS & QUOTES
    public List<Book> getAllBooks(){
        return repo.findAll();
    }

    public Book getBookById(Long id) {
        return repo.findById(id).orElseThrow( () -> new RuntimeException("book Not Found : ("));
    }
    public List<Book> searchBooks(String title, String philosopherName) {
        String searchTitle = (title != null && !title.isBlank()) ? title : null;
        String searchPhilosopher = (philosopherName != null && !philosopherName.isBlank()) ? philosopherName : null;

        List<Book> books = repo.searchBooksDynamic(searchTitle, searchPhilosopher);

        if (books.isEmpty()) {
            throw new RuntimeException("No books found matching your search criteria :(");
        }

        return books;
    }


    public List<Book> getBooksByPhilosopherId(Long philosopherId) {
        boolean philosopherExists = philosopherRepo.existsById(philosopherId);
        if (!philosopherExists) {
            throw new RuntimeException("Philosopher with ID " + philosopherId + " does not exist.");
        }

        return repo.findBooksByPhilosopherId(philosopherId);
    }


    // CRUD
    public Book addBookToPhilosopher(Long philosopherId, Book book) {
        Philosopher philosopher = philosopherRepo.findById(philosopherId)
                .orElseThrow(() -> new RuntimeException("Philosopher not found with id: " + philosopherId));
        boolean isDuplicate = repo.existsByTitleIgnoreCaseAndPhilosopherId(book.getTitle(), philosopherId);
        if (isDuplicate) {
            throw new RuntimeException("This book already exists for this philosopher!");
        }
        book.setPhilosopher(philosopher);

        return repo.save(book);
    }

    public void deleteBook(Long id) {
        boolean exists = repo.existsById(id);
        if(!exists) {
            throw new RuntimeException("This Book Does Not Exists : (");
        }
        repo.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        Book updater = repo.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found : ("));

        updater.setTitle(book.getTitle());
        updater.setBookImg(book.getBookImg());
        updater.setPublicationYear(book.getPublicationYear());
        updater.setSummary(book.getSummary());
        return repo.save(updater);
    }
}
