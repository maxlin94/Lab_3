package se.lernia.lindstrom.max.lab_3;

import org.junit.jupiter.api.Test;
import se.lernia.lindstrom.max.lab_3.entities.Category;
import se.lernia.lindstrom.max.lab_3.entities.Product;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    @Test
    void testEmptyOrNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("5", "", Category.JEANS, 7, LocalDateTime.now(), LocalDateTime.now());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("5", null, Category.JEANS, 7, LocalDateTime.now(), LocalDateTime.now());
        });
    }

    @Test
    void testEmptyOrNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("", "Shirt", Category.SHIRT, 7, LocalDateTime.now(), LocalDateTime.now());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Product(null, "Shirt", Category.SHIRT, 7, LocalDateTime.now(), LocalDateTime.now());
        });
    }

    @Test
    void testNullCategory() {
        assertThrows(NullPointerException.class, () -> {
            new Product("5", "Shirt", null, 7, LocalDateTime.now(), LocalDateTime.now());
        });
    }

    @Test
    void testNullCreationDate() {
        assertThrows(NullPointerException.class, () -> {
            new Product("5", "Shirt", Category.SHIRT, 7, null, LocalDateTime.now());
        });
    }

    @Test
    void testNullModificationDate() {
        assertThrows(NullPointerException.class, () -> {
            new Product("5", "Shirt", Category.SHIRT, 7, LocalDateTime.now(), null);
        });
    }
}
