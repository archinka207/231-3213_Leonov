package com.leonov.backend.config;

import com.leonov.backend.entity.SportNutrition;
import com.leonov.backend.repository.SportNutritionRepo;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Profile("!test") // Не выполнять при тестах
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(SportNutritionRepo repository) {
        return args -> {
            // Очистка базы (опционально)
            repository.deleteAll();

            // Генератор фейковых данных
            Faker faker = new Faker(new Locale("ru"));

            // Категории товаров
            String[] categories = {"Протеин", "Креатин", "Аминокислоты", "Витамины", "Жиросжигатели"};

            // Генерируем 50 фейковых записей
            List<SportNutrition> products = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> {
                    SportNutrition product = new SportNutrition();
                    product.setName(faker.commerce().productName() + " " + categories[i % categories.length]);
                    product.setPrice(faker.number().numberBetween(500L, 5000L));
                    product.setCategoryId((long) (i % categories.length + 1));
                    return product;
                })
                .collect(Collectors.toList());

            // Сохраняем в базу
            repository.saveAll(products);

            System.out.println("Сгенерировано");
        };
    }
}