package shop.main;

public class TextUIOutputSource implements OutputSource{
    public void printMessage(String message) {
        System.out.println(message);
    }
}
