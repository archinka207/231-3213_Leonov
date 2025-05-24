package com.leonov.backend.repository;
import com.leonov.backend.entity.SportNutrition;

import java.util.List;

import org.springframework.data.repository.CrudRepository;  

public interface SportNutritionRepo extends CrudRepository<SportNutrition, Long>{ 
    List<SportNutrition> findByCategoryId(Long categoryId);
    List<SportNutrition> findByNameContainingIgnoreCase(String name);
}
