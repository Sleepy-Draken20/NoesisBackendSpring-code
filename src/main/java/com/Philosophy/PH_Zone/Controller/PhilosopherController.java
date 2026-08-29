package com.Philosophy.PH_Zone.Controller;

import com.Philosophy.PH_Zone.Model.Philosopher;
import com.Philosophy.PH_Zone.Service.BookService;
import com.Philosophy.PH_Zone.Service.PhilService;
import com.Philosophy.PH_Zone.Service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
public class PhilosopherController {
    private final PhilService service;
    private final BookService bService;
    private final QuoteService qService;
    @Autowired
    public PhilosopherController(PhilService service, BookService bService, QuoteService qService) {
        this.service=service;
        this.bService=bService;
        this.qService=qService;
    }

    @GetMapping
    public ResponseEntity<List<Philosopher>> getAllOrSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String school,
            @RequestParam(required = false) String books
    ) {
        List<Philosopher> result = service.searchPhilosophers(name, school, books);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Philosopher> getById (@PathVariable Long id) {
        return ResponseEntity.ok(service.getPhilosopherById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Philosopher> addPhilosopher(@RequestBody Philosopher philosopher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPhilosopher(philosopher));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Philosopher> updatePhilosopher(@PathVariable Long id, @RequestBody Philosopher philosopher) {
        return ResponseEntity.ok(service.updatePhilosopher(id, philosopher));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePhilosopher(@PathVariable Long id) {
        service.deletePhilosopher(id);
        return ResponseEntity.noContent().build();
    }

}
