package se.lernia.lindstrom.max.lab_3.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDateTime creationDate,
        LocalDateTime lastModifiedDate
) {
    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Product rating must be between 0 and 10");
        }
        Objects.requireNonNull(category, "Category cannot be null");
        Objects.requireNonNull(creationDate, "Creation date cannot be null");
        Objects.requireNonNull(lastModifiedDate, "Last modification date cannot be null");
    }
}
