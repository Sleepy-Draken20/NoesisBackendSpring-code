package com.Philosophy.PH_Zone.Repository;

import com.Philosophy.PH_Zone.Model.Philosopher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhilosopherRepo extends JpaRepository<Philosopher,Long> {

    @Query("SELECT DISTINCT p FROM Philosopher p " +
            "LEFT JOIN FETCH p.books b " +
            "WHERE (:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:school IS NULL OR :school = '' OR LOWER(p.school) LIKE LOWER(CONCAT('%', :school, '%'))) AND " +
            "(:book IS NULL OR :book = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :book, '%')))")
    List<Philosopher> searchDynamic(
            @Param("name") String name,
            @Param("school") String school,
            @Param("book") String book
    );


    @EntityGraph(attributePaths = {"books", "quotes"})
    Optional<Philosopher> findWithDetailsById(Long id);

    boolean existsByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(DISTINCT p.school) FROM Philosopher p WHERE p.school IS NOT NULL")
    long countDistinctSchools();

}
