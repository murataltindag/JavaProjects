package shop.main;

import javax.swing.JOptionPane;

public class GUIOutputSource implements OutputSource {

    @Override
    public void printMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void printError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
