// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Category;
import model.Spending;
import model.SpendingList;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;

// Represents a reader that reads spendinglist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads spendinglist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SpendingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSpendingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses spendinglist from JSON object and returns it
    private SpendingList parseSpendingList(JSONObject jsonObject) {
        String user = jsonObject.getString("user");
        SpendingList sl = new SpendingList(user);
        addSpendingList(sl, jsonObject);
        return sl;
    }

    // MODIFIES: sl
    // EFFECTS: parses spendinglist from JSON object and adds them to spendinglist
    private void addSpendingList(SpendingList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spendinglist");
        for (Object json : jsonArray) {
            JSONObject nextSpending = (JSONObject) json;
            addSpending(sl, nextSpending);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses spending from JSON object and adds it to spendinglist
    private void addSpending(SpendingList sl, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String comment = jsonObject.getString("comment");

        Category category = Category.valueOf(jsonObject.getString("category"));

        sl.add(new Spending(amount,comment,category));
    }
}
