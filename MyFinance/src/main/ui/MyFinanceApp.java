package ui;

import model.Category;
import model.Spending;
import model.SpendingList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// MyFinance Application
public class MyFinanceApp {
    private static final String JSON_STORE = "./data/spendinglist.json";
    private SpendingList spendingList;
    private Scanner userInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: initializes the arraylist and the scanner, runs the MyFinance app;
    public MyFinanceApp() {
        spendingList = new SpendingList("spending list");
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMyFinance();
    }

    // EFFECTS: display menu to user, takes user input and call methods according to user's input
    private void runMyFinance() {

        int input;
        do {
            menu();
            input = userInput.nextInt();
            switch (input) {
                case 1 : printList();
                    break;
                case 2 : addSpending();
                    break;
                case 3 : deleteSpending();
                    break;
                case 4 : summary();
                    break;
                case 5 : saveList();
                    break;
                case 6 : loadList();
                    break;
            }

        } while (input != 7);
    }

    // EFFECTS: display menu options
    private void menu() {
        System.out.println("============================!MyFinance!===============================");
        System.out.println("1. View Spending History");
        System.out.println("2. Add a Spending");
        System.out.println("3. Delete a Spending");
        System.out.println("4. Summary");
        System.out.println("5. Save list");
        System.out.println("6. Load list");
        System.out.println("7. Exit");
        System.out.println("Pick [1-7]:");
    }

    // EFFECTS: prints the spendingList arraylist
    private void printList() {
        if (spendingList.size() > 0) {
            System.out.printf("%s\t%-12s\t$ %-15s%-15s\n","No.","Category","Amount", "Additional comments");
            int idx = 1;
            for (int i = 0; i < spendingList.size(); i++) {
                System.out.printf("%s\t%-12s\t$ %-15s%-20s\n",idx,spendingList.get(i).getCategory(),
                        spendingList.get(i).getAmount(),spendingList.get(i).getComment());
                idx++;
            }
        } else {
            System.out.println("No spending yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a Spending object into the arraylist
    private void addSpending() {
        Category category;
        String comment;
        double amount = 0;

//        userInput.nextLine();

        while (amount < 1) {
            System.out.println("Enter amount (must be more than 0): ");
            amount = userInput.nextDouble();
        }
        enumMenu();
        category = pickEnum();

        comment = addComment();

        if (spendingList.add(new Spending(amount,comment,category))) {
            System.out.println("Added!");
        } else {
            System.out.println("Failed!");
        }
    }

    // EFFECTS: display the category menu
    private void enumMenu() {
        System.out.println("Pick a category:");
        System.out.println("1. Grocery");
        System.out.println("2. Dining out");
        System.out.println("3. Shopping");
        System.out.println("4. Utilities");
        System.out.println("5. Entertainment ");
        System.out.println("6. Fees ");
        System.out.println("7. Others ");
        System.out.println("Pick [1-7]:");
    }

    // EFFECTS: Returns the chosen category
    private Category pickEnum() {
        int input;
        Category c = null;
        do {
            input = userInput.nextInt();
            switch (input) {
                case 1 : c = Category.GROCERY;
                    break;
                case 2 : c = Category.DINING_OUT;
                    break;
                case 3 : c = Category.SHOPPING;
                    break;
                case 4 : c = Category.UTILITIES;
                    break;
                case 5 : c = Category.ENTERTAINMENT;
                    break;
                case 6 : c = Category.FEES;
                    break;
                case 7 : c = Category.OTHERS;
                    break;
                //
            }
        } while (input < 1 && input > 7);
        return c;
    }

    // EFFECTS: User decides whether they want to add comment or not
    //          Returns a string of the user's comment
    private String addComment() {
        int input;
        do {
            System.out.println("Add additional comment?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            input = userInput.nextInt();
        } while (input != 1 && input != 2);

        if (input == 1) {
            System.out.println("Enter comment:");
            userInput.nextLine();

            return userInput.nextLine();
        } else {
            return "";
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a Spending object in the arraylist
    private void deleteSpending() {
        printList();
        int indexToDelete = -2;
        while (indexToDelete < -1 || indexToDelete >= spendingList.size()) {
            System.out.println("Enter the index number of the spending that you wish to delete (-1 to cancel): ");
            indexToDelete = userInput.nextInt();
        }
        if (indexToDelete != -1) {
            indexToDelete--;
            spendingList.remove(indexToDelete);
            System.out.println("Deleted!");
        }

    }

    // EFFECTS: prints the average, largest spending, and the smallest spending in the spendingList arraylist
    private void summary() {
        if (spendingList.size() > 0) {
            System.out.println("Total spendings: " + spendingList.size());
            System.out.println("Average spending: $" + averageOfAllSpendings());
            System.out.println("Largest spending: " + spendingList.get(largestSpendingIndex()).getComment()
                    + " with an amount of $" + spendingList.get(largestSpendingIndex()).getAmount());
            System.out.println("Smallest spending: " + spendingList.get(smallestSpendingIndex()).getComment()
                    + " with an amount of $" + spendingList.get(smallestSpendingIndex()).getAmount());
        } else {
            System.out.println("No spending yet!");
        }

    }

    // EFFECTS: returns the average of the all Spending in the arraylist
    private double averageOfAllSpendings() {
        double sum = 0;
        for (int i = 0; i < spendingList.size(); i++) {
            sum += spendingList.get(i).getAmount();
        }
        return (sum / spendingList.size());
    }

    // EFFECTS: returns the index of the largest Spending in the arraylist
    private int largestSpendingIndex() {
        int largestIdx = 0;
        for (int i = 1; i < spendingList.size(); i++) {
            if (spendingList.get(i).getAmount() > spendingList.get(largestIdx).getAmount()) {
                largestIdx = i;
            }
        }
        return largestIdx;
    }

    // EFFECTS: returns the index of the smallest Spending in the arraylist
    private int smallestSpendingIndex() {
        int smallestIdx = 0;
        for (int i = 1; i < spendingList.size(); i++) {
            if (spendingList.get(i).getAmount() < spendingList.get(smallestIdx).getAmount()) {
                smallestIdx = i;
            }
        }
        return smallestIdx;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads spendinglist from file
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(spendingList);
            jsonWriter.close();
            System.out.println("Saved " + spendingList.getUser() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads spendinglist from file
    private void loadList() {
        try {
            spendingList = jsonReader.read();
            System.out.println("Loaded " + spendingList.getUser() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
