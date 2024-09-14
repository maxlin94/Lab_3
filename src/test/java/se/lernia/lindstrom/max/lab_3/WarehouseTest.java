package se.lernia.lindstrom.max.lab_3;

import se.lernia.lindstrom.max.lab_3.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lernia.lindstrom.max.lab_3.entities.Product;
import se.lernia.lindstrom.max.lab_3.service.Warehouse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private Warehouse warehouse;
    private Product product1;
    private final int TOTAL_PRODUCTS = 4;
    private final List<Category> categories = new ArrayList<>();

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product1 = new Product("1", "Shirt", Category.SHIRT, 8, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        Product product2 = new Product("2", "Hoodie", Category.HOODIE, 6, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5));
        Product product3 = new Product("3", "Jeans", Category.JEANS, 10, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2));
        Product product4 = new Product("4", "Other Jeans", Category.JEANS, 10, LocalDateTime.now().minusDays(32), LocalDateTime.now().minusDays(31));
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        categories.add(product1.category());
        categories.add(product2.category());
        categories.add(product3.category());
    }

    @Test
    void testAddProduct() {
        assertEquals(TOTAL_PRODUCTS, warehouse.getAllProducts().size());
    }

    @Test
    void testAddProductFail() {
        Product product = new Product("1", "Jeans", Category.JEANS, 7, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2));
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
        assertEquals(TOTAL_PRODUCTS, warehouse.getAllProducts().size());
    }

    @Test
    void testModifyProduct() {
        warehouse.modifyProduct("1", "New Shirt", Category.SHIRT, 9);
        Product modifiedProduct = warehouse.getProductById("1");
        assertEquals("New Shirt", modifiedProduct.name());
        assertEquals(9, modifiedProduct.rating());
    }

    @Test
    void testModifyProductFail() {
        assertThrows(NoSuchElementException.class, () -> {
            warehouse.modifyProduct("000", "New Shirt", Category.SHIRT, 9);
        });
    }

    @Test
    void testGetProductById() {
        assertEquals(product1, warehouse.getProductById("1"));
    }

    @Test
    void testGetProductByIdFail() {
        assertThrows(NoSuchElementException.class, () -> {
            warehouse.getProductById("000");
        });
    }

    @Test
    void testGetProductsByCategory() {
        assertEquals(1, warehouse.getProductsByCategory(Category.HOODIE).size());
        assertEquals(0, warehouse.getProductsByCategory(Category.SHORTS).size());
    }

    @Test
    void testGetProductsCreatedAfter() {
        assertEquals(2, warehouse.getProductsCreatedAfter(LocalDateTime.now().minusDays(3)).size());
    }

    @Test
    void testGetProductsModifiedSinceCreation() {
        assertEquals(2, warehouse.getModifiedProducts().size());
    }

    @Test
    void testGetNonEmptyCategories() {
        assertEquals(categories, warehouse.getNonEmptyCategories());
    }

    @Test
    void testGetNumberProductsOfCategory() {
        assertEquals(1, warehouse.getNumberProductsByCategory(Category.SHIRT));
    }

    @Test
    void testGetProductStartingLetterMap() {
        Map<Character, Long> map = warehouse.getProductStartingLetterMap();
        assertEquals(1, map.get('S'));
        assertEquals(1, map.get('H'));
        assertEquals(1, map.get('J'));
        assertEquals(1, map.get('O'));
    }

    @Test
    void testGetMaxRatedProductsLastMonth() {
        assertEquals(1, warehouse.getMaxRatedProductsLastMonth().size());
    }
}