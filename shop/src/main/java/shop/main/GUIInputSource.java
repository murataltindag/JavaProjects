package shop.main;

import java.util.function.Predicate;

import javax.swing.JOptionPane;

/*
 * The GUIInputSource class implements the InputSource interface.
 * It is used to get input from the user using a GUI.
 */
public class GUIInputSource implements InputSource {

    @Override
    public String validateInput(Predicate<String> validator, String prompt, String errorPrompt) {
        String input;
        input = JOptionPane.showInputDialog(null, prompt);
        if(validator.test(input)){
            return input;
        }
        do {
            input = JOptionPane.showInputDialog(null, errorPrompt);
        } while (!validator.test(input));
        return input;
    }
}
