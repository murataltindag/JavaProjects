package shop.main;

/*
 * The TextOutputSource class is used to print messages to the user.
 */
public class TextOutputSource implements OutputSource {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
