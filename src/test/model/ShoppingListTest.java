package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListTest {

    private ShoppingList shoppingList;

    private final Item item1 = new Item("CPSC210 Textbook", "School", 100);
    private final Item item2 = new Item("iPhone 12 Pro", "Electronics", 1399);
    private final Item item3 = new Item("Plain Yellow T-Shirt", "Clothing", 20);
    private final Item item4 = new Item("Java - The Complete Reference", "Book", 76);
    private final Item item5 = new Item("Headphone", "Electronics", 389);

    @BeforeEach
    public void setup() {
        shoppingList = new ShoppingList();
    }



    @Test
    public void AddItemTest() {
        assertEquals(0, shoppingList.listLength());
        assertTrue(shoppingList.addItem(item1));
        assertEquals(1, shoppingList.listLength());
        assertTrue(shoppingList.addItem(item2));
        assertEquals(2, shoppingList.listLength());
        assertTrue(shoppingList.addItem(item3));
        assertEquals(3, shoppingList.listLength());
        assertTrue(shoppingList.addItem(item4));
        assertEquals(4, shoppingList.listLength());
        assertTrue(shoppingList.addItem(item5));
        assertEquals(5, shoppingList.listLength());

        assertTrue(shoppingList.checkContainItem(item5));
        assertFalse(shoppingList.addItem(item5));
        assertEquals(5, shoppingList.listLength());
        assertTrue(shoppingList.checkContainItem(item4));
        assertFalse(shoppingList.addItem(item4));
        assertEquals(5, shoppingList.listLength());
    }

    @Test
    public void RemoveItemTest() {
        assertTrue(shoppingList.addItem(item1));
        assertTrue(shoppingList.addItem(item2));
        assertTrue(shoppingList.addItem(item3));
        assertTrue(shoppingList.addItem(item4));
        assertTrue(shoppingList.addItem(item5));
        assertEquals(5, shoppingList.listLength());

        assertTrue(shoppingList.removeItem(item1));
        assertEquals(4, shoppingList.listLength());
        assertTrue(shoppingList.removeItem(item3));
        assertEquals(3, shoppingList.listLength());
        assertTrue(shoppingList.removeItem(item2));
        assertEquals(2, shoppingList.listLength());
        assertTrue(shoppingList.removeItem(item5));
        assertEquals(1, shoppingList.listLength());
        assertTrue(shoppingList.removeItem(item4));
        assertEquals(0, shoppingList.listLength());
    }

    @Test
    public void RemoveItemThatDoesNotExistTest() {
        assertFalse(shoppingList.removeItem(item1));
        assertFalse(shoppingList.removeItem(item2));
        assertFalse(shoppingList.removeItem(item3));
        assertFalse(shoppingList.removeItem(item4));
        assertFalse(shoppingList.removeItem(item5));
    }

    @Test
    public void TotalPriceTest() {
        assertTrue(shoppingList.addItem(item1));
        assertEquals(item1.getPrice(), shoppingList.totalPrice());
        assertTrue(shoppingList.addItem(item2));
        assertEquals((item1.getPrice() + item2.getPrice()), shoppingList.totalPrice());
        assertTrue(shoppingList.addItem(item3));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice()), shoppingList.totalPrice());
        assertTrue(shoppingList.addItem(item4));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice() + item4.getPrice()),
                shoppingList.totalPrice());
        assertTrue(shoppingList.addItem(item5));
        assertEquals((item1.getPrice() + item2.getPrice() + item3.getPrice() + item4.getPrice() +
                item5.getPrice()), shoppingList.totalPrice());
    }

    @Test
    public void FindItemByNameTest() {
        assertTrue(shoppingList.addItem(item1));
        assertTrue(shoppingList.addItem(item2));
        assertTrue(shoppingList.addItem(item3));
        assertTrue(shoppingList.addItem(item4));
        assertTrue(shoppingList.addItem(item5));
        assertEquals(5, shoppingList.listLength());

        assertEquals(item1, shoppingList.findItemByName("CPSC210 Textbook"));
        assertEquals(item2, shoppingList.findItemByName("iPhone 12 Pro"));
        assertEquals(item3, shoppingList.findItemByName("Plain Yellow T-Shirt"));
        assertEquals(item4, shoppingList.findItemByName("Java - The Complete Reference"));
        assertEquals(item5, shoppingList.findItemByName("Headphone"));
        assertNull(shoppingList.findItemByName("CPSC 210 Textbook"));
        assertNull(shoppingList.findItemByName("Tree"));
        assertNull(shoppingList.findItemByName("cpsc210 textbook"));
    }

    @Test
    public void ContainPurchaseItemTest() {
        assertTrue(shoppingList.addItem(item2));
        assertTrue(shoppingList.addItem(item3));
        assertTrue(shoppingList.addItem(item5));
        assertEquals(3, shoppingList.listLength());

        assertTrue(shoppingList.checkContainItem(item2));
        assertTrue(shoppingList.checkContainItem(item3));
        assertTrue(shoppingList.checkContainItem(item5));
        assertFalse(shoppingList.checkContainItem(item1));
        assertFalse(shoppingList.checkContainItem(item4));
    }

    @Test
    public void FindItemByIndexTest() {
        assertTrue(shoppingList.addItem(item1));
        assertTrue(shoppingList.addItem(item2));
        assertTrue(shoppingList.addItem(item3));
        assertTrue(shoppingList.addItem(item4));
        assertTrue(shoppingList.addItem(item5));
        assertEquals(5, shoppingList.listLength());

        assertEquals(item1, shoppingList.findItemByIndex(0));
        assertEquals(item2, shoppingList.findItemByIndex(1));
        assertEquals(item3, shoppingList.findItemByIndex(2));
        assertEquals(item4, shoppingList.findItemByIndex(3));
        assertEquals(item5, shoppingList.findItemByIndex(4));
    }

    @Test
    public void insertItemAtIndexTest() {
        assertEquals(0, shoppingList.listLength());
        assertTrue(shoppingList.insertItemAt(item1, 0));
        assertEquals(1, shoppingList.listLength());

        assertEquals(item1, shoppingList.findItemByIndex(0));

        assertTrue(shoppingList.insertItemAt(item2,0));
        assertEquals(2, shoppingList.listLength());
        assertEquals(item2, shoppingList.findItemByIndex(0));

        assertTrue(shoppingList.insertItemAt(item3, 1));
        assertEquals(3, shoppingList.listLength());
        assertEquals(item3, shoppingList.findItemByIndex(1));
    }
}