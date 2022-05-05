package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Item;
import model.ShoppingList;
import org.json.*;

// Represents a reader that reads Shopping List from JSON data stored in file
// this code is modeled from the WorkRoom example that was given.
public class ShoppingListJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public ShoppingListJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Shopping List from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShoppingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShoppingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Shopping List from JSON object and returns it
    private ShoppingList parseShoppingList(JSONObject jsonObject) {
        ShoppingList shoppingList = new ShoppingList();
        addShoppingList(shoppingList, jsonObject);
        return shoppingList;
    }

    // MODIFIES: shoppingList
    // EFFECTS: parses shoppingList from JSON object and adds them to Shopping List
    private void addShoppingList(ShoppingList shoppingList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("shopping list");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(shoppingList, nextItem);
        }
    }

    // MODIFIES: shoppingList
    // EFFECTS: parses item from JSON object and adds it to Shopping List
    private void addItem(ShoppingList shoppingList, JSONObject jsonObject) {
        String name = jsonObject.getString("product name");
        String category = jsonObject.getString("category");
        int price = jsonObject.getInt("price");
        Item item = new Item(name, category, price);
        shoppingList.addItem(item);
    }
}
