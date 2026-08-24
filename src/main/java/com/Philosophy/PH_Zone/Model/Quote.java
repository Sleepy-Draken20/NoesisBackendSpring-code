package com.Philosophy.PH_Zone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "quotes")
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String quoteText;

    private String sourceBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "philosopher_id", nullable = false)
    @JsonIgnoreProperties({"quotes", "books"})
    private Philosopher philosopher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"quotes", "books"})
    private Book book;


}
