import java.io.*;
import java.util.*;

// ProductType Class (Encapsulation, Polymorphism through toString)
class ProductType {
    private String typeName;

    public ProductType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}

// Base Product Class (Encapsulation)
class Product {
    private String name;
    private float quantity;
    private float price;
    private ProductType type;

    public Product(String name, float quantity, float price, ProductType type) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Quantity: %.2f, Price: %.2f, Type: %s",
                name, quantity, price, type);
    }
}

// AdminProduct Class Extending Product (Inheritance)
class AdminProduct extends Product {
    private String adminNote;

    public AdminProduct(String name, float quantity, float price, ProductType type, String adminNote) {
        super(name, quantity, price, type);
        this.adminNote = adminNote;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    @Override
    public String toString() {
        return super.toString() + ", Admin Note: " + adminNote;
    }
}

// User Class (Encapsulation, Polymorphism through Role Types)
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}

// Manager Class Extending User (Inheritance, Polymorphism)
class Manager extends User {
    public Manager(String username, String password) {
        super(username, password);
    }

    public void manageShop() {
        System.out.println("Managing the shop...");
    }
}

// Main System Class (Abstraction)
public class test2 {
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String PRODUCT_TYPES_FILE = "product-types.txt";

    private static List<Product> products = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<ProductType> productTypes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        boolean loggedIn = false;

        while (true) {
            if (!loggedIn) {
                displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                         registerUser();
                         break;
                    case 2: 
                        loggedIn = loginUser();
                        break;
                    case 0: {
                        saveData();
                        System.out.println("Exiting...");
                        return;
                    }
                    default: System.out.println("Invalid choice. Please try again.");
                }
            } else {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                         addProductType();
                    case 2:
                        addProduct();
                    case 3:
                         displayProducts();
                    case 4:
                        displayProductTypes();
                        break;
                    case 5:
                        updateProduct();
                        break;
                    case 6:
                        deleteProduct();
                        break;
                    case 7:
                        searchProduct();
                        break;
                    case 8:
                        saveData();
                        break;
                    case 9:
                        loggedIn = false;
                        break;
                    case 0: {
                        saveData();
                        System.out.println("Exiting...");
                        return;
                    }
                    default: System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    // Main Menu Display
    private static void displayMainMenu() {
        System.out.println("============= Welcome to Super Shop Management System =============");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------------------------------------");
    }

    // Management Menu
    private static void displayMenu() {
        System.out.println("================ Super Shop Management System ================");
        System.out.println("1. Add Product Type");
        System.out.println("2. Add Product");
        System.out.println("3. Display Products");
        System.out.println("4. Display Product Types");
        System.out.println("5. Update Product");
        System.out.println("6. Delete Product");
        System.out.println("7. Search Product");
        System.out.println("8. Save Data to File");
        System.out.println("9. Logout");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    // User Management
    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("Registration successful.");
    }

    private static boolean loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                return true;
            }
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    // File Handling (Abstraction)
    private static void saveData() {
        saveToFile(PRODUCTS_FILE, products);
        saveToFile(USERS_FILE, users);
        saveToFile(PRODUCT_TYPES_FILE, productTypes);
    }

    private static void loadData() {
        products = loadFromFile(PRODUCTS_FILE, Product.class);
        users = loadFromFile(USERS_FILE, User.class);
        productTypes = loadFromFile(PRODUCT_TYPES_FILE, ProductType.class);
    }

    private static <T> void saveToFile(String fileName, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadFromFile(String fileName, Class<T> type) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // Other Functionalities (Add, Update, Delete, etc.)
    // Similar to above implementation.
    private static void addProductType() {
        System.out.println("============== Add Product Type ==============");
        System.out.print("Enter product type name: ");
        String typeName = scanner.nextLine();
        productTypes.add(new ProductType(typeName));
        System.out.println("Product type added successfully.");
    }
    private static void addProduct() {
        System.out.println("============== Add Product ==============");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product quantity: ");
        float quantity = scanner.nextFloat();
        System.out.print("Enter product price: ");
        float price = scanner.nextFloat();
        scanner.nextLine(); // Consume newline

        System.out.println("Available Product Types:");
        for (int i = 0; i < productTypes.size(); i++) {
            System.out.println((i + 1) + ". " + productTypes.get(i).getTypeName());
        }
        System.out.print("Choose product type: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        ProductType type = typeChoice > 0 && typeChoice <= productTypes.size()
                ? productTypes.get(typeChoice - 1)
                : new ProductType("Unknown");

        products.add(new Product(name, quantity, price, type));
        System.out.println("Product added successfully.");
    }
    private static void displayProducts() {
        System.out.println("============== Product List ==============");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void displayProductTypes() {
        System.out.println("============== Product Types ==============");
        for (ProductType type : productTypes) {
            System.out.println(type);
        }
    }
    private static void updateProduct() {
        System.out.println("============== Update Product ==============");
        System.out.print("Enter the name of the product to update: ");
        String name = scanner.nextLine();

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                product.setQuantity(scanner.nextFloat());
                System.out.print("Enter new price: ");
                product.setPrice(scanner.nextFloat());
                scanner.nextLine(); // Consume newline
                System.out.println("Product updated successfully.");
                return;
            }
        }

        System.out.println("Product not found.");
    }
    private static void deleteProduct() {
        System.out.println("============== Delete Product ==============");
        System.out.print("Enter the name of the product to delete: ");
        String name = scanner.nextLine();

        products.removeIf(product -> product.getName().equalsIgnoreCase(name));
        System.out.println("Product deleted successfully.");
    }
    private static void searchProduct() {
        System.out.println("============== Search Product ==============");
        System.out.print("Enter the name of the product to search: ");
        String name = scanner.nextLine();

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println(product);
                return;
            }
        }

        System.out.println("Product not found.");
    }

}
