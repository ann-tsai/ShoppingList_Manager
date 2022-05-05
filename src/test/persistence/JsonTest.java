package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// this code is referenced from the WorkRoom example
public class JsonTest {
    protected void checkItem(String productName, String category, int price, Item item) {
        assertEquals(productName, item.getProductName());
        assertEquals(category, item.getCategory());
        assertEquals(price, item.getPrice());
    }
}
