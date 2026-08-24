package com.Philosophy.PH_Zone.Service;

import com.Philosophy.PH_Zone.Model.Philosopher;
import com.Philosophy.PH_Zone.Model.Quote;
import com.Philosophy.PH_Zone.Repository.PhilosopherRepo;
import com.Philosophy.PH_Zone.Repository.QuoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    private final QuoteRepo repo;
    private final PhilosopherRepo philosopherRepo;

    @Autowired
    public QuoteService(QuoteRepo repo, PhilosopherRepo philosopherRepo) {
        this.repo=repo;
        this.philosopherRepo=philosopherRepo;
    }

    public List<Quote> getAllQuotes() {
        return repo.findAll();
    }

    public List<Quote> getQuotesByPhilosopher(String name) {
        boolean exists = philosopherRepo.existsByNameContainingIgnoreCase(name);
        if (!exists) {
            throw new RuntimeException("Philosopher " + name + " Not Found : (");
        }
        List<Quote> quotes = repo.findByPhilosopherNameContainingIgnoreCase(name);

        if (quotes.isEmpty()) {
            throw new RuntimeException("No quotes found for philosopher: " + name);
        }

        return quotes;
    }

    public Quote getQuoteById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote Not Found with id: " + id));
    }

    public Quote getRandomQuote() {
        Quote randomQuote = repo.findRandomQuote();
        if (randomQuote == null) {
            throw new RuntimeException("No quotes available in database!");
        }
        return randomQuote;
    }

    public List<Quote> getQuotesByPhilosopherId(Long philosopherId) {
        if (!philosopherRepo.existsById(philosopherId)) {
            throw new RuntimeException("Philosopher Not Found with id: " + philosopherId);
        }
        return repo.findByPhilosopherId(philosopherId);
    }

    public List<Quote> getQuotesByBookId(Long bookId) {
        List<Quote> quotes = repo.findByBook_Id(bookId);
        if (quotes.isEmpty()) {
            throw new RuntimeException("No quotes found for Book ID: " + bookId);
        }
        return quotes;
    }

    public List<Quote> searchQuotes(String keyword, String philosopherName) {
        String searchKeyword = (keyword != null && !keyword.isBlank()) ? keyword : null;
        String searchPhilosopher = (philosopherName != null && !philosopherName.isBlank()) ? philosopherName : null;

        List<Quote> quotes = repo.searchQuotesDynamic(searchKeyword, searchPhilosopher);

        if (quotes.isEmpty()) {
            throw new RuntimeException("No quotes found matching your search criteria :(");
        }

        return quotes;
    }

    //CRUD
    public Quote addQuoteToPhilosopher(Long philoId, Quote quote) {
        Philosopher philosopher = philosopherRepo.findById(philoId).orElseThrow(() -> new RuntimeException("philosopher not found : ("));
        quote.setPhilosopher(philosopher);
        return repo.save(quote);
    }

    public Quote updateQuote(Long id, Quote newQuoteData) {
        Quote existingQuote = getQuoteById(id);

        if (newQuoteData.getQuoteText() != null && !newQuoteData.getQuoteText().trim().isEmpty()) {
            existingQuote.setQuoteText(newQuoteData.getQuoteText().trim());
        } else {
            throw new RuntimeException("Quote text cannot be empty!");
        }

        if (newQuoteData.getSourceBook() != null) {
            existingQuote.setSourceBook(newQuoteData.getSourceBook());
        }

        return repo.save(existingQuote);
    }

    public void deleteQuote(Long id) {
        Quote existingQuote = getQuoteById(id);
        repo.delete(existingQuote);
    }
}
