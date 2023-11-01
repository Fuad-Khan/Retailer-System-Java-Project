import java.util.Scanner;

class Customer {
    private String name;
    private String email;
    private String password;

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

class Item {
    private String name;
    private int price;
    private int quantity;

    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int purchasedQuantity) {
        if (purchasedQuantity <= quantity) {
            quantity -= purchasedQuantity;
        }
    }
}

class AccountManagement {
    public static Customer createCustomerAccount(Scanner scanner) {
        System.out.println("To start shopping, create your account.");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        System.out.println("Account created successfully!");
        return new Customer(name, email, password);
    }

    public static boolean loginCustomer(Scanner scanner, Customer customer) {
        while (true) {
            System.out.print("Please enter your email address: ");
            String enteredEmail = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String enteredPassword = scanner.nextLine();

            if (enteredEmail.equals(customer.getEmail()) && enteredPassword.equals(customer.getPassword())) {
                System.out.println("Welcome, " + customer.getEmail() + "!");
                return true;
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        }
    }
}

class Shopping {
    public static void displayItems(Item[] items) {
        System.out.println("Available items:");
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            System.out.println((i + 1) + ". " + item.getName() + " - " + item.getPrice() + " Taka" + " (quantity: " + item.getQuantity() + ")");
        }
    }

    public static int getQuantityToPurchase(Scanner scanner, Item item) {
        System.out.println("Please enter the quantity of " + item.getName() + " you would like to purchase:");
        int quantity = scanner.nextInt();
        if (quantity > item.getQuantity()) {
            System.out.println("We have limited quantity for " + item.getName() + ".");
            return 0;
        }
        return quantity;
    }

    public static void displayTotalPurchase(double totalPurchase) {
        if (totalPurchase > 20000) {
            double discount = totalPurchase * 0.1;
            double discountedTotal = totalPurchase - discount;
            System.out.println("Congratulations! You qualify for a 10% discount.");
            System.out.println("Total purchase amount: " + totalPurchase + " Taka");
            System.out.println("Discounted total: " + discountedTotal + " Taka");
        } else {
            System.out.println("Total purchase amount: " + totalPurchase + " Taka");
        }
    }

    public static void makePayment(Scanner scanner) {
        System.out.println("Please select a payment method:");
        System.out.println("1. bKash");
        System.out.println("2. Nagad");
        System.out.println("3. Banking");

        int paymentMethod = scanner.nextInt();

        switch (paymentMethod) {
            case 1:
                System.out.println("Please make payment to bKash account 01234567890.");
                break;
            case 2:
                System.out.println("Please make payment to Nagad account 01234567890.");
                break;
            case 3:
                System.out.println("Please make payment to our bank account No - 012345678911011.");
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }
}

public class RetailerSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create items
        Item[] items = new Item[]{
                new Item("Food Accessories", 100, 65),
                new Item("Home Accessories", 200, 300),
                new Item("Books", 350, 350),
                new Item("Electric Parts", 500, 100),
                new Item("Cloth", 250, 70)
        };

        Customer customer = AccountManagement.createCustomerAccount(scanner);

        boolean isLoggedIn = AccountManagement.loginCustomer(scanner, customer);
        double totalPurchase = 0;

        if (isLoggedIn) {
            boolean isDoneShopping = false;

            while (!isDoneShopping) {
                Shopping.displayItems(items);
                System.out.println("Please select an item to purchase (or enter 0 to finish shopping):");

                int selectedItem = scanner.nextInt();
                if (selectedItem == 0) {
                    isDoneShopping = true;
                    continue;
                } else if (selectedItem < 0 || selectedItem > items.length) {
                    System.out.println("Invalid selection.");
                    continue;
                }

                Item item = items[selectedItem - 1];
                int quantity = Shopping.getQuantityToPurchase(scanner, item);

                if (quantity > 0) {
                    item.decreaseQuantity(quantity);
                    double itemTotalPrice = item.getPrice() * quantity;
                    totalPurchase += itemTotalPrice;
                    System.out.println("You have selected " + quantity + " of " + item.getName() + ".");
                    System.out.println("The total price is " + itemTotalPrice + " Taka.");
                }

                System.out.println("Would you like to make another purchase? (y/n)");
                String response = scanner.next();
                if (response.equalsIgnoreCase("n")) {
                    isDoneShopping = true;
                }
            }

            Shopping.displayTotalPurchase(totalPurchase);
            Shopping.makePayment(scanner);
        }

        System.out.println("Thank you for shopping with us!");
    }
}
