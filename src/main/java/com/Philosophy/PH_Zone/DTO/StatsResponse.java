package com.Philosophy.PH_Zone.DTO;

public class StatsResponse {
    private long philosophers;
    private long books;
    private long quotes;
    private long schools;

    public StatsResponse(long philosophers, long books, long quotes, long schools) {
        this.philosophers = philosophers;
        this.books = books;
        this.quotes = quotes;
        this.schools = schools;
    }

    public long getPhilosophers() {
        return philosophers;
    }

    public long getBooks() {
        return books;
    }

    public long getQuotes() {
        return quotes;
    }

    public long getSchools() {
        return schools;
    }
}
