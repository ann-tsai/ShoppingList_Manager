package persistence;

import model.OldPurchasesList;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of purchased list to file
// this code is modeled from the WorkRoom example that was given.
public class OldPurchasesListJsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public OldPurchasesListJsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //          be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of purchased list to file
    public void write(OldPurchasesList oldPurchasesList) {
        JSONObject json = oldPurchasesList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
