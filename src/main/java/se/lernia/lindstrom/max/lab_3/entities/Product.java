package se.lernia.lindstrom.max.lab_3.entities;

import java.time.LocalDateTime;

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
    }
}
