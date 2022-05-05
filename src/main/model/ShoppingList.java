package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represent the shopping list - a list of Items that the user wish to purchase in the future.
// some parts of this code structure were referenced from lab5 and the WorkRoom example
public class ShoppingList extends WorkLists implements Writable {

    // EFFECTS: initialize each newly created ShoppingList as an empty shopping list
    public ShoppingList() {
        this.workList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: takes an Item and adds it to the end of the shopping list and returns true.
    //          If the item is already in the shopping list, do nothing and returns false.
    @Override
    public boolean addItem(Item item) {
        for (Item value : workList) {
            if (value.equals(item)) {
                return false;
            }
        }
        workList.add(item);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: takes an Item and removes it from the shopping list and returns true.
    //          If the item doesn't exist, do nothing and returns false.
    @Override
    public boolean removeItem(Item item) {
        return workList.remove(item);
    }


    // MODIFIES: this
    // EFFECTS: takes an Item and insert it at the specified index
    public boolean insertItemAt(Item item, int index) {
        workList.add(index, item);
        return true;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("shopping list", shoppingListToJson());
        return json;
    }

    // EFFECTS: returns items in this ShoppingList as a JSON array
    private JSONArray shoppingListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item value : workList) {
            jsonArray.put(value.toJson());
        }
        return jsonArray;
    }

}
