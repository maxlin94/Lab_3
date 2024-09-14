package se.lernia.lindstrom.max.lab_3.service;

import se.lernia.lindstrom.max.lab_3.entities.Category;
import se.lernia.lindstrom.max.lab_3.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(products.stream().anyMatch(p -> p.id().equals(product.id()))) {
            throw new IllegalArgumentException("Product already exists");
        }
        products.add(product);
    }

    public void modifyProduct(String id, String newName, Category newCategory, int newRating) {
        Product existingProduct = getProductById(id);
        Product modifiedProduct = new Product(
                existingProduct.id(),
                newName,
                newCategory,
                newRating,
                existingProduct.creationDate(),
                LocalDate.now()
        );
        products.remove(existingProduct);
        products.add(modifiedProduct);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + id + " not found"));
    }

    public List<Product> getProductsByCategory(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(product -> product.name().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(product -> product.creationDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> !product.creationDate().equals(product.lastModifiedDate()))
                .collect(Collectors.toList());
    }

    public List<Category> getNonEmptyCategories() {
        List<Category> categories = new ArrayList<>();
        products.forEach(product -> {
            if(!categories.contains(product.category())) {
                categories.add(product.category());
            }
        });
        return categories;
    }

    public int getNumberProductsByCategory(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .toList()
                .size();
    }

    public Map<Character, Long> getProductStartingLetterMap() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.name().charAt(0),
                        Collectors.counting()
                ));
    }

    public List<Product> getMaxRatedProductsLastMonth() {
        return products.stream()
                .filter(product -> product.rating() == 10 && product.creationDate().isAfter(LocalDate.now().minusMonths(1)))
                .sorted(Comparator.comparing(Product::creationDate))
                .collect(Collectors.toList());
    }
}
