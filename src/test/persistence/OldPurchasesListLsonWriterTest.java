package persistence;

import model.Item;
import model.OldPurchasesList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// this code is modeled from the WorkRoom example that was given.
class OldPurchasesListJsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            OldPurchasesList oldPurchasesList = new OldPurchasesList();
            OldPurchasesListJsonWriter writer = new OldPurchasesListJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOldPurchasesList() {
        try {
            OldPurchasesList oldPurchasesList = new OldPurchasesList();
            OldPurchasesListJsonWriter writer = new OldPurchasesListJsonWriter
                    ("./data/testWriterEmptyOldPurchasesList.json");
            writer.open();
            writer.write(oldPurchasesList);
            writer.close();

            OldPurchasesListJsonReader reader = new OldPurchasesListJsonReader
                    ("./data/testWriterEmptyOldPurchasesList.json");
            oldPurchasesList = reader.read();
            assertEquals(0, oldPurchasesList.listLength());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralOldPurchasesList() {
        try {
            OldPurchasesList oldPurchasesList = new OldPurchasesList();
            oldPurchasesList.addItem(new Item("black t-shirt", "clothing", 15));
            oldPurchasesList.addItem(new Item("jeans", "clothing", 25));
            OldPurchasesListJsonWriter writer = new OldPurchasesListJsonWriter
                    ("./data/testWriterGeneralOldPurchasesList.json");
            writer.open();
            writer.write(oldPurchasesList);
            writer.close();

            OldPurchasesListJsonReader reader = new OldPurchasesListJsonReader
                    ("./data/testWriterGeneralOldPurchasesList.json");
            oldPurchasesList = reader.read();
            assertEquals(2, oldPurchasesList.listLength());
            checkItem("black t-shirt", "clothing", 15, oldPurchasesList.findItemByIndex(0));
            checkItem("jeans", "clothing", 25, oldPurchasesList.findItemByIndex(1));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}