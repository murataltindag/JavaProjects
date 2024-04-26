package shop.main;

public class VideoStoreTextUI {
    private State currentState = States.StartState;
    private InputSource inputSource = new TextInputSource();
    private OutputSource outputSource = new TextOutputSource();

    public void run() {
        while (true) {
            currentState.process(inputSource, outputSource);
            currentState = currentState.nextState();
        }
    }

    public static void main(String[] args) {
        VideoStoreTextUI ui = new VideoStoreTextUI();
        ui.run();
    }
}