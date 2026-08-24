package com.Philosophy.PH_Zone.Repository;

import com.Philosophy.PH_Zone.Model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepo extends JpaRepository<Quote,Long> {
    List<Quote> findByPhilosopherId(Long philosopherId);

    List<Quote> findByBook_Id(Long bookId);

    //List<Quote> findByContentContainingIgnoreCase(String content);
    List<Quote> findByPhilosopherNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Quote findRandomQuote();


    // البحث في نص المقولة (استخدام quoteText)
    List<Quote> findByQuoteTextContainingIgnoreCase(String keyword);

    // البحث بالمصدر/الكتاب المصدر
    List<Quote> findBySourceBookContainingIgnoreCase(String sourceBook);
    @Query("SELECT q FROM Quote q WHERE " +
            "(:keyword IS NULL OR LOWER(q.quoteText) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:philosopherName IS NULL OR LOWER(q.philosopher.name) LIKE LOWER(CONCAT('%', :philosopherName, '%')))")
    List<Quote> searchQuotesDynamic(
            @Param("keyword") String keyword,
            @Param("philosopherName") String philosopherName
    );

}
