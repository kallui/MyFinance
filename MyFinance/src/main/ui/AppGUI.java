package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

// MyFinance Application GUI
public class AppGUI extends JPanel implements ListSelectionListener {

    private JFrame frame;

    private DefaultListModel listModel;
    private JList list;

    private static final String JSON_STORE = "./data/spendinglist.json";
    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";

    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;
    private JButton removeButton;

    private JPanel buttonPane;

    private JTextField amountText;
    private JTextField commentText;

    private JLabel amountLabel;
    private JLabel commentLabel;

    private SpendingList spendingList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: initializes spendingList, buttons, panel, jlist, textfields,jsonwriter,
    //          jsonreader, etc.
    public AppGUI() {
        super(new BorderLayout());

        spendingList = new SpendingList("spending list");

        //initial values:
//        spendingList.add(new Spending(11,"Spending 1",Category.GROCERY));
//        spendingList.add(new Spending(22,"Spending 2",Category.GROCERY));
//        spendingList.add(new Spending(33,"Spending 3",Category.GROCERY));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        listModel = new DefaultListModel();

        addSpendingListToListModel(listModel,spendingList);

        initJList();
        JScrollPane listScrollPane = new JScrollPane(list);

        initButtons();

        initButtonPane();

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./data/logoTextOnly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));

        add(picLabel,BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }


    // MODIFIES: this
    // EFFECTS: initializes JList
    private void initJList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(8);
    }

    // MODIFIES: this
    // EFFECTS: initializes ButtonPane panel
    private void initButtonPane() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));

        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(amountLabel);
        buttonPane.add(amountText);
        buttonPane.add(commentLabel);
        buttonPane.add(commentText);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    // MODIFIES: this
    // EFFECTS: initializes JButtons
    private void initButtons() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());


        amountText = new JTextField(5);
        amountText.addActionListener(addListener);
        amountText.getDocument().addDocumentListener(addListener);

        commentText = new JTextField(12);
        commentText.addActionListener(addListener);
        commentText.getDocument().addDocumentListener(addListener);

        amountLabel = new JLabel("Amount: ");
        commentLabel = new JLabel(" Comment: ");
    }

    // MODIFIES: this
    // EFFECTS: clears listModel and adds spendingList into the listModel
    private void addSpendingListToListModel(DefaultListModel listModel, SpendingList spendingList) {
        listModel.clear();
        for (int i = 0; i < spendingList.size(); i++) {
            listModel.addElement(spendingList.get(i).toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes frame
    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("MyFinance");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e: EventLog.getInstance()) {
                    System.out.println(e.toString() + "\n");

                }
                //THEN you can exit the program
                System.exit(0);
            }
        });

        //Create and set up the content pane.
        JComponent newContentPane = new AppGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // Listener for remove button
    // MODIFIES: this
    // EFFECTS: removes the selected cell
    private class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = list.getSelectedIndex();
            listModel.remove(index);


            //remove from spending list
            spendingList.remove(index);
            int size = listModel.getSize();

//            viewSpendingList();

            if (size == 0) {
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);


            }
        }
    }

    // Listener for add button
    // MODIFIES: this
    // EFFECTS: adds a new cell into the JList using data from text fields
    private class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String amountString = amountText.getText();
            String comment = commentText.getText();
            double amount;

            try {
                amount = Double.parseDouble(amountString);
            } catch (Exception ex) {
                amount = 0;
            }

            int index = list.getSelectedIndex();
            spendingList.add(new Spending(amount,comment, Category.GROCERY));
            listModel.addElement(spendingList.get(spendingList.size() - 1));

//            viewSpendingList();

            //Reset the text field.
            amountText.requestFocusInWindow();
            amountText.setText("");

            commentText.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: enables button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: if text field is empty, disable button
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // Listener for save button
    // MODIFIES: this
    // EFFECTS: saves spendingList into json file
    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(spendingList);
                jsonWriter.close();
                System.out.println("Saved " + spendingList.getUser() + " to " + JSON_STORE);


            } catch (FileNotFoundException ex) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

        }
    }

    // Listener for load button
    // MODIFIES: this
    // EFFECTS: loads json file into spending list
    private class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                spendingList = jsonReader.read();
                System.out.println("Loaded " + spendingList.getUser() + " from " + JSON_STORE);

            } catch (IOException ex) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }

            addSpendingListToListModel(listModel,spendingList);
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the remove button.
                removeButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: prints the spendingList
    private void viewSpendingList() {
        System.out.println("SpendingList:");
        for (int i = 0; i < spendingList.size(); i++) {
            System.out.println(spendingList.get(i).toString());
        }
        System.out.println("-----");
    }
}
