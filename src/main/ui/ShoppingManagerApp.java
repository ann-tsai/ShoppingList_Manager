package ui;

import model.Item;
import model.ShoppingList;
import model.OldPurchasesList;
import persistence.OldPurchasesListJsonReader;
import persistence.OldPurchasesListJsonWriter;
import persistence.ShoppingListJsonReader;
import persistence.ShoppingListJsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Shopping List and Money Manager application
// some parts of this code were referenced from the WorkRoom example that was given.
public class ShoppingManagerApp {
    private static final String JSON_STORE_SHOPPING_LIST = "./data/shoppingList.json";
    private static final String JSON_STORE_PURCHASED_LIST = "./data/oldPurchasesList.json";
    private ShoppingList shoppingList;
    private OldPurchasesList oldPurchasesList;
    private Scanner input;
    private ShoppingListJsonWriter shoppingListJsonWriter;
    private OldPurchasesListJsonWriter oldPurchasesListJsonWriter;
    private ShoppingListJsonReader shoppingListJsonReader;
    private OldPurchasesListJsonReader oldPurchasesListJsonReader;

    // EFFECTS: runs the Shopping Manager application
    public ShoppingManagerApp() throws FileNotFoundException, InputMismatchException {
        shoppingList = new ShoppingList();
        oldPurchasesList = new OldPurchasesList();
        input = new Scanner(System.in);

        shoppingListJsonWriter = new ShoppingListJsonWriter(JSON_STORE_SHOPPING_LIST);
        oldPurchasesListJsonWriter = new OldPurchasesListJsonWriter(JSON_STORE_PURCHASED_LIST);
        shoppingListJsonReader = new ShoppingListJsonReader(JSON_STORE_SHOPPING_LIST);
        oldPurchasesListJsonReader = new OldPurchasesListJsonReader(JSON_STORE_PURCHASED_LIST);

        loadShoppingList();
        loadOldPurchasesList();

        runShoppingManager();

        saveShoppingList();
        saveOldPurchasesList();

        System.out.println("\nYour actions have been saved!");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShoppingManager() {
        boolean showMenu = true;
        int keepGoing;
        String command;
        input = new Scanner(System.in);

        while (showMenu) {
            System.out.println();
            System.out.println("Do you wish to see the menu? (Enter '1' to proceed and '2' to quit)");
            keepGoing = input.nextInt();

            if (keepGoing == 1) {
                displayMenu();
                command = input.next();

                if (command.equalsIgnoreCase("Done")) {
                    showMenu = false;
                } else {
                    processCommand(command);
                }
            } else if (keepGoing == 2) {
                showMenu = false;
            } else {
                System.err.println("Selection not valid...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            addItemToShoppingList();
        } else if (command.equals("2")) {
            addItemToOldPurchasedList();
        } else if (command.equals("3")) {
            deleteShoppingListEntry();
        } else if (command.equals("4")) {
            deleteOldPurchasedListEntry();
        } else if (command.equals("5")) {
            moveItemToOldPurchases();
        } else if (command.equals("6")) {
            moveItemToShoppingList();
        } else if (command.equals("7")) {
            checkShoppingListSize();
        } else if (command.equals("8")) {
            checkOldPurchasesListSize();
        } else if (command.equals("9")) {
            checkTotalExpectedPrice();
        } else if (command.equals("10")) {
            checkTotalMoneySpent();
        } else {
            processCommandHelper(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommandHelper(String command) {
        if (command.equals("11")) {
            deleteAllShoppingListItems();
        } else if (command.equals("12")) {
            deleteAllOldPurchasesListItems();
        } else if (command.equals("13")) {
            printShoppingList();
        } else if (command.equals("14")) {
            printOldPurchasesList();
        } else {
            System.err.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    // this code is referenced from the TellerApp lecture lab
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add Entry: Shopping List");
        System.out.println("\t2 -> Add Entry: Purchased List");
        System.out.println("\t3 -> Delete Entry: Shopping List");
        System.out.println("\t4 -> Delete Entry: Purchased List");
        System.out.println("\t5 -> Move a Shopping List item into the Purchased list");
        System.out.println("\t6 -> Move a Purchased List item back to the Shopping list");
        System.out.println("\t7 -> Check Number of Items: Shopping List");
        System.out.println("\t8 -> Check Number of Items: Purchased List");
        System.out.println("\t9 -> Check Expected Money Usage");
        System.out.println("\t10 -> Check Total Money Spent");
        System.out.println("\t11 -> Remove All Items: Shopping List");
        System.out.println("\t12 -> Remove All Items: Purchased List");
        System.out.println("\t13 -> View Shopping List");
        System.out.println("\t14 -> View Purchased List");
        System.out.println("\tDone -> Quit App");
    }

    // MODIFIES: this
    // EFFECTS: adds new Item entry into the ShoppingList
    public void addItemToShoppingList() {
        input.nextLine();
        System.out.print("Enter product name:");
        String productName = input.nextLine().toUpperCase();
        if (shoppingList.checkContainItem(shoppingList.findItemByName(productName))) {
            System.err.print("'" + productName + "' already exist in the Shopping List! (Press 'enter' to continue)");
            addItemToShoppingList();
        } else if (oldPurchasesList.checkContainItem(
                oldPurchasesList.findItemByName(productName))) {
            System.err.print("You have already bought '" + productName + "' ! (Press 'enter' to continue)");
            addItemToShoppingList();
        } else {
            System.out.print("Enter product category:");
            String category = input.nextLine().toUpperCase();
            System.out.print("Enter product price: $CAD");
            int price = input.nextInt();
            Item item = new Item(productName, category, price);
            shoppingList.addItem(item);
            System.out.println();
            System.out.println("The item '" + productName + "' is successfully added to your Shopping List!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds new Item entry into the OldPurchasesList
    public void addItemToOldPurchasedList() {
        input.nextLine();
        System.out.print("Enter product name: ");
        String productName = input.nextLine().toUpperCase();
        if (oldPurchasesList.checkContainItem(oldPurchasesList.findItemByName(productName))) {
            System.err.print("'" + productName + "' already exist in the Purchased List! (Press 'enter' to continue)");
            addItemToOldPurchasedList();
        } else if (shoppingList.checkContainItem(shoppingList.findItemByName(productName))) {
            System.err.print("'" + productName + "'" + " already exist in the Shopping List! Redirecting you..."
                    + "(Press 'enter' to continue)");
            moveItemToOldPurchases();
        } else {
            System.out.print("Enter product category: ");
            String category = input.nextLine().toUpperCase();
            System.out.print("Enter product price: $CAD");
            int price = input.nextInt();
            Item item = new Item(productName, category, price);
            oldPurchasesList.addItem(item);
            System.out.println();
            System.out.println("The item '" + productName + "' is successfully added to your Purchased List!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the specified Item (based on product name) entry from the ShoppingList
    public void deleteShoppingListEntry() {
        System.out.print("Please enter the product name of the item you wish to delete from the Shopping List: ");
        input.nextLine();
        String productName = input.nextLine().toUpperCase();
        if (shoppingList.findItemByName(productName) == null) {
            System.err.println("Error: The item you are looking for does not exist!");
        } else {
            shoppingList.removeItem(shoppingList.findItemByName(productName));
            System.out.println();
            System.out.println("The product '" + productName + "' is successfully deleted from the Shopping List.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the specified Item (based on product name) entry from the OldPurchasesList
    public void deleteOldPurchasedListEntry() {
        input.nextLine();
        System.out.print("Please enter the product name of the item you wish to delete from the Purchased List: ");
        String productName = input.nextLine().toUpperCase();
        if (oldPurchasesList.findItemByName(productName) == null) {
            System.err.println("Error: The item you are looking for does not exist!");
        } else {
            oldPurchasesList.removeItem(oldPurchasesList.findItemByName(productName));
            System.out.println();
            System.out.println("The product '" + productName + "' is successfully deleted from the Purchased List.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the specified Item (based on product name) from the ShoppingList and adds it to the
    //          OldPurchasesList
    public void moveItemToOldPurchases() {
        input.nextLine();
        System.out.print("Please enter the product name of the item you wish to move to the Purchased List: ");
        String productName = input.nextLine().toUpperCase();
        if (shoppingList.findItemByName(productName) == null) {
            System.err.println("Error: The item you are looking for does not exist!");
        } else {
            Item item = shoppingList.findItemByName(productName);
            shoppingList.removeItem(item);
            oldPurchasesList.addItem(item);
            System.out.println();
            System.out.println("The product '" + productName + "' has been moved to the Purchased List.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the specified Item (based on product name) from the OldPurchasesList and adds it to the
    //          ShoppingList
    public void moveItemToShoppingList() {
        input.nextLine();
        System.out.print("Please enter the product name of the item you wish to move to the Shopping List: ");
        String productName = input.nextLine().toUpperCase();
        if (oldPurchasesList.findItemByName(productName) == null) {
            System.err.println("Error: The item you are looking for does not exist!");
        } else {
            Item item = oldPurchasesList.findItemByName(productName);
            oldPurchasesList.removeItem(item);
            shoppingList.addItem(item);
            System.out.println();
            System.out.println("The product '" + productName + "' has been moved to the Shopping List.");
        }
    }

    // EFFECTS: returns the number of items in the ShoppingList
    public void checkShoppingListSize() {
        System.out.println();
        System.out.println("You currently have " + shoppingList.listLength()
                + " item(s) in your Shopping List.");
    }

    // EFFECTS: returns the number of items in the OldPurchasesList
    public void checkOldPurchasesListSize() {
        System.out.println();
        System.out.println("You currently have " + oldPurchasesList.listLength()
                + " item(s) in your Purchased List.");
    }

    // EFFECTS: returns the total price of the items in the ShoppingList
    public void checkTotalExpectedPrice() {
        System.out.println();
        System.out.println("The total price in your Shopping List amounts to $CAD" + shoppingList.totalPrice()
                + ".");
    }

    // EFFECTS: returns the total money spent from the items in the OldPurchasesList
    public void checkTotalMoneySpent() {
        System.out.println();
        System.out.println("The total price in your Purchased List amounts to $CAD" + oldPurchasesList.totalPrice()
                + ".");
    }

    // EFFECTS: saves the shopping list to file
    private void saveShoppingList() {
        try {
            shoppingListJsonWriter.open();
            shoppingListJsonWriter.write(shoppingList);
            shoppingListJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_STORE_SHOPPING_LIST);
        }
    }

    // EFFECTS: saves the old purchases list to file
    private void saveOldPurchasesList() {
        try {
            oldPurchasesListJsonWriter.open();
            oldPurchasesListJsonWriter.write(oldPurchasesList);
            oldPurchasesListJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_STORE_PURCHASED_LIST);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shopping list from file
    private void loadShoppingList() {
        try {
            shoppingList = shoppingListJsonReader.read();
        } catch (IOException e) {
            System.err.println("Unable to read from file: " + JSON_STORE_SHOPPING_LIST);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads old purchases list from file
    private void loadOldPurchasesList() {
        try {
            oldPurchasesList = oldPurchasesListJsonReader.read();
        } catch (IOException e) {
            System.err.println("Unable to read from file: " + JSON_STORE_PURCHASED_LIST);
        }
    }

    // EFFECTS: prints all items and their information in the shopping list to the console
    private void printShoppingList() {
        System.out.println("There are " + shoppingList.listLength() + " items in the Shopping List: ");
        for (int i = 0; i < shoppingList.listLength(); i++) {
            System.out.println(shoppingList.findItemByIndex(i).toString());
        }
    }

    // EFFECTS: prints all items and their information in the old purchases list to the console
    private void printOldPurchasesList() {
        System.out.println("There are " + oldPurchasesList.listLength() + " items in the Purchased List: ");
        for (int i = 0; i < oldPurchasesList.listLength(); i++) {
            System.out.println(oldPurchasesList.findItemByIndex(i).toString());
        }
    }

    // EFFECTS: remove all items in the shopping list
    private void deleteAllShoppingListItems() {
        shoppingList = null;
        shoppingList = new ShoppingList();
        System.out.println();
        System.out.println("All items in the Shopping List has been removed successfully.");
    }

    // EFFECTS: remove all items in the old purchases list
    private void deleteAllOldPurchasesListItems() {
        oldPurchasesList = null;
        oldPurchasesList = new OldPurchasesList();
        System.out.println();
        System.out.println("All items in the Purchased List has been removed successfully.");
    }

    public static void main(String[] args) {
        try {
            new ShoppingManagerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
        }
    }

}