package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    private Item item;

    @BeforeEach
    public void setup() {
        item = new Item("Product","Category",0);
    }

    @Test
    public void printItemInStringTest() {
        assertEquals("ㅁ Product      [Category]     [$CAD0]", item.toString());

    }
}
