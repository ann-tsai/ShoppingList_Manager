package persistence;

import model.OldPurchasesList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// this code is modeled from the WorkRoom example that was given.
class OldPurchasesListJsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        OldPurchasesListJsonReader reader = new OldPurchasesListJsonReader("./data/noSuchFile.json");
        try {
            OldPurchasesList oldPurchasesList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyOldPurchasesList() {
        OldPurchasesListJsonReader reader = new OldPurchasesListJsonReader
                ("./data/testReaderEmptyOldPurchasesList.json");
        try {
            OldPurchasesList oldPurchasesList = reader.read();
            assertEquals(0, oldPurchasesList.listLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOldPurchasesList() {
        OldPurchasesListJsonReader reader = new OldPurchasesListJsonReader
                ("./data/testReaderGeneralOldPurchasesList.json");
        try {
            OldPurchasesList oldPurchasesList = reader.read();
            assertEquals(2, oldPurchasesList.listLength());
            checkItem("black t-shirt", "clothing", 15, oldPurchasesList.findItemByIndex(0));
            checkItem("jeans", "clothing", 25, oldPurchasesList.findItemByIndex(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
