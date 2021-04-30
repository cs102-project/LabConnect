import java.util.Scanner;

import company.*;

/**
 * A tester class for object inside the company package
 * 
 * @author Berkan Şahin
 * @version 09.03.2021
 */
public class CompanyTester {
    public static void main(String[] args) {

        Scanner scan = new Scanner("1 2 3 4 5 6 7 8 0");

        // Constants
        final int INIT_ITEMS = 1;
        final int INIT_PEOPLE = 2;
        final int GIVE_ITEMS_TO_CUSTOMERS = 3;
        final int SEND_FEW_ITEMS = 4;
        final int OVERWHELM_EMPLOYEES = 5;
        final int DELIVER_ITEMS = 6;
        final int TERMINATE_CONTRACT = 7;
        final int EMPTY_HANDS = 8;
        final int EXIT_TEST = 0;

        // Variables
        Item food = null;
        Item clothes = null;
        Item furniture = null;
        Item letter = null;
        Item books = null;
        Item napkins = null;
        Item photos = null;
        Item highlighters = null;
        Item resistors = null;
        Item mirror = null;
        Item dumbell = null;
        Customer bob = null;
        Customer alice = null;
        Employee chad = null;
        Employee mary = null;
        Company company;
        int choice;

        // Program Code

        company = new Company(3, 5);

        do {
            System.out.println();
            System.out.println(company);

            System.out.println("--- OPTIONS ---");
            System.out.printf("%d - Initialize several items%n", INIT_ITEMS);
            System.out.printf("%d - Initialize two customers and two employees%n", INIT_PEOPLE);
            System.out.printf("%d - Put items in Alice and Bob's hands%n", GIVE_ITEMS_TO_CUSTOMERS);
            System.out.printf("%d - Alice and Bob try to send each other a single item%n", SEND_FEW_ITEMS);
            System.out.printf("%d - Bob sends 10 items, overwhelming the company%n", OVERWHELM_EMPLOYEES);
            System.out.printf("%d - Deliver all items currently held by the company%n", DELIVER_ITEMS);
            System.out.printf("%d - Terminate an employee's contract%n", TERMINATE_CONTRACT);
            System.out.printf("%d - Empty the customers' hands%n", EMPTY_HANDS);
            System.out.printf("%d - Exit the program%n", EXIT_TEST);
            System.out.print("[Your choice]> ");

            choice = scan.nextInt();
            
            System.out.println();

            switch (choice) {

            case EXIT_TEST:
                break;

            case INIT_ITEMS:
                food = new Item(3, "Food");
                clothes = new Item(1, "Clothes");
                furniture = new Item(63, "Furniture");
                letter = new Item(0.02, "Letters");
                books = new Item(0.5, "Books");
                napkins = new Item(0.7, "Napkins");
                photos = new Item(0.07, "Some Photographs");
                highlighters = new Item(1.3, "120 pack of highlighters");
                resistors = new Item(0.33, "7 330 ohm resistors");
                mirror = new Item(4.2, "Mirror");
                dumbell = new Item(10, "5 kg dumbell set");
                break;

            case INIT_PEOPLE:
                bob = new Customer("Bob");
                alice = new Customer("Alice");
                chad = new Employee(314, "Chad");
                mary = new Employee(3, "Mary");

                bob.setPos(-1, 3);
                alice.setPos(2, -3);
                chad.setPos(3, 4);
                mary.setPos(1, 1);

                company.addCustomer(bob);
                company.addCustomer(alice);
                company.addEmployee(chad);
                company.addEmployee(mary);

            case GIVE_ITEMS_TO_CUSTOMERS:
                bob.setCurrentItem(letter);
                alice.setCurrentItem(photos);
                break;

            case SEND_FEW_ITEMS:
                if (alice.sendItem(company, mirror, bob)) {
                    System.out.println("Alice could send an item.");
                } else {
                    System.out.println("Alice should wait as all employees are unavailable right now.");
                }

                if (bob.sendItem(company, highlighters, alice)) {
                    System.out.println("Bob could send an item.");
                } else {
                    System.out.println("Bob should wait as all employees are unavailable right now.");
                }

                break;

            case OVERWHELM_EMPLOYEES:
                bob.sendItem(company, food, alice);
                bob.sendItem(company, furniture, alice);
                bob.sendItem(company, dumbell, alice);
                bob.sendItem(company, napkins, alice);
                bob.sendItem(company, resistors, alice);
                bob.sendItem(company, highlighters, alice);
                bob.sendItem(company, photos, alice);
                bob.sendItem(company, books, alice);
                bob.sendItem(company, clothes, alice);
                bob.sendItem(company, letter, alice);
                break;

            case DELIVER_ITEMS:
                company.deliverPackages();
                break;

            case EMPTY_HANDS:
                alice.setCurrentItem(null);
                bob.setCurrentItem(null);
                break;

            case TERMINATE_CONTRACT:
                company.terminateContract(0);
                break;

            default:
                System.out.println("Invalid option! Try again.");
            }

        } while (choice != 0);

        scan.close();
        System.out.println("Bye!");
    }
}
