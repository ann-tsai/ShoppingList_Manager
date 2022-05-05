package model;

import java.util.LinkedList;

public abstract class WorkLists {
    protected LinkedList<Item> workList;

    public WorkLists() {
        workList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: takes an Item and adds it to the end of the work list and returns true.
    //          If the item is already in the work list, do nothing and returns false.
    public abstract boolean addItem(Item item);

    // MODIFIES: this
    // EFFECTS: takes an Item and removes it from the work list and returns true.
    //          If the item doesn't exist, do nothing and returns false.
    public abstract boolean removeItem(Item item);

    // EFFECTS: returns the number of items currently in the shopping list
    public int listLength() {
        return workList.size();
    }

    // EFFECTS: takes an Item and returns true if it is already in the work list; false otherwise
    public boolean checkContainItem(Item item) {
        for (Item value : workList) {
            if (value.equals(item)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the total price of the items in the work list
    public int totalPrice() {
        int totalPrice = 0;
        for (Item value : workList) {
            totalPrice = totalPrice + value.getPrice();
        }
        return totalPrice;
    }

    // EFFECTS: returns the item in the work list with the given product name
    public Item findItemByName(String productName) {
        for (Item value : workList) {
            if (value.getProductName().equals(productName)) {
                return value;
            }
        }
        return null;
    }

    // EFFECTS: returns the item in the work list with the given index
    public Item findItemByIndex(int index) {
        return workList.get(index);
    }
}
