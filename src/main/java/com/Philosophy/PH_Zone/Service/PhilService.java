package com.Philosophy.PH_Zone.Service;

import com.Philosophy.PH_Zone.Model.Philosopher;
import com.Philosophy.PH_Zone.Repository.PhilosopherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PhilService {
    private final PhilosopherRepo repo;
    @Autowired
    public PhilService(PhilosopherRepo repo){
        this.repo = repo;
    }

    public List<Philosopher>  getAllPhilosophers(){
        return repo.findAll();
    }

    public Philosopher getPhilosopherById(Long id){
        return repo.findWithDetailsById(id).orElseThrow(() -> new RuntimeException("Philosopher Not Found : ("));
    }
// this code is good but there is better
//    public List<Philosopher> findPhilosopherBySchool(String school) {
//        boolean exists = repo.existsBySchoolIgnoreCase(school);
//        if(!exists) {
//            throw new RuntimeException("We Can Not Find " + school + "In Our DataBase : (");
//        }
//        return repo.findBySchoolIgnoreCase(school);
//    }
//
//    public Philosopher searchPhilosopherByName(String name) {
//        boolean exists = repo.existsByNameIgnoreCase(name);
//        if(!exists) {
//            throw new RuntimeException("We Can Not Find " + name + "In Our DataBase : (");
//        }
//        return repo.findPhilosopherByNameContainingIgnoreCase(name);
//    }

    public List<Philosopher> searchPhilosophers(String name, String school, String book) {
        String searchName = (name != null && !name.isBlank()) ? name : null;
        String searchSchool = (school != null && !school.isBlank()) ? school : null;
        String searchBook = (book != null && !book.isBlank()) ? book : null;

        List<Philosopher> philosophers = repo.searchDynamic(searchName, searchSchool, searchBook);

        if (philosophers.isEmpty()) {
            throw new RuntimeException("No philosophers found matching the criteria :(");
        }

        return philosophers;
    }

    // CRUD
    public Philosopher addPhilosopher(Philosopher philosopher){
        return repo.save(philosopher);
    }

    public void deletePhilosopher(Long id) {
        repo.deleteById(id);
    }

    public Philosopher updatePhilosopher(Long id, Philosopher updatedData) {
        Philosopher existingPhilosopher = repo.findWithDetailsById(id)
                .orElseThrow(() -> new RuntimeException("Philosopher Does Not Found With Id: " + id));

        existingPhilosopher.setName(updatedData.getName());
        existingPhilosopher.setImg(updatedData.getImg());
        existingPhilosopher.setDateOfBirth(updatedData.getDateOfBirth());
        existingPhilosopher.setDateOfDeath(updatedData.getDateOfDeath());
        existingPhilosopher.setSchool(updatedData.getSchool());

        return repo.save(existingPhilosopher);
    }




}
