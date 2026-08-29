package com.Philosophy.PH_Zone.Controller;

import com.Philosophy.PH_Zone.Model.Quote;
import com.Philosophy.PH_Zone.Service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {
    private final QuoteService service;
    @Autowired
    public QuoteController(QuoteService service) {
        this.service=service;
    }



    @GetMapping
    public ResponseEntity<List<Quote>> getAllOrSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String philosopher
    ) {
        if ((keyword == null || keyword.isBlank()) && (philosopher == null || philosopher.isBlank())) {
            return ResponseEntity.ok(service.getAllQuotes());
        }

        return ResponseEntity.ok(service.searchQuotes(keyword, philosopher));
    }

    @GetMapping("/random")
    public ResponseEntity<Quote> getRandomQuote() {
        return ResponseEntity.ok(service.getRandomQuote());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getQuoteById(id));
    }

    @GetMapping("/philosopher/{id}")
    public ResponseEntity<List<Quote>> getQuotesBYPhilosopherId(@PathVariable("id") Long philId) {
        return ResponseEntity.ok(service.getQuotesByPhilosopherId(philId));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<List<Quote>> getQuotesByBookId(@PathVariable("id") Long bId) {
        return ResponseEntity.ok(service.getQuotesByBookId(bId));
    }

    @PostMapping("/philosopher/{philId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Quote> addQuoteToPhilosopher(@PathVariable("philId") Long philoId,@RequestBody Quote quote) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addQuoteToPhilosopher(philoId,quote));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quote) {
        return ResponseEntity.ok(service.updateQuote(id,quote));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        service.deleteQuote(id);
        return ResponseEntity.noContent().build();//204
    }
 }


