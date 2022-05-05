package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Item;
import model.OldPurchasesList;
import org.json.*;

// Represents a reader that reads Old Purchases List from JSON data stored in file
// this code is modeled from the WorkRoom example that was given.
public class OldPurchasesListJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public OldPurchasesListJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Old Purchases List from file and returns it;
    // throws IOException if an error occurs reading data from file
    public OldPurchasesList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOldPurchasesList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Old Purchases List from JSON object and returns it
    private OldPurchasesList parseOldPurchasesList(JSONObject jsonObject) {
        OldPurchasesList oldPurchasesList = new OldPurchasesList();
        addOldPurchasesList(oldPurchasesList, jsonObject);
        return oldPurchasesList;
    }

    // MODIFIES: oldPurchasesList
    // EFFECTS: parses oldPurchasesList from JSON object and adds them to Old Purchases List
    private void addOldPurchasesList(OldPurchasesList oldPurchasesList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("purchased list");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(oldPurchasesList, nextItem);
        }
    }

    // MODIFIES: oldPurchasesList
    // EFFECTS: parses item from JSON object and adds it to Old Purchases List
    private void addItem(OldPurchasesList oldPurchasesList, JSONObject jsonObject) {
        String name = jsonObject.getString("product name");
        String category = jsonObject.getString("category");
        int price = jsonObject.getInt("price");
        Item item = new Item(name, category, price);
        oldPurchasesList.addItem(item);
    }
}
