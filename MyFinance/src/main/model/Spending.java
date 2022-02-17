package model;

import org.json.JSONObject;
import persistence.Writable;


// Represents a spending having an amount and name
public class Spending implements Writable {
    private double amount;
    private String comment;
    private Category category;

    /*
     * REQUIRES: amount > 0, name has a non-zero length
     * MODIFIES: this, amount, name
     * EFFECTS: constructs a new Spending object
     *          if amount is <= 0, set amount to a default value of 1;
     *          if name is an empty string, set name to "default"
     */
    public Spending(double amount, String comment, Category category) {
        if (amount > 0) {
            this.amount = amount;
        } else {
            this.amount = 0;
        }
        this.comment = comment;
        this.category = category;
    }

    /*
     * EFFECTS: returns the amount of Spending
     */
    public double getAmount() {
        return amount;
    }

    /*
     * EFFECTS: returns the name of Spending
     */
    public String getComment() {
        return comment;
    }

    /*
     * EFFECTS: returns the category of spending
     */
    public Category getCategory() {
        return category;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: json
    // EFFECTS: inserts Spending to json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("category", category);
        json.put("comment", comment);
        return json;
    }

    public String toString() {
        if (comment.equals("")) {
            return "$ " + amount;
        }
        return "$ " + amount + "     " + comment;
    }
}
