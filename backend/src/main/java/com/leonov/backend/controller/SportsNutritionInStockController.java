package com.leonov.backend.controller;

import com.leonov.backend.entity.SportNutrition;
import com.leonov.backend.repository.SportNutritionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sport-nutrition")
public class SportsNutritionInStockController {

    private final SportNutritionRepo sportNutritionRepo;

    @Autowired
    public SportsNutritionInStockController(SportNutritionRepo sportNutritionRepo) {
        this.sportNutritionRepo = sportNutritionRepo;
    }

    // Create
    @PostMapping
    public ResponseEntity<SportNutrition> create(@RequestBody SportNutrition sportNutrition) {
        SportNutrition saved = sportNutritionRepo.save(sportNutrition);
        return ResponseEntity.ok(saved);
    }

    // Read (by id)
    @GetMapping("/{id}")
    public ResponseEntity<SportNutrition> getById(@PathVariable Long id) {
        Optional<SportNutrition> item = sportNutritionRepo.findById(id);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<SportNutrition> update(
            @PathVariable Long id,
            @RequestBody SportNutrition updatedItem) {
        
        return sportNutritionRepo.findById(id)
                .map(existingItem -> 
                {
                    existingItem.setName(updatedItem.getName());
                    existingItem.setPrice(updatedItem.getPrice());
                    existingItem.setCategoryId(updatedItem.getCategoryId());
                    SportNutrition saved = sportNutritionRepo.save(existingItem);
                    return ResponseEntity.ok(saved);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (sportNutritionRepo.existsById(id)) {
            sportNutritionRepo.deleteById(id);
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.notFound().build();
    }

    // Дополнительные endpoints
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<SportNutrition>> getByCategory(@PathVariable Long categoryId) {
        List<SportNutrition> items = sportNutritionRepo.findByCategoryId(categoryId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SportNutrition>> searchByName(@RequestParam String name) {
        List<SportNutrition> items = sportNutritionRepo.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(items);
    }
}