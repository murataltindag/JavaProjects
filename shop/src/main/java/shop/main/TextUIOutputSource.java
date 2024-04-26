package shop.main;

/*
 * The TextUIOutputSource class is used to print messages to the user.
 */
public class TextUIOutputSource implements OutputSource{
    public void printMessage(String message) {
        System.out.println(message);
    }
}
