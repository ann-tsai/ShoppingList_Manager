package persistence;

import org.json.JSONObject;

// this code was referenced from the WorkRoom example
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
