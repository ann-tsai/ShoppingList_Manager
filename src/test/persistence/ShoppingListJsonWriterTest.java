package persistence;

import model.Item;
import model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// this code is modeled from the WorkRoom example that was given.
class ShoppingListShoppingListJsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ShoppingList shoppingList = new ShoppingList();
            ShoppingListJsonWriter writer = new ShoppingListJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoppingList() {
        try {
            ShoppingList shoppingList = new ShoppingList();
            ShoppingListJsonWriter writer = new ShoppingListJsonWriter
                    ("./data/testWriterEmptyShoppingList.json");
            writer.open();
            writer.write(shoppingList);
            writer.close();

            ShoppingListJsonReader reader = new ShoppingListJsonReader
                    ("./data/testWriterEmptyShoppingList.json");
            shoppingList = reader.read();
            assertEquals(0, shoppingList.listLength());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoppingList() {
        try {
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.addItem(new Item("white t-shirt", "clothing", 10));
            shoppingList.addItem(new Item("glasses", "accessory", 20));
            ShoppingListJsonWriter writer = new ShoppingListJsonWriter
                    ("./data/testWriterGeneralShoppingList.json");
            writer.open();
            writer.write(shoppingList);
            writer.close();

            ShoppingListJsonReader reader = new ShoppingListJsonReader
                    ("./data/testWriterGeneralShoppingList.json");
            shoppingList = reader.read();
            assertEquals(2, shoppingList.listLength());
            checkItem("white t-shirt", "clothing", 10, shoppingList.findItemByIndex(0));
            checkItem("glasses", "accessory", 20, shoppingList.findItemByIndex(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}