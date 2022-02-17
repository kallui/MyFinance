// This test class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Category;
import model.Spending;
import model.SpendingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testWriterEmptySpendingList() {
        try {
            SpendingList sl = new SpendingList("test");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySpendingList.json");

            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySpendingList.json");
            sl = reader.read();
            assertEquals("test", sl.getUser());
            assertEquals(0, sl.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSuccess() {
        try {
            SpendingList sl = new SpendingList("niko");
            sl.add(new Spending(123, "game", Category.ENTERTAINMENT));
            sl.add(new Spending(345, "snacks", Category.SHOPPING));
            JsonWriter writer = new JsonWriter("./data/testWriterSuccess.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSuccess.json");
            sl = reader.read();
            assertEquals("niko", sl.getUser());

            assertEquals(2, sl.size());
            checkSpending(123, "game", Category.ENTERTAINMENT, sl.get(0));
            checkSpending(345, "snacks", Category.SHOPPING, sl.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}