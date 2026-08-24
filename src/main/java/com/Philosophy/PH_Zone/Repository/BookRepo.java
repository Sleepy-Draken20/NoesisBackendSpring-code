package com.Philosophy.PH_Zone.Repository;

import com.Philosophy.PH_Zone.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {
    List<Book> findBooksByPhilosopherId(Long id);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPhilosopherNameContainingIgnoreCase(String name);
    boolean existsByTitleIgnoreCaseAndPhilosopherId(String title, Long philosopherId);
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:philosopherName IS NULL OR LOWER(b.philosopher.name) LIKE LOWER(CONCAT('%', :philosopherName, '%')))")
    List<Book> searchBooksDynamic(
            @Param("title") String title,
            @Param("philosopherName") String philosopherName
    );
}
