package persistence;

import model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// this code is modeled from the WorkRoom example that was given.
class ShoppingListJsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        ShoppingListJsonReader reader = new ShoppingListJsonReader("./data/noSuchFile.json");
        try {
            ShoppingList shoppingList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShoppingList() {
        ShoppingListJsonReader reader = new ShoppingListJsonReader
                ("./data/testReaderEmptyShoppingList.json");
        try {
            ShoppingList shoppingList = reader.read();
            assertEquals(0, shoppingList.listLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoppingList() {
        ShoppingListJsonReader reader = new ShoppingListJsonReader
                ("./data/testReaderGeneralShoppingList.json");
        try {
            ShoppingList shoppingList = reader.read();
            assertEquals(2, shoppingList.listLength());
            checkItem("white t-shirt", "clothing", 10, shoppingList.findItemByIndex(0));
            checkItem("glasses", "accessory", 20, shoppingList.findItemByIndex(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
