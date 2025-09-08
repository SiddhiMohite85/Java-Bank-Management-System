import java.util.*;

// Abstraction
abstract class Account {
    private String accountHolder;
    private String accountNumber;
    protected double balance;

    public Account(String accountHolder, String accountNumber, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public abstract void withdraw(double amount); // Abstract method

    public void displayDetails() {
        System.out.println("Name: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: ₹" + balance);
    }
}

// Inheritance + Polymorphism
class SavingsAccount extends Account {
    private double interestRate = 0.04;

    public SavingsAccount(String name, String accNumber, double balance) {
        super(name, accNumber, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew ₹" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest ₹" + interest + " added.");
    }
}

// Main Bank System
class BankSystem {
    private Map<String, Account> accounts = new HashMap<>();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- OOP Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Add Interest (Savings Only)");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccount();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> addInterest();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createAccount() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();
        System.out.print("Enter initial balance: ₹");
        double balance = sc.nextDouble();

        Account acc = new SavingsAccount(name, accNum, balance); // Polymorphism
        accounts.put(accNum, acc);
        System.out.println("Savings Account created successfully.");
    }

    private void viewAccount() {
        Account acc = getAccount();
        if (acc != null) acc.displayDetails();
    }

    private void deposit() {
        Account acc = getAccount();
        if (acc != null) {
            System.out.print("Enter deposit amount: ₹");
            double amount = sc.nextDouble();
            acc.deposit(amount);
        }
    }

    private void withdraw() {
        Account acc = getAccount();
        if (acc != null) {
            System.out.print("Enter withdrawal amount: ₹");
            double amount = sc.nextDouble();
            acc.withdraw(amount); // Dynamic binding
        }
    }

    private void addInterest() {
        Account acc = getAccount();
        if (acc instanceof SavingsAccount) {
            ((SavingsAccount) acc).addInterest(); // Downcasting
        } else {
            System.out.println("Interest not applicable.");
        }
    }

    private Account getAccount() {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();
        Account acc = accounts.get(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
        }
        return acc;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        BankSystem system = new BankSystem();
        system.start();
    }
}
