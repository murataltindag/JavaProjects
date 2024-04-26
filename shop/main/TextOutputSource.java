package shop.main;

public class TextOutputSource implements OutputSource {
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
