package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpendingTest {
    private Spending spending1;
    private Spending spending2;

    @BeforeEach
    void runBefore() {
        spending1 = new Spending(35.02,"snacks",Category.GROCERY);
        spending2 = new Spending(-1,"",Category.ENTERTAINMENT);

    }

    @Test
    void testGetAmount() {
        assertEquals(spending1.getAmount(),35.02);
        assertEquals(spending2.getAmount(),0);
    }

    @Test
    void testGetName() {
        assertEquals(spending1.getComment(),"snacks");
        assertEquals(spending2.getComment(),"");
    }

    @Test
    void testToString() {
        assertEquals(spending1.toString(),"$ 35.02     snacks");
        assertEquals(spending2.toString(),"$ 0.0");
    }
}