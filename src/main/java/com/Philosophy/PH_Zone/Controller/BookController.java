package com.Philosophy.PH_Zone.Controller;

import com.Philosophy.PH_Zone.Model.Book;
import com.Philosophy.PH_Zone.Model.Philosopher;
import com.Philosophy.PH_Zone.Service.BookService;
import com.Philosophy.PH_Zone.Service.PhilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://noesis-pnqta551i-draken1.vercel.app")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService service;
    private final PhilService pService;
    @Autowired
    public BookController(BookService service,PhilService pService) {
        this.service=service;
        this.pService=pService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllOrSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String philosopher
    ) {

        if ((title == null || title.isBlank()) && (philosopher == null || philosopher.isBlank())) {
            return ResponseEntity.ok(service.getAllBooks());
        }


        List<Book> books = service.searchBooks(title, philosopher);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping("/philosopher/{id}")
    public ResponseEntity<List<Book>> getBookByPhilosopherId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBooksByPhilosopherId(id));
    }

    //CRUD

    @PostMapping("/philosopher/{philosopherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBookToPhilosopher(@PathVariable Long philosopherId,@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBookToPhilosopher(philosopherId,book));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id,book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build(); //204
    }


}

