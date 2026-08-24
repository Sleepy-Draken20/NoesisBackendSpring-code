package com.Philosophy.PH_Zone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "philosophers")
@AllArgsConstructor
@NoArgsConstructor
public class Philosopher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String img;

    private Integer dateOfBirth;
    private Integer dateOfDeath;
    // maybe we will use it
    //private String bio;

    private String school;

    @OneToMany(mappedBy = "philosopher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"quotes", "books"})
    private Set<Book> books;

    @OneToMany(mappedBy = "philosopher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"quotes", "books"})
    private Set<Quote> quotes;

}
