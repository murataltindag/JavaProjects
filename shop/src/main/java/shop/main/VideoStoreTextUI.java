package shop.main;

/*
 * The VideoStoreTextUI class is used to run the video store text user interface.
 */
public class VideoStoreTextUI {
    private State currentState = States.StartState;
    private InputSource inputSource = new TextInputSource();
    private OutputSource outputSource = new TextOutputSource();

    /*
     * The run method is used to run the video store text user interface with the state machine.
     */
    public void run() {
        while (true) {
            currentState.process(inputSource, outputSource);
            currentState = currentState.nextState();
        }
    }

    /*
     * The main method creates an instance of the VideoStoreTextUI class to start the application.
     */
    public static void main(String[] args) {
        VideoStoreTextUI ui = new VideoStoreTextUI();
        ui.run();
    }
}