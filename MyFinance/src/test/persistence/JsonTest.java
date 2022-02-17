// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Category;
import model.Spending;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkSpending(double amount, String comment, Category category, Spending spending) {
        assertEquals(amount, spending.getAmount());
        assertEquals(comment, spending.getComment());
        assertEquals(category, spending.getCategory());
    }
}
