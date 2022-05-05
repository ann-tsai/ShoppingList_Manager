package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represent the purchased list - a list of Items that the user had already purchased
// some parts of this code structure were referenced from lab5 and the WorkRoom example
public class OldPurchasesList extends WorkLists implements Writable {

    // EFFECTS: initialize each newly created OldPurchaseList as an empty OldPurchaseList
    public OldPurchasesList() {
        this.workList = new LinkedList<>();
    }


    // MODIFIES: this
    // EFFECTS: adds the Item to the beginning of the OldPurchaseList and returns true.
    @Override
    public boolean addItem(Item item) {
        workList.addFirst(item);
        workList.getFirst().purchased();
        return true;
    }

    // MODIFIES: this
    // EFFECTS: takes an Item and removes it from the OldPurchaseList and returns true.
    //          If the item does not exist, do nothing and returns false.
    @Override
    public boolean removeItem(Item item) {
        if (findItemPosition(item) != -1) {
            workList.get(findItemPosition(item) - 1).notPurchased();
            return workList.remove(item);
        } else {
            return false;
        }
    }

    // EFFECTS: returns the index of the given Item in the OldPurchaseList, returns -1 if the item does not exist.
    public int findItemPosition(Item item) {
        for (int i = 0; i < workList.size(); i++) {
            if (workList.get(i).equals(item)) {
                return i + 1;
            }
        }
        return -1;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("purchased list", workListToJson());
        return json;
    }

    // EFFECTS: returns items in this OldPurchasesList as a JSON array
    private JSONArray workListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item value : workList) {
            jsonArray.put(value.toJson());
        }
        return jsonArray;

    }
}
