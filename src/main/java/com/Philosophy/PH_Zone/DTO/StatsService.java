package com.Philosophy.PH_Zone.DTO;

import com.Philosophy.PH_Zone.Repository.BookRepo;
import com.Philosophy.PH_Zone.Repository.PhilosopherRepo;
import com.Philosophy.PH_Zone.Repository.QuoteRepo;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final PhilosopherRepo philosopherRepo;
    private final BookRepo bookRepo;
    private final QuoteRepo quoteRepo;

    public StatsService(
            PhilosopherRepo philosopherRepo,
            BookRepo bookRepo,
            QuoteRepo quoteRepo
    ) {
        this.philosopherRepo = philosopherRepo;
        this.bookRepo = bookRepo;
        this.quoteRepo = quoteRepo;
    }

    public StatsResponse getStats() {

        long philosophers = philosopherRepo.count();
        long books = bookRepo.count();
        long quotes = quoteRepo.count();
        long schools = philosopherRepo.countDistinctSchools();

        return new StatsResponse(
                philosophers,
                books,
                quotes,
                schools
        );
    }
}
