// This test class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Category;
import model.Spending;
import model.SpendingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SpendingList sl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySpendingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySpendingList.json");
        try {
            SpendingList sl = reader.read();
            assertEquals("test", sl.getUser());
            assertEquals(0, sl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSuccess() {
        JsonReader reader = new JsonReader("./data/testReaderSuccess.json");
        try {
            SpendingList sl = reader.read();
            assertEquals("niko", sl.getUser());
            assertEquals(2, sl.size());
            checkSpending(123, "game", Category.ENTERTAINMENT, sl.get(0));
            checkSpending(345, "snacks", Category.SHOPPING, sl.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}