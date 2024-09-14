package se.lernia.lindstrom.max.lab_3;

import se.lernia.lindstrom.max.lab_3.entities.Category;
import se.lernia.lindstrom.max.lab_3.entities.Product;
import se.lernia.lindstrom.max.lab_3.service.Warehouse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Warehouse warehouse = new Warehouse();
    private static final List<Category> categories = Arrays.stream(Category.values()).toList();

    public static void main(String[] args) {
        int selection;
        do {
            printMenu();
            selection = getSelection(0, 11);
            menuChoice(selection);
        } while (selection != 0);
    }

    public static void printMenu() {
        System.out.println("Warehouse management");
        System.out.println("1. Add new product");
        System.out.println("2. Edit product");
        System.out.println("3. List all products");
        System.out.println("4. View product by ID");
        System.out.println("5. View products by category");
        System.out.println("6. View products created after date");
        System.out.println("7. View modified products");
        System.out.println("8. View non empty categories");
        System.out.println("9. View number of products in category");
        System.out.println("10. View number of products starting with x letter");
        System.out.println("11. View products with max rating created last month");
        System.out.println("0. Exit");
    }

    public static int getSelection(int min, int max) {
        int selection = Integer.MIN_VALUE;
        while (selection < min || selection > max) {
            try {
                selection = scanner.nextInt();
                scanner.nextLine();
                if (selection < min || selection > max) {
                    System.out.println("Invalid input");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
        return selection;
    }

    public static void menuChoice(int selection) {
        switch (selection) {
            case 1:
                addProduct();
                break;
            case 2:
                editProduct();
                break;
            case 3:
                listAllProducts();
                break;
            case 4:
                listProductById();
                break;
            case 5:
                listProductsByCategory();
                break;
            case 6:
                listProductsCreatedAfterDate();
                break;
            case 7:
                listModifiedProducts();
                break;
            case 8:
                listNonEmptyCategories();
                break;
            case 9:
                listNumberProductsInCategory();
                break;
            case 10:
                listNumberProductsStartingWithLetter();
                break;
            case 11:
                listMaxRatedProductsLastMonth();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }

    public static void printCategories() {
        System.out.println("Select a category");
        for (int i = 0; i < categories.size(); i++) {
            int menuNumber = i + 1;
            System.out.println("[" + menuNumber + "] " + categories.get(i));
        }
    }

    public static String getProductName() {
        System.out.println("Enter a name for the product");
        String name;
        do {
            name = scanner.nextLine();
        } while (name.isEmpty() || name.isBlank());
        return name;
    }

    public static int getProductRating() {
        System.out.println("Enter a rating for the product");
        return getSelection(0, 10);
    }

    public static Category getProductCategory() {
        printCategories();
        int selection = getSelection(0, categories.size());
        return categories.get(selection - 1);
    }

    public static String getProductId(List<Product> products) {
        System.out.println("Enter the ID of the product");
        return String.valueOf(getSelection(0, products.size()));
    }

    public static void addProduct() {
        LocalDate creationDate = LocalDate.now();
        String productId = String.valueOf(warehouse.getAllProducts().size() + 1);
        String name = getProductName();
        Category category = getProductCategory();
        int productRating = getProductRating();
        Product product = new Product(productId, name, category, productRating, creationDate, creationDate);
        warehouse.addProduct(product);
    }

    public static void editProduct() {
        List<Product> products = warehouse.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found");
            return;
        }
        listAllProducts();
        String id = getProductId(products);
        String newName = getProductName();
        Category newCategory = getProductCategory();
        int newRating = getProductRating();
        try {
            warehouse.modifyProduct(id, newName, newCategory, newRating);
        } catch (NoSuchElementException e) {
            System.out.println("Product not found");
        }
    }

    public static void listAllProducts() {
        List<Product> products = warehouse.getAllProducts();
        products.forEach(System.out::println);
    }

    public static void listProductById() {
        System.out.println("Enter the ID of the product");
        try {
            String id = scanner.nextLine();
            Product product = warehouse.getProductById(id);
            System.out.println(product);
        } catch (NoSuchElementException e) {
            System.out.println("Product not found");
        }
    }

    public static void listProductsByCategory() {
        Category category = getProductCategory();
        List<Product> products = warehouse.getProductsByCategory(category);
        if (products.isEmpty()) {
            System.out.println("No products with category " + category);
        } else {
            products.forEach(System.out::println);
        }
    }

    public static void listProductsCreatedAfterDate() {
        System.out.println("Enter a date (YYYY-MM-DD):");
        LocalDate date = null;
        do {
            String dateInput = scanner.nextLine();
            try {
                date = LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please enter a valid date in the format YYYY-MM-DD.");
            }
        } while (date == null);
        List<Product> products = warehouse.getProductsCreatedAfter(date);
        if (products.isEmpty()) {
            System.out.println("No products created after " + date);
            return;
        }
        products.forEach(System.out::println);
    }

    public static void listModifiedProducts() {
        warehouse.getModifiedProducts().forEach(System.out::println);
    }

    public static void listNonEmptyCategories() {
        warehouse.getNonEmptyCategories().forEach(System.out::println);
    }

    public static void listNumberProductsInCategory() {
        Category category = getProductCategory();
        warehouse.getProductsByCategory(category).forEach(System.out::println);
    }

    public static void listNumberProductsStartingWithLetter() {
        warehouse.getProductStartingLetterMap().forEach((key, value) -> System.out.println("Letter: " + key + " Count: " + value));
    }

    public static void listMaxRatedProductsLastMonth() {
        warehouse.getMaxRatedProductsLastMonth().forEach(System.out::println);
    }
}
