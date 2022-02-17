package ui;

// Runs AppGUI
public class Main {
    public static void main(String[] args) {
//        new MyFinanceApp();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AppGUI app = new AppGUI();
                app.createAndShowGUI();
            }
        });

    }
}
