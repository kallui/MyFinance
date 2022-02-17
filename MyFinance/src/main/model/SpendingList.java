package model;


import com.sun.corba.se.spi.ior.Writeable;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Spending List that contains Spending objects
public class SpendingList implements Writable {

    private List<Spending> spendingList;
    private String user;

    /*
     * MODIFIES: this, spendingList, user
     * EFFECTS: constructs a new Spending object
     */
    public SpendingList(String user) {
        this.user = user;
        spendingList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds spending to list
    public boolean add(Spending s) {
        EventLog.getInstance().logEvent(new Event("Added spending [" + s.toString() + "] to spending list"));
        return spendingList.add(s);
    }

    // MODIFIES: this
    // EFFECTS: removes spending in index i from list
    public void remove(int i) {
        EventLog.getInstance().logEvent(new Event("Removed spending [" + get(i).toString() + "] from spending list"));
        spendingList.remove(i);
    }

    // EFFECTS: gets spending at index i of spendingList
    public Spending get(int i) {
        return spendingList.get(i);
    }

    // EFFECTS: returns size of list
    public int size() {
        return spendingList.size();
    }

    // EFFECTS: gets user
    public String getUser() {
        return user;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: json
    // EFFECTS: inserts SpendingList to json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("user", user);
        json.put("spendinglist", spendingListToJson());
        return json;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns spending in this spendinglist as a JSON array
    private JSONArray spendingListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Spending s : spendingList) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}
