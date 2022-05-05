package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item having a product name, category, price and a purchase/not purchased status.
// The code structure here is partially referenced from lab5 and the WorkRoom example
public class Item implements Writable {
    private final String productName;
    private final String category;
    private final int price;
    private boolean isPurchased;

    // EFFECTS: item has given product name, category, price (expressed in nearest CAD dollar), and is not yet purchased
    public Item(String productName, String category, int price) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        isPurchased = false;
    }

    // EFFECTS: returns product name
    public String getProductName() {
        return productName;
    }

    // EFFECTS: returns category
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns price
    public int getPrice() {
        return price;
    }

    // EFFECTS: returns true if product is already purchased, false otherwise
    public boolean isPurchased() {
        return isPurchased;
    }

    // MODIFIES: this
    // EFFECTS: changes purchase status to true
    public void purchased() {
        isPurchased = true;
    }

    // MODIFIES: this
    // EFFECTS: changes the purchase status to false
    public void notPurchased() {
        isPurchased = false;
    }


    @Override
    // EFFECTS: return the item's product name, category, and price in String
    public String toString() {
        return ("ㅁ " + getProductName() + "      [" + getCategory() + "]     [$CAD" + getPrice() + "]");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("product name", productName);
        json.put("category", category);
        json.put("price", price);
        return json;
    }
}
