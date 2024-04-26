package shop.main;

import javax.swing.JOptionPane;

/**
 * The GUIOutputSource class implements the OutputSource interface.
 * It is used to print messages to the user using a GUI.
 */
public class GUIOutputSource implements OutputSource {

    @Override
    public void printMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void printError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
