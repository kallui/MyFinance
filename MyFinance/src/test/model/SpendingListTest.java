package model;

import model.Spending;
import model.Category;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpendingListTest {
    private SpendingList sl;

    @BeforeEach
    void runBefore() {
        sl = new SpendingList("test");
    }

    @Test
    void testAdd() {
        assertEquals(0,sl.size());
        sl.add(new Spending(1,"comment",Category.SHOPPING));
        assertEquals(1,sl.get(0).getAmount());
        assertEquals("comment",sl.get(0).getComment());
        assertEquals(Category.SHOPPING,sl.get(0).getCategory());
    }

    @Test
    void testRemove() {
        assertEquals(0,sl.size());
        sl.add(new Spending(0,"comment",Category.SHOPPING));
        assertEquals(1,sl.size());
        sl.remove(0);
        assertEquals(0,sl.size());
    }

    @Test
    void testGetUser() {
        assertEquals("test",sl.getUser());
    }

}
