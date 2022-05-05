package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldPurchasesListTest {

    private OldPurchasesList oldPurchasesList;

    private final Item item1 = new Item("CPSC210 Textbook", "School", 100);
    private final Item item2 = new Item("iPhone 12 Pro", "Electronics", 1399);
    private final Item item3 = new Item("Plain Yellow T-Shirt", "Clothing", 20);
    private final Item item4 = new Item("Java - The Complete Reference", "Book", 76);
    private final Item item5 = new Item("Headphone", "Electronics", 389);


    @BeforeEach
    public void initialize() {
        oldPurchasesList = new OldPurchasesList();
    }

    @Test
    public void PurchasedItemTest() {
        assertEquals(0, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.addItem(item1));
        assertEquals(1, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.addItem(item2));
        assertEquals(2, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.addItem(item3));
        assertEquals(3, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.addItem(item4));
        assertEquals(4, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals(5, oldPurchasesList.listLength());
    }

    @Test
    public void ReturnedItemTest() {
        assertTrue(oldPurchasesList.addItem(item1));
        assertTrue(item1.isPurchased());
        assertTrue(oldPurchasesList.addItem(item2));
        assertTrue(item2.isPurchased());
        assertTrue(oldPurchasesList.addItem(item3));
        assertTrue(item3.isPurchased());
        assertTrue(oldPurchasesList.addItem(item4));
        assertTrue(item4.isPurchased());
        assertTrue(oldPurchasesList.addItem(item5));
        assertTrue(item5.isPurchased());
        assertEquals(5, oldPurchasesList.listLength());

        assertTrue(oldPurchasesList.removeItem(item1));
        assertFalse(item1.isPurchased());
        assertEquals(4, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.removeItem(item3));
        assertFalse(item3.isPurchased());
        assertEquals(3, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.removeItem(item2));
        assertFalse(item2.isPurchased());
        assertEquals(2, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.removeItem(item5));
        assertFalse(item5.isPurchased());
        assertEquals(1, oldPurchasesList.listLength());
        assertTrue(oldPurchasesList.removeItem(item4));
        assertFalse(item4.isPurchased());
        assertEquals(0, oldPurchasesList.listLength());
    }

    @Test
    public void ReturnedItemThatDoesNotExistTest() {
        assertFalse(oldPurchasesList.removeItem(item1));
        assertFalse(oldPurchasesList.removeItem(item2));
        assertFalse(oldPurchasesList.removeItem(item3));
        assertFalse(oldPurchasesList.removeItem(item4));
        assertFalse(oldPurchasesList.removeItem(item5));
    }

    @Test
    public void TotalPriceTest() {
        assertTrue(oldPurchasesList.addItem(item1));
        assertEquals(item1.getPrice(), oldPurchasesList.totalPrice());
        assertTrue(oldPurchasesList.addItem(item2));
        assertEquals((item1.getPrice() + item2.getPrice()), oldPurchasesList.totalPrice());
        assertTrue(oldPurchasesList.addItem(item3));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice()), oldPurchasesList.totalPrice());
        assertTrue(oldPurchasesList.addItem(item4));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice() + item4.getPrice()),
                oldPurchasesList.totalPrice());
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice() + item4.getPrice() +
                item5.getPrice()), oldPurchasesList.totalPrice());
    }

    @Test
    public void ItemPositionTest() {
        assertTrue(oldPurchasesList.addItem(item1));
        assertTrue(oldPurchasesList.addItem(item2));
        assertTrue(oldPurchasesList.addItem(item3));
        assertTrue(oldPurchasesList.addItem(item4));
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals(5, oldPurchasesList.listLength());

        assertEquals(5, oldPurchasesList.findItemPosition(item1));
        assertEquals(4, oldPurchasesList.findItemPosition(item2));
        assertEquals(3, oldPurchasesList.findItemPosition(item3));
        assertEquals(2, oldPurchasesList.findItemPosition(item4));
        assertEquals(1, oldPurchasesList.findItemPosition(item5));
    }

    @Test
    public void FindItemByNameTest() {
        assertTrue(oldPurchasesList.addItem(item1));
        assertTrue(oldPurchasesList.addItem(item2));
        assertTrue(oldPurchasesList.addItem(item3));
        assertTrue(oldPurchasesList.addItem(item4));
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals(5, oldPurchasesList.listLength());

        assertEquals(item1, oldPurchasesList.findItemByName("CPSC210 Textbook"));
        assertEquals(item2, oldPurchasesList.findItemByName("iPhone 12 Pro"));
        assertEquals(item3, oldPurchasesList.findItemByName("Plain Yellow T-Shirt"));
        assertEquals(item4, oldPurchasesList.findItemByName("Java - The Complete Reference"));
        assertEquals(item5, oldPurchasesList.findItemByName("Headphone"));
        assertNull(oldPurchasesList.findItemByName("Book"));
        assertNull(oldPurchasesList.findItemByName("CPSC 210 Textbook"));
        assertNull(oldPurchasesList.findItemByName("Textbook"));
        assertNull(oldPurchasesList.findItemByName("cpsc210 textbook"));
    }

    @Test
    public void ContainPurchaseItemTest() {
        assertTrue(oldPurchasesList.addItem(item2));
        assertTrue(oldPurchasesList.addItem(item3));
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals(3, oldPurchasesList.listLength());

        assertTrue(oldPurchasesList.checkContainItem(item2));
        assertTrue(oldPurchasesList.checkContainItem(item3));
        assertTrue(oldPurchasesList.checkContainItem(item5));
        assertFalse(oldPurchasesList.checkContainItem(item1));
        assertFalse(oldPurchasesList.checkContainItem(item4));
    }

    @Test
    public void FindItemByIndexTest() {
        assertTrue(oldPurchasesList.addItem(item1));
        assertTrue(oldPurchasesList.addItem(item2));
        assertTrue(oldPurchasesList.addItem(item3));
        assertTrue(oldPurchasesList.addItem(item4));
        assertTrue(oldPurchasesList.addItem(item5));
        assertEquals(5, oldPurchasesList.listLength());

        assertEquals(item1, oldPurchasesList.findItemByIndex(4));
        assertEquals(item2, oldPurchasesList.findItemByIndex(3));
        assertEquals(item3, oldPurchasesList.findItemByIndex(2));
        assertEquals(item4, oldPurchasesList.findItemByIndex(1));
        assertEquals(item5, oldPurchasesList.findItemByIndex(0));
    }
}

